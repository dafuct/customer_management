package com.nixsolutions.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class Error extends HttpServlet {

  private static final long serialVersionUID = -6029804010912957300L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    handleRequest(req);
    req.getRequestDispatcher("views/error.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }

  private void handleRequest(HttpServletRequest req) {
    Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
    String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
    String message = (String) req.getAttribute("javax.servlet.error.message");
    Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
    String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");

    req.setAttribute("statusCode" , code);
    req.setAttribute("exceptionType" , throwable.getClass().getName());
    req.setAttribute("requestUri" , requestUri);
    req.setAttribute("message" , message);
    req.setAttribute("exception" , throwable.getMessage());
    req.setAttribute("servletName" , servletName);
  }
}
