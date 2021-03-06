package com.example.demo.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private String body;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = "";
		BufferedReader bufferedReader = request.getReader();			
		String line;
		while ((line = bufferedReader.readLine()) != null){
			body += line;
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
		return new ServletInputStream() {
			
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}
		};			
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
}
