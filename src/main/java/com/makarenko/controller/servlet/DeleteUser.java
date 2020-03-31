package com.makarenko.controller.servlet;

import com.makarenko.dao.UserDao;
import com.makarenko.dao.hibernate.HibernateUserDao;
import com.makarenko.entity.Client;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteUser extends HttpServlet {

  private static final long serialVersionUID = 4700877670298792944L;
  private UserDao userDao;

  @Override
  public void init() {
    userDao = new HibernateUserDao();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    Client client = (Client) req.getSession().getAttribute("admin");
    Long id = Long.parseLong(req.getParameter("id"));
    if (client.getId().equals(id)) {
      req.getSession().setAttribute("error", "You can't delete yourself!");
      resp.sendRedirect(req.getContextPath() + "/admin");
    } else {
      Client clientToDelete = new Client(id);
      userDao.remove(clientToDelete);
      resp.sendRedirect(req.getContextPath() + "/admin");
    }
  }
}
