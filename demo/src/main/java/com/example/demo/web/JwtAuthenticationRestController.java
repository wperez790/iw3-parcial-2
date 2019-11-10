package com.example.demo.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.business.BusinessException;
import com.example.demo.business.IUsuarioBusiness;
import com.example.demo.business.NotFoundException;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.Usuario;
import com.example.demo.model.dto.JwtRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationRestController {
	
	/*@Autowired
	private AuthenticationManager authenticationManager;*/
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IUsuarioBusiness usuarioBusiness;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "/loginJwt")
	public ResponseEntity<String> login(@RequestBody JwtRequest authenticationRequest){
		Usuario u = null;
		try{
			u = usuarioBusiness.load(authenticationRequest.getName());
		}catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(u != null) {
			boolean isValid = passwordEncoder.matches(
					authenticationRequest.getPassword(), 
					u.getPassword());
			if(!isValid)
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getName());
		return new ResponseEntity<String>(getUserJson(authenticationRequest.getName(), token),HttpStatus.OK);
	}
	
	private String getUserJson(String username, String token) {
		JSONObject js = new JSONObject();
		js.put("username", username);
		js.put("jwtToken", token);
		
		return js.toString();
		
	}
}