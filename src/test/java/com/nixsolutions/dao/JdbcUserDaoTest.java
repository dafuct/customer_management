package com.nixsolutions.dao;


import static org.junit.Assert.assertEquals;


import com.nixsolutions.entity.Role;
import com.nixsolutions.entity.User;
import com.nixsolutions.property.JdbcProperties;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
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

public class JdbcUserDaoTest {

    private IDatabaseTester iDatabaseTester;
    private UserDao userDao;
    private JdbcProperties properties = new JdbcProperties().invoke();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    User[] users = new User[4];

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        userDao = new JdbcUserDao();
        users[0] = new User("first_name1", "last_name1",
            "login1", "password1", "da@gmail.com",
            format.parse("2000-01-01"), new Role(1L, "admin"));
        users[1] = new User("first_name2", "last_name2",
            "login2", "password2", "not@gmail.com",
            format.parse("2020-01-01"), new Role(2L, "user"));
        users[2] = new User(2L, "first_name3", "last_name3",
            "login3", "password3", "bro@gmail.com",
            format.parse("1999-01-01"), new Role(2L, "user"));
        users[3] = new User(4L, "first_name4", "last_name4",
            "login4", "password4", "true@gmail.com",
            format.parse("1998-01-01"), new Role(2L, "user"));

        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
    }

    @After
    public void tearDown() throws Exception {
        iDatabaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
        iDatabaseTester.onTearDown();
    }

    private IDatabaseConnection getConnection() throws Exception {
        iDatabaseTester = new JdbcDatabaseTester(
            properties.getDriver(), properties.getUrl(),
            properties.getUser(), properties.getPassword());

        queryCreateTableRole();
        queryCreateTableUser();

        IDataSet iDataSet = getDataSet();
        iDatabaseTester.setDataSet(iDataSet);
        iDatabaseTester.onSetup();

        return iDatabaseTester.getConnection();
    }

    private IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass()
            .getClassLoader().getResourceAsStream("dataset_user.xml"));
    }

    @Test
    public void testCreateUserInTableException() {
        exception.expect(NullPointerException.class);
        userDao.create(null);
        exception.expectMessage(
            "should be threw NullPointerException");
    }

    @Test
    public void testCreateUserInTable() {
        userDao.create(users[2]);
        User admin = userDao.findByLogin(users[2].getLogin());
        assertEquals(users[2].getLogin(), admin.getLogin());
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
    public void testUpdateUserInTable() {
        userDao.update(users[2]);

        User byName = userDao.findByLogin(users[2].getLogin());
        assertEquals(users[2].getLogin(), byName.getLogin());
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
    public void testRemoveUser() throws Exception {
        userDao.remove(new User(2L));

        IDataSet databaseDataSet = iDatabaseTester.getConnection()
            .createDataSet();
        ITable actualTable = databaseDataSet.getTable("user");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
            .build(getClass().getClassLoader()
                .getResourceAsStream("dataset_remove_user.xml"));
        ITable expectedTable = expectedDataSet.getTable("user");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testUsersFindAll() throws Exception {
        List<User> allUsers = userDao.findAll();

        IDataSet databaseDataSet = iDatabaseTester.getConnection()
            .createDataSet();
        ITable table = databaseDataSet.getTable("user");

        assertEquals(table.getRowCount(), allUsers.size());
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
    public void testFindByLoginUser() {
        User byLogin = userDao.findByLogin(users[0].getLogin());
        assertEquals(users[0].getLogin(), byLogin.getLogin());
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

    @Test
    public void testFindByEmailUser() {
        User byEmail = userDao.findByEmail(users[0].getEmail());
        assertEquals(users[0].getEmail(), byEmail.getEmail());
    }

    private void queryCreateTableRole() {
        String queryRole = "create table if not exists "
            + "role (id int primary key, name varchar(255))";
        try (Statement statement = iDatabaseTester.getConnection()
            .getConnection().createStatement()) {
            statement.executeUpdate(queryRole);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void queryCreateTableUser() {
        String queryUser = "create table if not exists user ("
            + "id int, first_name varchar(255),"
            + "last_name varchar(255), login varchar(255), "
            + "password varchar(255), email varchar(255), "
            + "birthday date, role_id int)";
        try (Statement statement = iDatabaseTester.getConnection()
            .getConnection().createStatement()) {
            statement.executeUpdate(queryUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}