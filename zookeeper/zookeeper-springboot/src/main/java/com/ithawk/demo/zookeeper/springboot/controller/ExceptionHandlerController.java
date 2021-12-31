package com.ithawk.demo.zookeeper.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object exceptionHandler(RuntimeException e){

        Map<String,Object> result=new HashMap<>(  );
        result.put( "status","error" );
        result.put( "message",e.getMessage() );
        return result;
    }


}
