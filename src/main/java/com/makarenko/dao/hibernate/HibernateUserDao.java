package com.makarenko.dao.hibernate;

import com.makarenko.dao.UserDao;
import com.makarenko.dao.util.HibernateUtils;
import com.makarenko.entity.Client;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateUserDao implements UserDao {

  @Override
  public void create(Client client) {
    if (client == null) {
      throw new NullPointerException();
    }

    if (client.getLogin() == null) {
      throw new NullPointerException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.save(client);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Client client) {
    if (client == null) {
      throw new NullPointerException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.update(client);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public void remove(Client client) {
    if (client == null) {
      throw new NullPointerException();
    }

    if (client.getId() == null) {
      throw new NullPointerException();
    }

    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      session.remove(client);

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Client> findAll() {
    List<Client> list;
    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      list = session.createQuery("FROM Client", Client.class).list();

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
    return list;
  }

  @Override
  public Client findByLogin(String login) {
    if (login == null) {
      throw new NullPointerException();
    }

    if (login.isEmpty()) {
      throw new IllegalArgumentException();
    }

    Client client;
    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      Query query = session
          .createQuery("FROM Client c WHERE c.login =: login");
      query.setParameter("login", login);
      client = (Client) query.uniqueResult();

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
    return client;
  }

  @Override
  public Client findByEmail(String email) {
    if (email == null) {
      throw new NullPointerException();
    }

    if (email.isEmpty()) {
      throw new IllegalArgumentException();
    }

    Client client;
    Transaction transaction = null;
    try {
      Session session = HibernateUtils.getSessionFactory().getCurrentSession();
      transaction = session.beginTransaction();

      Query query = session
          .createQuery("FROM Client c WHERE c.email =: email");
      query.setParameter("email", email);
      client = (Client) query.uniqueResult();

      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw new RuntimeException(e);
    }
    return client;
  }
}
