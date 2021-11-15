package com.example.crudboot.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "enabled")
    private int enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roleList;

    public User() {
    }

    public User(String email, String password, String username, String name, String lastName) {

        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    public void addRole(Role role) {
        if (roleList == null) {
            roleList = new ArrayList<>();
        }
        roleList.add(role);
    }

    public boolean isAdmin() {
        return roleList.contains(new Role("ADMIN"));
    }

    //==============================================Setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    //=======================================Getters

    public String getPassword() {
        return password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }


    //============================================= @Override
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && name.equals(user.name) && lastName.equals(user.lastName) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, email);
    }
}

