package com.xingchi.tornado.springdoc.config;


import com.xingchi.tornado.springdoc.anno.ApiGroup;
import com.xingchi.tornado.springdoc.model.PackageGroup;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.AbstractRequestService;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.OpenAPIService;
import org.springdoc.core.OperationService;
import org.springdoc.core.PropertyResolverUtils;
import org.springdoc.core.SecurityService;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocProviders;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource;
import org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springdoc.core.Constants.ALL_PATTERN;
import static org.springdoc.core.Constants.DEFAULT_GROUP_NAME;
import static org.springdoc.core.Constants.SPRINGDOC_ENABLED;

/**
 * 自动配置
 *
 * @author xiaoya
 * @date 2022/12/5 14:58
 * @modified xiaoya
 */
@Order(Integer.MIN_VALUE + 10)
@Configuration
@DependsOn("requestMappingHandlerMapping")
@EnableConfigurationProperties(OpenApiProperties.class)
@AutoConfigureBefore({MultipleOpenApiSupportConfiguration.class, })
@ConditionalOnProperty(name = SPRINGDOC_ENABLED)
public class OpenApiAutoConfiguration implements BeanFactoryAware, InitializingBean {

    /**
     * 基于注解方式进行分组
     */
    public static final String ANNOTATION_OPENAPI_GROUP_BEAN_NAME_PREFIX = "annotationOpenApiGroup";

    /**
     * 基于包扫描方式进行分组
     */
    public static final String PACKAGE_OPENAPI_GROUP_BEAN_NAME_PREFIX = "packageOpenApiGroup";

    @Autowired
    private RequestMappingHandlerMapping mappingHandlerMapping;
    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private OpenApiProperties openApiProperties;

    private ConfigurableBeanFactory beanFactory;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .externalDocs(new ExternalDocumentation()
                        .description(openApiProperties.getExternalDocumentation().getDescription())
                        .url(openApiProperties.getExternalDocumentation().getUrl())
                        .extensions(openApiProperties.getContact().getExtensions()));
    }

    private Info getApiInfo() {

        OpenApiProperties.Contact contact = openApiProperties.getContact();

        Contact finalContact = new Contact();
        finalContact.setName(contact.getName());
        finalContact.setEmail(contact.getEmail());
        finalContact.setUrl(contact.getUrl());
        finalContact.setExtensions(contact.getExtensions());

        OpenApiProperties.License license = openApiProperties.getLicense();
        License finalLicense = new License();
        finalLicense.setName(license.getName());
        finalLicense.setUrl(license.getUrl());
        finalLicense.setExtensions(license.getExtensions());

        return new Info()
                .title(openApiProperties.getTitle())
                .version(openApiProperties.getVersion())
                .description(openApiProperties.getDescription())
                .license(finalLicense)
                .contact(finalContact);
    }

    public boolean isMethodToInclude(Method method, String groupName) {
        ApiGroup annotation = AnnotationUtils.findAnnotation(method, ApiGroup.class);
        if (annotation == null) {
            return false;
        }
        String[] group = annotation.group();
        return Arrays.stream(group).anyMatch(item -> StringUtils.hasText(groupName) && groupName.equalsIgnoreCase(item));
    }

    /**
     * 创建默认分组
     *
     * @return      默认分组
     */
    private GroupedOpenApi getDefaultGroupOpenApi() {

        String defaultGroupName = openApiProperties.getDefaultGroupName();
        if (!StringUtils.hasText(defaultGroupName)) {
            defaultGroupName = DEFAULT_GROUP_NAME;
        }

        return GroupedOpenApi.builder()
                .group(defaultGroupName)
                .pathsToMatch(ALL_PATTERN)
                .pathsToExclude(webEndpointProperties.getBasePath() + ALL_PATTERN)
                .build();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableBeanFactory) {
            this.beanFactory = (ConfigurableBeanFactory) beanFactory;
        }
    }


    @Override
    public void afterPropertiesSet() {
        // 添加分组对象
        // 获取所有HandlerMethod
        Map<RequestMappingInfo, HandlerMethod> mappingHandlerMethods = mappingHandlerMapping.getHandlerMethods();
        Set<String> groupNames = new HashSet<>();
        for (HandlerMethod handlerMethod : mappingHandlerMethods.values()) {
            // 处理每一个handlerMethod
            // 判断当前请求方法上是否包含了ApiGroup.class
            Method method = handlerMethod.getMethod();
            ApiGroup annotation = AnnotationUtils.findAnnotation(method, ApiGroup.class);
            if (annotation == null) {
                continue;
            }
            String[] group = annotation.group();
            groupNames.addAll(Arrays.asList(group));
        }

        // 设置默认分组
        Map<String, GroupedOpenApi> groupMappings = new HashMap<>();
        if (openApiProperties.isDefaultGroup()) {
            GroupedOpenApi defaultGroupOpenApi = getDefaultGroupOpenApi();
            groupMappings.put(defaultGroupOpenApi.getGroup(), defaultGroupOpenApi);
        }

        // 根据包名进行分组
        int index = 0;
        List<PackageGroup> groups = openApiProperties.getGroups();
        if (!CollectionUtils.isEmpty(groups)) {

            for (PackageGroup group : groups) {
                String groupName = group.getGroupName();
                String packageName = group.getPackageName();
                if (StringUtils.hasText(groupName) && StringUtils.hasText(packageName)) {
                    GroupedOpenApi build = GroupedOpenApi.builder()
                            .group(groupName)
                            .displayName(groupName.trim())
                            .packagesToScan(packageName.trim())
                            .build();
                    groupMappings.put(PACKAGE_OPENAPI_GROUP_BEAN_NAME_PREFIX + "_" + index++, build);
                }
            }
        }

        // 根据注解内容进行分组
        for (String groupName : groupNames) {
            GroupedOpenApi build = GroupedOpenApi.builder()
                    .group(groupName)
                    .displayName(groupName.trim())
                    .addOpenApiMethodFilter(method -> this.isMethodToInclude(method, groupName))
                    .build();
            groupMappings.put(ANNOTATION_OPENAPI_GROUP_BEAN_NAME_PREFIX + "_" + index++, build);
        }

        List<GroupedOpenApi> result = new ArrayList<>();
        for (Map.Entry<String, GroupedOpenApi> entry : groupMappings.entrySet()) {
            GroupedOpenApi group = entry.getValue();
            beanFactory.registerSingleton(entry.getKey(), group);
        }
    }
}
