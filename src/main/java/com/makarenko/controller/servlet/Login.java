package com.makarenko.controller.servlet;

import com.makarenko.dao.UserDao;
import com.makarenko.dao.hibernate.HibernateUserDao;
import com.makarenko.entity.Role;
import com.makarenko.entity.Client;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {

  private static final long serialVersionUID = 7656096123661049956L;
  private UserDao userDao;

  @Override
  public void init() {
    userDao = new HibernateUserDao();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("views/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    HttpSession session = req.getSession(true);

    Client client = userDao.findByLogin(login);

    if (client == null) {
      req.setAttribute("error", "Login or Password wrong");
      req.getRequestDispatcher("views/login.jsp").forward(req, resp);
      return;
    }

    if (!client.getLogin().equals(login) || !client.getPassword().equals(password)) {
      req.setAttribute("error", "Login or Password wrong");
      req.getRequestDispatcher("views/login.jsp").forward(req, resp);
      return;
    }

    if (client.getLogin().equals(login) && client.getPassword().equals(password)) {
      Role role = client.getRole();
      if (role.getName().equals("admin")) {
        session.setAttribute("admin", client);
        resp.sendRedirect(req.getContextPath() + "/admin");
        return;
      }

      if (role.getName().equals("client")) {
        session.setAttribute("client", client);
        resp.sendRedirect(req.getContextPath() + "/client");
      }
    }
  }
}