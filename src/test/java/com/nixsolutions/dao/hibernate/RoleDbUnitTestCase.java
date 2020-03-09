package com.nixsolutions.dao.hibernate;

import static org.junit.Assert.assertEquals;

import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.util.HibernateUtils;
import com.nixsolutions.entity.Role;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoleDbUnitTestCase extends HibernateDbUnitTestCase {

  private RoleDao roleDao;
  private Role[] roles = new Role[6];

  @Rule
  public ExpectedException exception = ExpectedException.none();

  public RoleDbUnitTestCase() throws ClassNotFoundException {
    super();
  }

  @Before
  public void setUp() throws Exception {
    HibernateUtils.getSessionFactory().getCurrentSession();
    setInitialData();
    roleDao = new HibernateRoleDao();

    roles[0] = new Role(1L, "admin");
    roles[1] = new Role(2L, "client");
    roles[2] = new Role(3L, "sample");
    roles[3] = new Role(4L, "gist");
    roles[4] = new Role(2L, "gist");
    roles[5] = new Role(3L, "test");
  }

  @Test
  public void testCreateRoleInTable() {
    roleDao.create(roles[2]);
    Role admin = roleDao.findByName(roles[2].getName());
    assertEquals(roles[2].getName(), admin.getName());
  }

  @Test
  public void testCreateRoleInTableException() {
    exception.expect(NullPointerException.class);
    roleDao.create(null);
    exception.expectMessage("should be threw NullPointerException");
  }

  @Test
  public void testUpdateRoleInTableException() throws Exception {
    exception.expect(NullPointerException.class);
    roleDao.update(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(IllegalArgumentException.class);
    roleDao.update(roles[3]);
    exception.expectMessage(
        "should be threw IllegalArgumentException "
            + "if role doesn't exist");
  }

  @Test
  public void testUpdateRoleInTable() {
    roleDao.update(roles[4]);
    Role byName = roleDao.findByName(roles[4].getName());
    assertEquals(roles[4].getName(), byName.getName());
  }

  @Test
  public void testRemoveRoleException() {
    exception.expect(NullPointerException.class);
    roleDao.remove(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(NullPointerException.class);
    roleDao.remove(roles[4]);
    exception.expectMessage("should be threw IllegalArgumentException "
        + "if role doesn't exist");
  }

  @Test
  public void testRemoveRole() {
    roleDao.remove(roles[5]);
    assertEquals(2, roleDao.findAll().size());
  }

  @Test
  public void testFindByName() {
    Role byName = roleDao.findByName(roles[0].getName());
    assertEquals(roles[0].getName(), byName.getName());
  }

  @Test
  public void testFindByNameException() {
    exception.expect(NullPointerException.class);
    roleDao.findByName(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(IllegalArgumentException.class);
    roleDao.findByName(roles[4].getName());
    exception.expectMessage("should be threw IllegalArgumentException "
        + "if role doesn't exist");
  }
}
