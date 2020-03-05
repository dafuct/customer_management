package com.nixsolutions.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class User {

  private Long id;
  private String firstName;
  private String lastName;
  private String login;
  private String password;
  private String email;
  private LocalDate birthday;
  private Role role;
  private int age;

  public User(Long id, String firstName, String lastName, String login, String password,
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

  public User(String firstName, String lastName, String login, String password,
      String email, LocalDate birthday, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.email = email;
    this.birthday = birthday;
    this.role = role;
  }

  public User(Long id) {
    this.id = id;
  }

  public User(String login) {
    this.login = login;
  }

  public User() {
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
    this.age = Period.between(this.birthday, currentDate).getYears();
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return getFirstName().equals(user.getFirstName()) &&
            getLastName().equals(user.getLastName()) &&
            getLogin().equals(user.getLogin()) &&
            getPassword().equals(user.getPassword()) &&
            getEmail().equals(user.getEmail()) &&
            getBirthday().equals(user.getBirthday()) &&
            getRole().equals(user.getRole());
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
