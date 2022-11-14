package com.ithawk.demo.springcloud.gateway.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class XinyueAccountAuthentication extends AbstractAuthenticationToken{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object credentials;
    private String principal;
    
    public XinyueAccountAuthentication(Object credentials,String principal,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public String getPrincipal() {
        return this.principal;
    }

}
