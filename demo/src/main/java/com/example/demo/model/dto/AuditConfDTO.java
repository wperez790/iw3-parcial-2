package com.example.demo.model.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuditConfDTO {
	
	@Value("${com.example.demo.audit:true}")
	private Boolean auditEnable;
	
	@Value("${com.example.demo.audit.headers:*}")
	private String auditHeaders;
	
	@Value("${com.example.demo.audit.body:true}")
	private Boolean auditBody;
	
	@Value("${com.example.demo.audit.body.maxSize:1 Kb}")
	private String auditBodySize;	
	
	@Value("${com.example.demo.audit.pattern:*}")
	private String auditRequests;
	
	/*@Value("${com.example.demo.audit.requests:*}")
	private String auditRequests;*/
			
	public AuditConfDTO() {}
	
	public AuditConfDTO(Boolean auditEnable, String auditHeaders, 
			Boolean auditBody, String auditBodySize, String auditRequests) {
		this.auditEnable = auditEnable;		
		this.auditHeaders = auditHeaders;
		this.auditBody = auditBody;		
		this.auditBodySize = auditBodySize;
		this.auditRequests = auditRequests;
	}
	
	

	public String getAuditRequests() {
		return auditRequests;
	}

	public void setAuditRequests(String auditRequests) {
		this.auditRequests = auditRequests;
	}

	public Boolean getAuditEnable() {
		return auditEnable;
	}

	public void setAuditEnable(Boolean auditEnable) {
		this.auditEnable = auditEnable;
	}

	public String getAuditHeaders() {
		return auditHeaders;
	}

	public void setAuditHeaders(String auditHeaders) {
		this.auditHeaders = auditHeaders;
	}

	public Boolean getAuditBody() {
		return auditBody;
	}

	public void setAuditBody(Boolean auditBody) {
		this.auditBody = auditBody;
	}

	public String getAuditBodySize() {
		return auditBodySize;
	}

	public void setAuditBodySize(String auditBodySize) {
		this.auditBodySize = auditBodySize;
	}	
}


