package com.ithawk.demo.spring5.webflux.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 自定义异常处理器：当异常发生时返回400，并返回异常信息。
 */
@Component
@Order(-99)
public class CumstomExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String message = this.formatExceptionMassage(ex);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    private String formatExceptionMassage(Throwable ex) {
        String msg = "发生异常：" + ex.getMessage();
        if(ex instanceof StudentException) {
            StudentException e = (StudentException) ex;
            msg = msg + "【" + e.getField() + ":" + e.getValue() + "】";
        }
        return msg;
    }
}
