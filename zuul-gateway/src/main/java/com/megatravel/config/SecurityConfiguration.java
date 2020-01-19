package com.megatravel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http
        	.csrf()
        	.disable();

        // No session will be created or used by spring security
        http
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http
	    	.authorizeRequests()
	    	.anyRequest()
		    .authenticated();

//        // If a user try to access a resource without having enough permissions
//        http
//        	.exceptionHandling()
//        	.accessDeniedPage("/login");

        // Optional, if you want to test the API from a browser
        http
        	.httpBasic()
        	.disable();
        
        //https
        http
        	.requiresChannel()
        	.anyRequest()
        	.requiresSecure();
        
        //XSS
        http
        	.headers()
        	.contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*/")//
        		.antMatchers(HttpMethod.OPTIONS, "/**")
        		.antMatchers(HttpMethod.POST, "/**")
        		.antMatchers(HttpMethod.PUT, "/**")
        		.antMatchers(HttpMethod.DELETE, "/**")
        		.antMatchers(HttpMethod.GET, "/**");
    } 

}
