package com.makarenko.dao.hibernate;

import com.makarenko.dao.RoleDao;
import com.makarenko.dao.util.HibernateUtils;
import com.makarenko.entity.Role;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateRoleDao implements RoleDao {

  @Override
  public void create(Role role) {
    if (role == null) {
      throw new NullPointerException();
    }

    if (role.getName() == null) {
      throw new IllegalArgumentException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.save(role);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Role role) {
    if (role == null) {
      throw new NullPointerException();
    }

    if (role.getId() == null) {
      throw new IllegalArgumentException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.update(role);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public void remove(Role role) {
    if (role == null) {
      throw new NullPointerException();
    }

    if (role.getId() == null) {
      throw new IllegalArgumentException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.remove(role);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public Role findByName(String name) {
    if (name == null) {
      throw new NullPointerException();
    }

    if (name.isEmpty()) {
      throw new IllegalArgumentException();
    }

    Role role;
    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      Query query = session
          .createQuery("FROM Role role WHERE role.name = :name");
      query.setParameter("name", name);
      role = (Role) query.uniqueResult();

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
    return role;
  }

  @Override
  public List<Role> findAll() {
    List<Role> list;
    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      list = session.createQuery("FROM Role", Role.class).list();

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
    return list;
  }
}
