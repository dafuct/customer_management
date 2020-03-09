package com.nixsolutions.controller.servlet;

import com.nixsolutions.dao.UserDao;
import com.nixsolutions.dao.hibernate.HibernateUserDao;
import com.nixsolutions.entity.Client;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class Admin extends HttpServlet {

  private static final long serialVersionUID = 4741762862288630405L;
  private UserDao userDao;

  @Override
  public void init() {
    userDao = new HibernateUserDao();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<Client> list = userDao.findAll();
    req.setAttribute("list", list);
    req.getRequestDispatcher("views/admin.jsp").forward(req, resp);
  }
}
