package com.gonwan.springcloud.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
 * The new OAuth2 support in Spring are actively developing. All functions are merging into core Spring Security 5.
 * As a result, current implementation is suppose to change. See:
 * - https://spring.io/blog/2018/01/30/next-generation-oauth-2-0-support-with-spring-security
 * - https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer  /* required for the controller to work */
@RestController
public class AuthenticationApplication {

    /**
     * See: UserInfoTokenServices#setPrincipalExtractor() and UserInfoTokenServices#setAuthoritiesExtractor().
     */
    @GetMapping("/user")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getPrincipal());
        userInfo.put("username", user.getName());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getAuthorities()));
        return userInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
