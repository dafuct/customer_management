package com.makarenko.controller.servlet;

import com.makarenko.dao.RoleDao;
import com.makarenko.dao.UserDao;
import com.makarenko.dao.hibernate.HibernateRoleDao;
import com.makarenko.dao.hibernate.HibernateUserDao;
import com.makarenko.entity.Client;
import com.makarenko.entity.Role;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;

@WebServlet("/add")
public class AddUser extends HttpServlet {

  private static final long serialVersionUID = 2568408169733935285L;
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
    req.getRequestDispatcher("views/add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    String login = req.getParameter("login");
    String password = req.getParameter("password");
    String passwordAgain = req.getParameter("passwordAgain");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    String birthday = req.getParameter("birthday");
    String role = req.getParameter("role");

    if (login.isEmpty() || password.isEmpty() || passwordAgain.isEmpty() ||
        email.isEmpty() || firstName.isEmpty() ||
        lastName.isEmpty() || birthday.isEmpty()) {
      req.setAttribute("error", "All fields must be inputted");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    if (!password.equals(passwordAgain)) {
      req.setAttribute("error", "Passwords not equals");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    EmailValidator validator = EmailValidator.getInstance();
    boolean isValidEmail = validator.isValid(email);
    if (!isValidEmail) {
      req.setAttribute("error", "Invalid email");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    LocalDate birthDate;
    try {
      birthDate = LocalDate.parse(birthday);
    } catch (DateTimeParseException e) {
      req.setAttribute("error", "Invalid date format");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    if (birthDate.isAfter(LocalDate.now())) {
      req.setAttribute("error", "Date must not be in future");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    Client existingClient = userDao.findByLogin(login);
    if (existingClient != null) {
      req.setAttribute("error", "Client with this login already exists");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }
    existingClient = userDao.findByEmail(email);
    if (existingClient != null) {
      req.setAttribute("error", "Client with this email already exists");
      req.getRequestDispatcher("views/add.jsp").forward(req, resp);
      return;
    }

    Role roleUser = roleDao.findByName(role);
    Client client = new Client(firstName, lastName, login, password, email, birthDate, roleUser);
    userDao.create(client);

    resp.sendRedirect(req.getContextPath() + "/admin");
  }
}
