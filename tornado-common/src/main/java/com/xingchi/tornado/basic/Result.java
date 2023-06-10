package com.xingchi.tornado.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 结果返回对象工厂
 *
 * @author xingchi
 * @date 2022/8/30 19:56
 * @modified xingchi
 */
@Data
@Schema(name = "通用返回结果")
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    @Schema(name = "响应码", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer code;

    /**
     * 提示消息
     */
    @Schema(name = "提示消息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    /**
     * 操作状态
     */
    @Schema(name = "操作状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean success;

    @Schema(name = "数据载体", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;

    /**
     *
     * <p>
     *     <li>全参构造</li>
     *     <li>定义反序列化时的字段对应关系</li>
     * </p>
     *
     * @param code          状态码
     * @param message       提示消息
     * @param success       是否成功
     * @param data          数据载体
     */
    @JsonCreator
    private Result(@JsonProperty(value = "code") Integer code,
                  @JsonProperty(value = "message") String message,
                  @JsonProperty(value = "success") Boolean success,
                  @JsonProperty(value = "data") T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    private Result(BaseCode baseCode, T data) {
        this(baseCode.code(), baseCode.message(), baseCode.success(), data);
    }

    private Result(BaseCode baseCode) {
        this(baseCode, null);
    }

    private Result(T data) {
        this(CommonCode.SUCCESS, data);
    }

    protected Result() {
        this((T) null);
    }


    /**
     * 请求成功
     *
     * @param <T>       返回值参数
     * @return          成功状态
     */
    public static <T> Result<T> ok() {
       return new Result<>(CommonCode.SUCCESS);
    }

    /**
     * 请求成功，返回数据
     *
     * @param data      数据载荷
     * @param <T>       返回载荷类型
     * @return          响应数据
     */
    public static <T> Result<T> ok(T data) {
       return new Result<>(data);
    }

    /**
     * 程序运行失败
     *
     * @param code      状态码
     * @param <T>       数据类型
     * @return          失败错误码
     */
    public static <T> Result<T> fail() {
        return new Result<>(CommonCode.FAIL);
    }

    /**
     * 程序运行失败
     *
     * @param code      状态码
     * @param <T>       数据类型
     * @return          失败错误码
     */
    public static <T> Result<T> fail(BaseCode code) {
       return new Result<>(code);
    }

    /**
     * 程序运行失败
     *
     * @param code      状态码
     * @param <T>       数据类型
     * @param data      失败响应数据
     * @return          失败错误码
     */
    public static <T> Result<T> fail(BaseCode code, T data) {
        return new Result<>(code, data);
    }


}
