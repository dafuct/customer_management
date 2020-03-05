package com.nixsolutions.dao;

import static org.junit.Assert.assertEquals;

import com.nixsolutions.entity.Role;
import com.nixsolutions.property.JdbcProperties;
import java.sql.Statement;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JdbcRoleDaoTest {

  private IDatabaseTester iDatabaseTester;
  private RoleDao roleDao;
  private JdbcProperties properties = new JdbcProperties().invoke();

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    roleDao = new JdbcRoleDao();
    DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
  }

  @After
  public void tearDown() throws Exception {
    DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
  }

  private IDatabaseConnection getConnection() throws Exception {
    iDatabaseTester = new JdbcDatabaseTester(
        properties.getDriver(), properties.getUrl(),
        properties.getUser(), properties.getPassword());

    queryCreateTableRole();

    IDataSet iDataSet = getDataSet();
    iDatabaseTester.setDataSet(iDataSet);
    iDatabaseTester.onSetup();

    return iDatabaseTester.getConnection();
  }

  private IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder().build(
        getClass().getClassLoader()
            .getResourceAsStream("dataset_role.xml"));
  }

  @Test
  public void testCreateRoleInTableException() {
    exception.expect(NullPointerException.class);
    roleDao.create(null);
    exception.expectMessage("should be threw NullPointerException");
  }

  @Test
  public void testCreateRoleInTable() {
    Role sampleRole = new Role("sample");
    roleDao.create(sampleRole);
    Role admin = roleDao.findByName(sampleRole.getName());
    assertEquals(sampleRole.getName(), admin.getName());
  }

  @Test
  public void testUpdateRoleInTableException() throws Exception {
    exception.expect(NullPointerException.class);
    roleDao.update(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(IllegalArgumentException.class);
    roleDao.update(new Role(4L, "gist"));
    exception.expectMessage(
        "should be threw IllegalArgumentException "
            + "if role doesn't exist");
  }

  @Test
  public void testUpdateRoleInTable() {
    Role roleUserUpdate = new Role(1L, "gist");
    roleDao.update(roleUserUpdate);

    Role byName = roleDao.findByName(roleUserUpdate.getName());
    assertEquals(roleUserUpdate.getName(), byName.getName());
  }

  @Test
  public void testRemoveRoleException() {
    exception.expect(NullPointerException.class);
    roleDao.remove(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(NullPointerException.class);
    roleDao.remove(new Role(4L));
    exception.expectMessage("should be threw IllegalArgumentException "
        + "if role doesn't exist");
  }

  @Test
  public void testRemoveRole() throws Exception {
    roleDao.remove(new Role(1L));

    IDataSet databaseDataSet = iDatabaseTester.getConnection()
        .createDataSet();
    ITable actualTable = databaseDataSet.getTable("role");

    IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
        .build(getClass().getClassLoader()
            .getResourceAsStream("dataset_remove_role.xml"));
    ITable expectedTable = expectedDataSet.getTable("role");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void testFindByName() {
    Role adminRole = new Role("admin");
    Role byName = roleDao.findByName(adminRole.getName());
    assertEquals(adminRole.getName(), byName.getName());
  }

  @Test
  public void testFindByNameException() {
    exception.expect(NullPointerException.class);
    roleDao.findByName(null);
    exception.expectMessage("should be threw NullPointerException "
        + "if role null");

    exception.expect(IllegalArgumentException.class);
    roleDao.findByName(new Role("test").getName());
    exception.expectMessage("should be threw IllegalArgumentException "
        + "if role doesn't exist");
  }

  private void queryCreateTableRole() {
    String queryRole = "create table if not exists"
        + " role (id int auto_increment primary key, name varchar(255))";
    try (Statement statement = iDatabaseTester.getConnection()
        .getConnection().createStatement()) {
      statement.executeUpdate(queryRole);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}