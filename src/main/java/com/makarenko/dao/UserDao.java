package com.makarenko.dao;

import com.makarenko.entity.Client;
import java.util.List;

public interface UserDao {

  void create(Client client);

  void update(Client client);

  void remove(Client client);

  List<Client> findAll();

  Client findByLogin(String login);

  Client findByEmail(String email);
}
