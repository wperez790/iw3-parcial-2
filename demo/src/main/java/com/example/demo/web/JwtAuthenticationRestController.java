package com.example.demo.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.dto.JwtRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationRestController {
	
	/*@Autowired
	private AuthenticationManager authenticationManager;*/
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
		
	@PostMapping(value = "/loginJwt")
	public ResponseEntity<String> login(@RequestBody JwtRequest authenticationRequest){
		//authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getName());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<String>(getUserJson(authenticationRequest.getName(), token),HttpStatus.OK);
	}
	
	private String getUserJson(String username, String token) {
		JSONObject js = new JSONObject();
		js.put("username", username);
		js.put("jwtToken", token);
		
		return js.toString();
		
	}
	
	/*private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}*/
}