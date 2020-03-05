package com.nixsolutions.controller.servlet;

import com.nixsolutions.dao.JdbcUserDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;
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
    userDao = new JdbcUserDao();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    User user = (User) req.getSession().getAttribute("admin");
    String login = req.getParameter("user_login");
    if (user.getLogin().equals(login)) {
      req.getSession().setAttribute("error", "You can't delete yourself!");
      resp.sendRedirect(req.getContextPath() + "/admin");
    } else {
      User userToDelete = new User(login);
      userDao.remove(userToDelete);
      resp.sendRedirect(req.getContextPath() + "/admin");
    }
  }
}
