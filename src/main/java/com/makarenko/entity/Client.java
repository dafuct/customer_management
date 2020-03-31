package com.makarenko.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client implements Serializable {

  private static final long serialVersionUID = 6585370168907351666L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "birthday")
  private LocalDate birthday;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private Role role;

  public Client(Long id, String firstName, String lastName, String login, String password,
      String email, LocalDate birthday, Role role) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.email = email;
    this.birthday = birthday;
    this.role = role;
  }

  public Client(String firstName, String lastName, String login, String password,
      String email, LocalDate birthday, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.email = email;
    this.birthday = birthday;
    this.role = role;
  }

  public Client(Long id) {
    this.id = id;
  }

  public Client(String login) {
    this.login = login;
  }

  public Client() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public int getAge() {
    LocalDate currentDate = LocalDate.now();
    int age = Period.between(this.birthday, currentDate).getYears();
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Client)) {
      return false;
    }
    Client client = (Client) o;
    return getFirstName().equals(client.getFirstName()) &&
        getLastName().equals(client.getLastName()) &&
        getLogin().equals(client.getLogin()) &&
        getPassword().equals(client.getPassword()) &&
        getEmail().equals(client.getEmail()) &&
        getBirthday().equals(client.getBirthday()) &&
        getRole().equals(client.getRole());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getId(), getFirstName(),
            getLastName(), getLogin(),
            getPassword(), getEmail(),
            getBirthday(), getRole());
  }
}
