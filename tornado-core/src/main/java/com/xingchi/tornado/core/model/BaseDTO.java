package com.xingchi.tornado.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础DTO
 *
 * @author xingchi
 * @date 2024/1/7 19:46
 */
@Data
public abstract class BaseDTO implements Serializable {

    @Schema(name = "id", description = "id")
    private String id;


}
