package com.xingchi.tornado.sms.exception;

import com.xingchi.tornado.core.handler.DefaultGlobalExceptionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * sms 模块全局异常处理
 *
 * @author xingchi
 * @date 2023/6/11 17:18
 * @modified xingchi
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class SmsGlobalExceptionHandler extends DefaultGlobalExceptionHandler {
}
