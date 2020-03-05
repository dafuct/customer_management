package com.nixsolutions.controller.servlet;

import com.nixsolutions.dao.JdbcUserDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.Role;
import com.nixsolutions.entity.User;
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
    userDao = new JdbcUserDao();
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

    User user = userDao.findByLogin(login);

    if (user == null) {
      req.setAttribute("error", "Login or Password wrong");
      req.getRequestDispatcher("views/login.jsp").forward(req, resp);
      return;
    }

    if (!user.getLogin().equals(login) || !user.getPassword().equals(password)) {
      req.setAttribute("error", "Login or Password wrong");
      req.getRequestDispatcher("views/login.jsp").forward(req, resp);
      return;
    }

    if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
      Role role = user.getRole();
      if (role.getName().equals("admin")) {
        session.setAttribute("admin", user);
        resp.sendRedirect(req.getContextPath() + "/admin");
        return;
      }

      if (role.getName().equals("user")) {
        session.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/user");
      }
    }
  }
}