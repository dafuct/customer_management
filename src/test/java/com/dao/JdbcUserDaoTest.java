package com.dao;


import com.entity.Role;
import com.entity.User;
import com.property.JdbcProperties;
import java.io.FileOutputStream;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;

public class JdbcUserDaoTest extends DBTestCase {

    private IDatabaseTester iDatabaseTester;
    private UserDao userDao;
    private JdbcProperties properties = new JdbcProperties().invoke();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    User[] users = new User[4];

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

        iDatabaseTester = new JdbcDatabaseTester(
            properties.getDriver(), properties.getUrl(),
            properties.getUser(), properties.getPassword());

        queryCreateTableRole();
        queryCreateTableUser();

        IDataSet expectedDataSet = getDataSet();
        iDatabaseTester.setDataSet(expectedDataSet);
        iDatabaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        iDatabaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
        iDatabaseTester.onTearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass()
            .getClassLoader().getResourceAsStream("dataset_user.xml"));
    }

    public void testCreateUserInTable() throws Exception {
        try {
            userDao.create(null);
            fail("should be threw NullPointerException");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException");
        }

        userDao.create(users[2]);
        User admin = userDao.findByLogin(users[2].getLogin());
        assertEquals(users[2].getLogin(), admin.getLogin());
    }

    public void testUpdateUserInTable() throws Exception {
        try {
            userDao.update(null);
            fail("should be threw NullPointerException if user null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if user null");
        }

        try {
            userDao.update(users[3]);
            fail("should be threw IllegalArgumentException if user "
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if user "
                + "doesn't exist");
        }

        userDao.update(users[2]);

        User byName = userDao.findByLogin(users[2].getLogin());
        assertEquals(users[2].getLogin(), byName.getLogin());
    }

    public void testRemoveUserException() throws Exception {
        try {
            userDao.remove(null);
            fail("should be threw NullPointerException if user null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if user null");
        }

        try {
            userDao.remove(users[3]);
            fail("should be threw IllegalArgumentException if user "
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if user "
                + "doesn't exist");
        }
    }

    public void testRemoveUser() throws Exception {
        userDao.remove(new User(2L));

        QueryDataSet partialDataSet = new QueryDataSet(
            iDatabaseTester.getConnection());
        partialDataSet.addTable("user", "SELECT * FROM user");
        FlatXmlDataSet.write(partialDataSet,
            new FileOutputStream(
                "src/test/resource/dataset_remove_user.xml"));

        IDataSet databaseDataSet = iDatabaseTester.getConnection()
            .createDataSet();
        ITable actualTable = databaseDataSet.getTable("user");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
            .build(getClass().getClassLoader()
                .getResourceAsStream("dataset_remove_user.xml"));
        ITable expectedTable = expectedDataSet.getTable("user");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testUsersFindAll() throws Exception {
        List<User> allUsers = userDao.findAll();

        IDataSet databaseDataSet = iDatabaseTester.getConnection()
            .createDataSet();
        ITable table = databaseDataSet.getTable("user");

        assertEquals(table.getRowCount(), allUsers.size());
    }

    public void testFindByLoginUser() throws Exception {
        try {
            userDao.findByLogin(null);
            fail("should be threw NullPointerException if login null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if login null");
        }

        try {
            userDao.findByLogin(users[3].getLogin());
            fail("should be threw IllegalArgumentException if login"
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if login"
                + "doesn't exist");
        }

        User byLogin = userDao.findByLogin(users[0].getLogin());
        assertEquals(users[0].getLogin(), byLogin.getLogin());
    }

    public void testFindByEmailUser() throws Exception {
        try {
            userDao.findByEmail(null);
            fail("should be threw NullPointerException if email null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if email null");
        }

        try {
            userDao.findByEmail(users[3].getEmail());
            fail("should be threw IllegalArgumentException if email"
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if email"
                + "doesn't exist");
        }

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