package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userroles")
public class Role {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int role_id;

private String roleName;

public Role() {

}

public Role(String roleName) {
super();
this.roleName = roleName;
}

public int getRole_id() {
return role_id;
}

public void setRole_id(int role_id) {
this.role_id = role_id;
}

public String getRoleName() {
return roleName;
}

public void setRoleName(String roleName) {
this.roleName = roleName;
}

}