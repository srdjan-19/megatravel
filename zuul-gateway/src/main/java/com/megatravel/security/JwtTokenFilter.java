package com.megatravel.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.megatravel.exception.ExceptionResponse;

import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null) {
        	
            try {
                jwtTokenProvider.validateToken(token) ;
            } catch (JwtException | IllegalArgumentException e) {
            	System.out.println("ODJE");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
                throw new ExceptionResponse("Invalid JWT token",HttpStatus.UNAUTHORIZED);
            }
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            //setting auth in the context.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);

	}

}
