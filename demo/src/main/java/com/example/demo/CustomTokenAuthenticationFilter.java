package com.example.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.business.BusinessException;
import com.example.demo.business.IAuthTokenService;
import com.example.demo.business.NotFoundException;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.AuthToken;
import com.example.demo.model.Usuario;
import com.example.demo.persistence.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;



public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	
	public CustomTokenAuthenticationFilter(IAuthTokenService authTokenService, UsuarioRepository usuariosDAO) {
		super();
		this.authTokenService = authTokenService;
		this.usuariosDAO = usuariosDAO;
	}

	private IAuthTokenService authTokenService;

	
	private UsuarioRepository usuariosDAO;

	public static String ORIGIN_TOKEN_TOKEN = "token";
	public static String ORIGIN_TOKEN_HEADER = "header";
	
	public static String AUTH_JWT_HEADER= "JWT-HEADER";
	public static String AUTH_JWT_PARAMETER= "jwt";

	public static String AUTH_HEADER = "X-AUTH-TOKEN";
	public static String AUTH_HEADER1 = "XAUTHTOKEN";
	public static String AUTH_PARAMETER = "xauthtoken";
	public static String AUTH_PARAMETER1 = "token";
	
	
	//public static String ATTR_SESSION_NOT_CREATION = "ATTR_SESSION_NOT_CREATION";

	private boolean esValido(String valor) {
		return valor != null && valor.trim().length() > 10;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		final String jwtToken = request.getHeader(AUTH_JWT_HEADER);
		String username = null;
	
		
		if (jwtToken != null) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				log.error("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.error("JWT Token has expired");
			}
		} 
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				chain.doFilter(request, response);
				return;
			}
			
		}

	
/*CUSTOM TOKEN*/
		
		String parameter = request.getParameter(AUTH_PARAMETER);
		if (!esValido(parameter)) {
			parameter = request.getParameter(AUTH_PARAMETER1);
		}
		String header = request.getHeader(AUTH_HEADER);
		if (!esValido(header)) {
			header = request.getHeader(AUTH_HEADER1);
		}
		if (!esValido(parameter) && !esValido(header) ) {
			chain.doFilter(request, response);
			return;
		}
		String token = "";
		if (esValido(parameter)) {
			token=parameter;
			log.trace("Token recibido por query param="+token);
		} else {
			token=header;
			log.trace("Token recibido por header="+token);
		}
		String[] tokens = null;

		try {
			tokens = AuthToken.decode(token);
			if (tokens.length != 2) {
				chain.doFilter(request, response);
				return;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			chain.doFilter(request, response);
			return;
		}

		// A partir de aquí, se considera que se envió el el token y es propritario, por
		// ende si no está ok, login inválido
		AuthToken authToken = null;
		try {
			authToken = authTokenService.load(tokens[0]);
		} catch (NotFoundException e) {
			SecurityContextHolder.clearContext();
			//throw new ServletException("No existe el token=" + token);
			log.debug("No existe el token=" + token);
			chain.doFilter(request, response);
			return;
		} catch (BusinessException e) {
			SecurityContextHolder.clearContext();
			log.error(e.getMessage(), e);
			chain.doFilter(request, response);
			return;
			//throw new ServletException(e);
		}

		if (!authToken.valid()) {
			try {
				if (authToken.getType().equals(AuthToken.TYPE_DEFAULT)
						|| authToken.getType().equals(AuthToken.TYPE_TO_DATE)
						|| authToken.getType().equals(AuthToken.TYPE_REQUEST_LIMIT)) {
					authTokenService.delete(authToken);
				}
				if (authToken.getType().equals(AuthToken.TYPE_FROM_TO_DATE)) {
					if (authToken.getTo().getTime() < System.currentTimeMillis()) {
						authTokenService.delete(authToken);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			SecurityContextHolder.clearContext();
			log.debug("El Token "+token+" ha expirado");
			//throw new ServletException("El Token ha expirado. Token=" + token);
			chain.doFilter(request, response);
			return;
		}

		try {
			authToken.setLast_used(new Date());
			authToken.addRequest();
			authTokenService.save(authToken);

			username = authToken.getUsername();
			List<Usuario> lu = usuariosDAO.findByUsername( username);
			if(lu.size()==1) {
				log.trace("Token para usuario {} ({}) [{}]",lu.get(0).getUsername(),token,request.getRequestURI());
				lu.get(0).setSessionToken(token);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(lu.get(0), null,lu.get(0).getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			//request.setAttribute(ATTR_SESSION_NOT_CREATION, "true");
			} else {
				log.debug("No se encontró el usuario {} por token",username);
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			chain.doFilter(request, response);
		}

	}
}