package com.ithawk.demo.springcloud.gateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFallbackController {

    @GetMapping("/fallback")
    public String fallbackHandler() {
        return "This is the Gateway Fallback";
    }
}
