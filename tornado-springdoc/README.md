## tornado-springdoc

#### 1. 背景

> 由于Swagger已经很久没有维护，与tornado使用的SpringBoot 2.6.x版本出现兼容性问题，因此寻找Swagger的替代品因此，找到了SpringDoc，SpringDoc相对于Springfox社区更加活跃，并且直接OpenAPI 3.0规范，因此选择该框架作为API管理，并开发了这个Spring starter，该starter只是在原SpringDoc进行增强，添加了基于注解方式的分组文档管理

#### 2. 主要内容

* 添加了`ApiGroup`注解用于API文档管理，无需在手动添加其他配置，实现基于注解方式的配置
* 添加了根据 扫描包名进行分组，更加清晰，同时也不影响SpringDoc的分组配置

#### 3. 具体使用

* 基于注解的API分组管理，只需要指定的RequestMapping上添加ApiGroup注解即可

  ```java
  @ApiGroup(group = "1.0.0")
  @GetMapping("/list/all")
  @Operation(summary = "查询所有的短信模板信息", description = "查询所有的短信模板信息不分页")
  public Result<List<NoteTemplateVO>> findAll() {
      // 查询所有短信模板
      return Result.ok(noteTemplateService.findAll());
  }
  ```

* 基于简单分组的配置

  ```yaml
  springdoc:
    api-docs:
      enabled: true
    group-configs:
      - display-name: 111
        group: 1112ss
        packages-to-scan:
          - com.xingchi
    # 自定义配置
    tornado:
      doc:
        version: 1.0.0
        description: 短信服务接口
        contact:
          name: xingchi
          email: 1710518450@qq.com
          url: https://gitee.com/tornado-xc/tornado.git
        title: 短信服务接口
        groups:
          - package-name: com.xingchi
            group-name: 所有接口
          - package-name: com.xingchi.tornado.sms.controller1
            group-name: 模板接口
        default-group: true
        default-group-name: 全部接口
  ```

  
