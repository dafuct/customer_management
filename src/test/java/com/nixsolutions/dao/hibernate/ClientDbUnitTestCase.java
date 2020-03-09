package com.nixsolutions.dao.hibernate;

import static org.junit.Assert.assertEquals;

import com.nixsolutions.dao.UserDao;
import com.nixsolutions.dao.util.HibernateUtils;
import com.nixsolutions.entity.Client;
import com.nixsolutions.entity.Role;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClientDbUnitTestCase extends HibernateDbUnitTestCase {

  private UserDao userDao;
  private Client[] users = new Client[5];

  @Rule
  public ExpectedException exception = ExpectedException.none();

  public ClientDbUnitTestCase() throws ClassNotFoundException {
    super();
  }

  @Before
  public void setUp() throws Exception {
    HibernateUtils.getSessionFactory().getCurrentSession();
    setInitialData();
    userDao = new HibernateUserDao();

    users[0] = new Client(1L, "first_name1", "last_name1",
        "login1", "password1", "da@gmail.com",
        LocalDate.of(2000, 1, 1), new Role(1L, "admin"));
    users[1] = new Client(2L, "first_name2", "last_name2",
        "login2", "password2", "not@gmail.com",
        LocalDate.of(2020, 1, 1), new Role(2L, "user"));
    users[2] = new Client(2L, "first_name3", "last_name3",
        "login3", "password3", "bro@gmail.com",
        LocalDate.of(1999, 1, 1), new Role(2L, "user"));
    users[3] = new Client("first_name4", "last_name4",
        "login4", "password4", "true@gmail.com",
        LocalDate.of(1999, 1, 1), new Role(2L, "user"));
    users[4] = new Client(4L, "first_name4", "last_name4",
        "login2", "password4", "bro44@gmail.com",
        LocalDate.of(1999, 1, 1), new Role(2L, "user"));
  }

  @Test
  public void testFindAll() {
    assertEquals(2, userDao.findAll().size());
  }

  @Test
  public void testCreateClient() {
    userDao.create(users[4]);
    assertEquals(3, userDao.findAll().size());
  }

  @Test
  public void testClientRemove() {
    userDao.remove(users[1]);
    assertEquals(1, userDao.findAll().size());
  }

  @Test
  public void testClientUpdate() {
    userDao.update(users[2]);
    String login = users[2].getLogin();
    Client client = userDao.findByLogin(login);
    assertEquals(login, client.getLogin());
  }

  @Test
  public void testFindByLogin() {
    String login = users[1].getLogin();
    Client client = userDao.findByLogin(login);
    assertEquals(login, client.getLogin());
  }

  @Test
  public void testFindByEmail() {
    Client byEmail = userDao.findByEmail(users[0].getEmail());
    Assert.assertEquals(users[0].getEmail(), byEmail.getEmail());
  }

  @Test
  public void testCreateUserInTableException() {
    exception.expect(NullPointerException.class);
    userDao.create(null);
    exception.expectMessage(
        "should be threw NullPointerException");
  }

  @Test
  public void testUpdateUserInTableException() {
    exception.expect(NullPointerException.class);
    userDao.update(null);
    exception.expectMessage(
        "should be threw NullPointerException if user null");

    exception.expect(IllegalArgumentException.class);
    userDao.update(users[3]);
    exception.expectMessage("should be threw "
        + "IllegalArgumentException if user doesn't exist");
  }

  @Test
  public void testRemoveUserException() {
    exception.expect(NullPointerException.class);
    userDao.remove(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if user null");

    exception.expect(IllegalArgumentException.class);
    userDao.remove(users[3]);
    exception.expectMessage("should be threw "
        + "IllegalArgumentException if user doesn't exist");
  }

  @Test
  public void testFindByLoginUserException() {
    exception.expect(NullPointerException.class);
    userDao.findByLogin(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if login null");

    exception.expect(IllegalArgumentException.class);
    userDao.findByLogin(users[3].getLogin());
    exception.expectMessage("should be threw "
        + "IllegalArgumentException if login doesn't exist");
  }

  @Test
  public void testFindByEmailUserException() {
    exception.expect(NullPointerException.class);
    userDao.findByEmail(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if email null");

    exception.expect(IllegalArgumentException.class);
    userDao.findByEmail(users[3].getEmail());
    exception.expectMessage("should be threw "
        + "IllegalArgumentException if email doesn't exist");
  }
}
