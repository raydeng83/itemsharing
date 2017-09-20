package com.itemsharing.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by z00382545 on 9/19/17.
 */
@Configuration
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {
//            "/v1/user/**"
//            "/v1/user"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
//                antMatchers("/**").
        antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();
    }
}
