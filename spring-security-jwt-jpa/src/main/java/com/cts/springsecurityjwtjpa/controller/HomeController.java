package com.cts.springsecurityjwtjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.springsecurityjwtjpa.JwtUtil;
import com.cts.springsecurityjwtjpa.model.AuthenticationRequest;
import com.cts.springsecurityjwtjpa.model.AuthenticationResponse;
import com.cts.springsecurityjwtjpa.model.UserDto;
import com.cts.springsecurityjwtjpa.service.CustomUserDetailsService;

@RestController
public class HomeController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	@GetMapping("/hello")
	public String home() {
		return "Hello User";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect Username & Password : " + e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}
	
	@PostMapping("/register")
	public ResponseEntity saveUser(@RequestBody UserDto user) throws Exception{
		return ResponseEntity.ok(userDetailsService.save(user));
	}
}
