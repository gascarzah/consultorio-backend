package com.gafahtec.consultorio.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {

//	private final List<String> allowedOrigins = Arrays.asList("*");
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200",
	                                                          "https://gascarzah.com",
	                                                          "http://localhost:3000",
	                                                          "http://localhost:5173",
	                                                          "http://127.0.0.1:5173",
	                                                          "http://172.25.208.1:5173",
	                                                          "http://192.168.1.15:5173"
	                                                          );
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		 String origin = request.getHeader("Origin");
//		 System.out.println(origin);
         response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
//         response.setHeader("Vary", "Origin");
         response.setHeader("Access-Control-Allow-Credentials", "true");
		
//		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
		// chain.doFilter(req, res);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
}