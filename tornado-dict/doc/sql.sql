-- 字典类型表
CREATE TABLE dict_type
(
    id          BIGINT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        VARCHAR(64)            NOT NULL COMMENT '字典类型名称',
    code        VARCHAR(64)            NOT NULL COMMENT '字典类型编码',
    description VARCHAR(500) DEFAULT NULL COMMENT '字典类型描述',
    create_by   BIGINT(20)   DEFAULT NULL COMMENT '创建者',
    create_time DATETIME               NOT NULL COMMENT '创建时间',
    update_by   BIGINT(20)   DEFAULT NULL COMMENT '创建者',
    update_time DATETIME               NOT NULL COMMENT '更新时间',
    deleted     tinyint      default 0 null comment '删除标记：0、未删除；1、已删除',
    PRIMARY KEY (id),
    UNIQUE KEY idx_dic_type_code (code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典类型表';


-- 字典项表
CREATE TABLE dict_data
(
    id           BIGINT(20)           NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name         VARCHAR(64)          NOT NULL COMMENT '字典项名称',
    code         VARCHAR(64)          NOT NULL COMMENT '字典项编码',
    dict_type_id BIGINT(20)           NOT NULL COMMENT '字典类型ID',
    sort         INT(11)              NOT NULL COMMENT '排序',
    create_by    BIGINT(20) DEFAULT NULL COMMENT '创建者',
    create_time  DATETIME             NOT NULL COMMENT '创建时间',
    update_by    BIGINT(20) DEFAULT NULL COMMENT '创建者',
    update_time  DATETIME             NOT NULL COMMENT '更新时间',
    deleted      tinyint    default 0 null comment '删除标记：0、未删除；1、已删除',
    PRIMARY KEY (id),
    KEY idx_dict_type_id (dict_type_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典项表';



