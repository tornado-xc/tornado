# 短信通知服务

## 1. 数据库设计

```sql
CREATE TABLE `note_template`
(
    `id`            bigint       NOT NULL COMMENT '模板id',
    `app_id`        varchar(255) DEFAULT NULL COMMENT '应用id',
    `app_name`      varchar(255) DEFAULT NULL COMMENT '应用名',
    `code`          varchar(50)  DEFAULT NULL COMMENT '模板编码',
    `content`       varchar(255) NOT NULL COMMENT '模板内容',
    `business_type` varchar(50)  NOT NULL COMMENT '业务类型',
    `sign_name`     varchar(20)  NOT NULL COMMENT '签名',
    `type`          tinyint      NOT NULL COMMENT '模板类型1、验证码；2、通知',
    `platform`      tinyint      NOT NULL COMMENT '短信通道平台：1、阿里云；2、腾讯云；10、其他',
    `deleted`       tinyint      DEFAULT '0' COMMENT '删除标记：0、未删除；1、已删除',
    `create_time`   datetime     NOT NULL COMMENT '创建时间',
    `update_time`   datetime     NOT NULL COMMENT '更新时间',
    `description` varchar(500)   DEFAULT NULL COMMENT '模板说明',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信模板';
```
