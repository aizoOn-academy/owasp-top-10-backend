package it.aizoon.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();
	
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
    	if(exception instanceof DisabledException) {
    		defaultRedirectStrategy.sendRedirect(request, response, "/login-disabled");
    		return;
    	}
    	if(exception.getCause() instanceof LockedException) {
    		defaultRedirectStrategy.sendRedirect(request, response, "/login-locked");
    		return;
    	}
    	defaultRedirectStrategy.sendRedirect(request, response, "/login-error");
    }
}
