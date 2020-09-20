package com.my.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.api.JwtUtils;

// @Component -> @ServletComponentScan for enable urlPatterns
// @Component
@WebFilter(filterName="mainFilter",urlPatterns="/api/*")
public class MyFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		System.out.println("BEGIN MyFilter::doFilter,  Remote Address: " + request.getRemoteAddr());
		
		// chain.doFilter(request, response);
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		if (req.getMethod().equals("OPTIONS"))
		{
			chain.doFilter(request, response);
			return;
		}
		
//		resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//		resp.setHeader("Access-Control-Allow-Methods", req.getMethod());
//		resp.setHeader("Access-Control-Max-Age", "3600");
//	    resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
//	    resp.setHeader("Content-Type", "application/json;charset=utf-8");
		
		System.out.println(req.getMethod());
//		Enumeration<String> em = req.getHeaderNames();
//		while(em.hasMoreElements())
//		{
//			String key = em.nextElement();
//			System.out.println(key + "==>" + req.getHeader(key));
//		}
		
		String token = req.getHeader("Authorization");
//		System.out.println("token=" + token);
		Map<String, Object> result = JwtUtils.validateToken(token);
		System.out.println("result: " + result);
		if (!result.get("ERR_MSG").equals("ERR_MSG_OK"))
		{
			System.out.println("[MyFilter]  sendRedirect: http://localhost:9090/login");
			resp.sendRedirect("http://localhost:9090/login");
		}
		else
		{
			chain.doFilter(request, response);
		}
		
		System.out.println("END MyFilter::doFilter,  Remote Address: " + request.getRemoteAddr());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

	
}
