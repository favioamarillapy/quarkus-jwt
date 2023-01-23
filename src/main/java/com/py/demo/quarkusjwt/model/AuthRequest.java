package com.py.demo.quarkusjwt.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author favio.amarilla
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {

    public String username;
    public String password;
    public String role;

}
