package com.xingchi.tornado.springdoc.config;

import com.xingchi.tornado.springdoc.model.PackageGroup;
import io.swagger.v3.oas.models.ExternalDocumentation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API配置
 *
 * @author xiaoya
 * @date 2022/12/6 11:03
 * @modified xiaoya
 */
@Data
@ConfigurationProperties(prefix = "springdoc.tornado.doc")
public class OpenApiProperties {

    /**
     * 默认分组名
     */
    private String defaultGroupName = "defaultOpenApi";

    /**
     * 开启默认分组
     */
    private boolean defaultGroup = true;

    /**
     * 文档title
     */
    private String title = "";

    /**
     * 版本
     */
    private String version = "1.0.0";

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 许可
     */
    private License license = new License();

    /**
     * 许可
     */
    private Contact contact = new Contact();

    /**
     * 根据包名进行分组
     */
    private List<PackageGroup> groups;

    /**
     * 扩展信息
     */
    private ExternalDocumentation externalDocumentation = new ExternalDocumentation();

    @Data
    static class Contact {
        private String name;
        private String url;
        private String email;
        private Map<String, Object> extensions = new HashMap<>();
    }

    @Data
    static class License {
        private String name;
        private String url;
        private Map<String, Object> extensions;
    }

    @Data
    static class ExternalDocumentation {
        private String description;
        private String url;
        private Map<String, Object> extensions = new HashMap<>();
    }

}
