package com.example.seedlist.config;

import com.example.seedlist.dto.Result;
import com.example.seedlist.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ResultAdvice {

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException:", e);
        return new Result(ResultCode.PARAM_ERROR.getCode(), getBindingResultErrors(e.getBindingResult()));
    }

    private String getBindingResultErrors(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                sb.append(error.getDefaultMessage()).append(",");
            }
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }
}
