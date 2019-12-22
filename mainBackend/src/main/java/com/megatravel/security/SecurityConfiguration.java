package com.megatravel.security;

import javax.ws.rs.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsImplementation userDetailsService;
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;
	    
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

        // If a user try to access a resource without having enough permissions
        http
        	.exceptionHandling()
        	.accessDeniedPage("/signin")
        	.authenticationEntryPoint(unauthorizedHandler);
;

        // Apply JWT
        http
        	.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
       
        // Optional, if you want to test the API from a browser
        http
        	.httpBasic();
      
        //XSS
        http
        	.headers()
        	.contentSecurityPolicy("script-src 'self' https://localhost:4200; object-src https://localhost:4200; report-uri /csp-report-endpoint/");
        
        //https
        http
        	.requiresChannel()
        	.anyRequest()
        	.requiresSecure();
        
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow eureka client to be accessed without authentication
        web.ignoring().antMatchers("/*/")//
                .antMatchers("/eureka/**")//
                .antMatchers(HttpMethod.OPTIONS, "/**"); // Request type options should be allowed.
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 
    

}
