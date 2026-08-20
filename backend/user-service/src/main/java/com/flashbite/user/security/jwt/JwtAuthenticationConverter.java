package com.flashbite.user.security.jwt;

import com.flashbite.common.security.JwtClaims;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


import java.util.Collection;
import java.util.List;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String role = jwt.getClaimAsString(JwtClaims.ROLE);
            if(role == null || role.isBlank()){
                return new JwtAuthenticationToken(jwt , List.of());
            }

        Collection<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_"+role)
        );

            return new JwtAuthenticationToken(jwt , authorities);

    }
}
