package com.dao;

import com.entity.Role;
import com.property.JdbcProperties;
import java.io.FileOutputStream;
import java.sql.Statement;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;

public class JdbcRoleDaoTest extends DBTestCase {

    private IDatabaseTester iDatabaseTester;
    private RoleDao roleDao;
    private JdbcProperties properties = new JdbcProperties().invoke();

    @Before
    public void setUp() throws Exception {
        roleDao = new JdbcRoleDao();
        iDatabaseTester = new JdbcDatabaseTester(
            properties.getDriver(), properties.getUrl(),
            properties.getUser(), properties.getPassword());

        queryCreateTableRole();

        IDataSet iDataSet = getDataSet();
        iDatabaseTester.setDataSet(iDataSet);
        iDatabaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        iDatabaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
        iDatabaseTester.onTearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
            getClass().getClassLoader()
                .getResourceAsStream("dataset_role.xml"));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    public void testFindByName() throws Exception {
        Role adminRole = new Role("admin");
        Role byName = roleDao.findByName(adminRole.getName());
        assertEquals(adminRole.getName(), byName.getName());
    }

    public void testCreateRoleInTable() throws Exception {
        try {
            roleDao.create(null);
            fail("should be threw NullPointerException");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException");
        }

        Role sampleRole = new Role("sample");
        roleDao.create(sampleRole);
        Role admin = roleDao.findByName(sampleRole.getName());
        assertEquals(sampleRole.getName(), admin.getName());
    }

    public void testUpdateRoleInTable() throws Exception {
        try {
            roleDao.update(null);
            fail("should be threw NullPointerException if role null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if role null");
        }

        try {
            roleDao.update(new Role(4L, "gist"));
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        }

        Role roleUserUpdate = new Role(1L, "gist");
        roleDao.update(roleUserUpdate);

        Role byName = roleDao.findByName(roleUserUpdate.getName());
        assertEquals(roleUserUpdate.getName(), byName.getName());
    }

    public void testRemoveRoleException() throws Exception {
        try {
            roleDao.remove(null);
            fail("should be threw NullPointerException if role null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if role null");
        }

        try {
            roleDao.remove(new Role(4L));
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        }
    }

    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role(1L));

        QueryDataSet partialDataSet = new QueryDataSet(
            iDatabaseTester.getConnection());
        partialDataSet.addTable("role", "SELECT * FROM role");
        FlatXmlDataSet.write(partialDataSet,
            new FileOutputStream(
                "src/test/resource/dataset_remove_role.xml"));

        IDataSet databaseDataSet = iDatabaseTester.getConnection()
            .createDataSet();
        ITable actualTable = databaseDataSet.getTable("role");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
            .build(getClass().getClassLoader()
                .getResourceAsStream("dataset_remove_role.xml"));
        ITable expectedTable = expectedDataSet.getTable("role");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public void testFindByNameException() {
        try {
            roleDao.findByName(null);
            fail("should be threw NullPointerException if role null");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            fail("should be threw NullPointerException if role null");
        }

        try {
            roleDao.findByName(new Role("test").getName());
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("should be threw IllegalArgumentException if role "
                + "doesn't exist");
        }
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