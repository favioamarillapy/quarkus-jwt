package com.py.demo.quarkusjwt.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author favio.amarilla
 */
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class User extends PanacheEntity {

    public String username;
    public String password;
    public String role;

    public static User findByUsername(String username) {

        return find("username", username).firstResult();
    }

}
