package com.nixsolutions.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "role")
public class Role  implements Serializable {

  private static final long serialVersionUID = 1584418691832587636L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  public Role() {
  }

  public Role(Long id) {
    this.id = id;
  }

  public Role(String name) {
    this.name = name;
  }

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Role)) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equals(getId(), role.getId()) &&
        Objects.equals(getName(), role.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

}
