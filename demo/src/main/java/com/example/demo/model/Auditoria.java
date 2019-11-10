package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Auditoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date tiempo;
	
	private String uri;
	
	private String metodo;
	
	@Column(columnDefinition = "BLOB",name= "parametros_json")
	@Lob
	private String parametrosJSON;
	
	@Column(columnDefinition = "BLOB",name= "headers_json")
	@Lob
	private String headersJSON;
	
	private String tipoToken;
	
	private Boolean tokenValido;
	
	@Column(columnDefinition = "TEXT")
	private String descripcion;
	
	private String username;
	
	@Column(columnDefinition = "MEDIUMBLOB")
	@Lob	
	private String body;
	
	public static String JWT_TOKEN ="jwt";
	public static String CUSTOM_TOKEN = "custom";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTiempo() {
		return tiempo;
	}

	public void setTiempo(Date tiempo) {
		this.tiempo = tiempo;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getParametrosJSON() {
		return parametrosJSON;
	}

	public void setParametrosJSON(String parametrosJSON) {
		this.parametrosJSON = parametrosJSON;
	}

	public String getHeadersJSON() {
		return headersJSON;
	}

	public void setHeadersJSON(String headersJSON) {
		this.headersJSON = headersJSON;
	}

	public String getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(String tipoToken) {
		this.tipoToken = tipoToken;
	}

	public Boolean getTokenValido() {
		return tokenValido;
	}

	public void setTokenValido(Boolean tokenValido) {
		this.tokenValido = tokenValido;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
