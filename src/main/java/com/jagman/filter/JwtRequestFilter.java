package com.jagman.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jagman.config.JwtHelper;
import com.jagman.model.Person;
import com.jagman.services.userDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
	
    @Autowired
	private UserDetailsService userDetailsService;
    @Autowired
    private JwtHelper jwtHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
		String token = null;
		String email = null;
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) { 
			token = authHeader.substring(7); 
			email = jwtHelper.extractUserEmail(token); 
		} 
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			var userDetails = userDetailsService.loadUserByUsername(email);

			if(jwtHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);		
	}

}
