package com.cts.gyan.hotel.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.gyan.hotel.exception.CustomerNotFoundException;
import com.cts.gyan.hotel.model.CustomerDetails;
import com.cts.gyan.hotel.service.CustomerDetailsService;
import com.cts.gyan.hotel.service.JwtService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomerDetailsService customerDetailsService;

	@Autowired
	private JwtService jwtService;

	/**
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, CustomerNotFoundException {

		final String jwtRequestHeader = request.getHeader("Authorization");

		log.info("Inside JwtRequestFilter : {}", request.getRequestURI());

		String jwt = null, emailAddress = null;
		if (jwtRequestHeader != null && jwtRequestHeader.startsWith("Bearer ")) {
			jwt = jwtRequestHeader.substring(7);
			try {
				emailAddress = jwtService.extractEmailAddress(jwt);
				log.info("Successfully obtained emailAddress : ({}) from JWT", emailAddress);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			log.error("", jwt);
		}

		if (emailAddress != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			CustomerDetails customerDetails = customerDetailsService.loadUserByUsername(emailAddress);
			if (jwtService.validateToken(jwt, customerDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						customerDetails, null, customerDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				log.info("Successfully obtained and validated JWT :: {}", jwt);
			} else {
				log.error("Validation failed for JWT :: {}", jwt);
			}
		} else {
			log.error("", jwt);
		}
		log.info("-------- Exiting JwtRequestFilter");
		filterChain.doFilter(request, response);
	}
}
