package com.afonso.cursomc.services;

import com.afonso.cursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author afonso.gomes
 */
public class UserService {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
