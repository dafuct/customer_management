package com.nixsolutions.controller.servlet;

import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.dao.hibernate.HibernateRoleDao;
import com.nixsolutions.dao.hibernate.HibernateUserDao;
import com.nixsolutions.entity.Client;
import com.nixsolutions.entity.Role;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;

@WebServlet("/edit")
public class EditUser extends HttpServlet {

  private static final long serialVersionUID = 7955655058898397308L;
  private UserDao userDao;
  private RoleDao roleDao;

  @Override
  public void init() {
    userDao = new HibernateUserDao();
    roleDao = new HibernateRoleDao();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String login = req.getParameter("login");
    Client byLogin = userDao.findByLogin(login);

    if (byLogin == null) {
      req.setAttribute("error", "There is no such user");
      req.getRequestDispatcher("views/admin.jsp").forward(req, resp);
      return;
    }

    req.setAttribute("id", byLogin.getId());
    req.setAttribute("login", byLogin.getLogin());
    req.setAttribute("password", byLogin.getPassword());
    req.setAttribute("passwordAgain", byLogin.getPassword());
    req.setAttribute("email", byLogin.getEmail());
    req.setAttribute("firstName", byLogin.getFirstName());
    req.setAttribute("lastName", byLogin.getLastName());
    req.setAttribute("birthday", byLogin.getBirthday());
    req.setAttribute("roleName", byLogin.getRole().getName());

    req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    Long id = Long.parseLong(req.getParameter("id"));
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    String passwordAgain = req.getParameter("passwordAgain");
    String email = req.getParameter("email");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String birthday = req.getParameter("birthday");
    String roleName = req.getParameter("role");

    req.setAttribute("id", id);
    req.setAttribute("login", login);
    req.setAttribute("password", password);
    req.setAttribute("passwordAgain", passwordAgain);
    req.setAttribute("email", email);
    req.setAttribute("firstName", firstName);
    req.setAttribute("lastName", lastName);
    req.setAttribute("birthday", birthday);
    req.setAttribute("roleName", roleName);

    if (password.isEmpty() || passwordAgain.isEmpty() || email.isEmpty() || firstName.isEmpty()
        || lastName.isEmpty() || birthday.isEmpty()) {
      req.setAttribute("error", "All fields must be inputted");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    LocalDate birthDate;
    try {
      birthDate = LocalDate.parse(birthday);
    } catch (DateTimeParseException e) {
      req.setAttribute("error", "Invalid date format");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    if (birthDate.isAfter(LocalDate.now())) {
      req.setAttribute("error", "Date must not be in future");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    if (!password.equals(passwordAgain)) {
      req.setAttribute("error", "Passwords not equal");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    EmailValidator validator = EmailValidator.getInstance();
    boolean isValidEmail = validator.isValid(email);
    if (!isValidEmail) {
      req.setAttribute("error", "Invalid email");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    Client byLogin = userDao.findByLogin(login);
    Client byEmail = userDao.findByEmail(email);
    if (byEmail != null && !byLogin.equals(byEmail)) {
      req.setAttribute("error", "Client with this email already exists");
      req.getRequestDispatcher("views/edit.jsp").forward(req, resp);
      return;
    }

    Role roleUser = roleDao.findByName(roleName);
    Client client = new Client(id, firstName, lastName, login, password, email, birthDate, roleUser);
    userDao.update(client);

    resp.sendRedirect(req.getContextPath() + "/admin");
  }
}
