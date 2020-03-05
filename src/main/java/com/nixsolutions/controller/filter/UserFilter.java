package com.nixsolutions.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/user"})
public class UserFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpSession session = httpRequest.getSession(false);

    boolean isLoggedInAdmin = (session != null && session.getAttribute("user") != null);

    String loginURI = httpRequest.getContextPath() + "/login";

    boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

    boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

    if (isLoggedInAdmin && (isLoginRequest || isLoginPage)) {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
      dispatcher.forward(request, response);
    } else if (isLoggedInAdmin || isLoginRequest) {
      chain.doFilter(request, response);
    } else {
      RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp");
      dispatcher.forward(request, response);
    }
  }
}
