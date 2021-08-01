package com.ithawk.demo.springcloud.gateway.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;
//参考AuthorityReactiveAuthorizationManager实现
public class XinyueReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext>  {

    private List<String> authorities = new ArrayList<>();
    
    public XinyueReactiveAuthorizationManager(String authority, String... authorities ) {
        this.authorities.add("ROLE_" + authority);
        if(authorities != null) {
            for(String auth : authorities) {
               this.authorities.add("ROLE_" + auth);
            }
        }
    }
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable( a -> a.getAuthorities())
                .map( g-> g.getAuthority())
                .any(c->{
                    //检测权限是否匹配
                    String[] roles = c.split(",");
                    for(String role:roles) {
                        if(authorities.contains(role)) {
                            return true;
                        }
                    }
                    return false;
                })
                .map( hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
