package com.afonso.cursomc.resources;

import com.afonso.cursomc.dto.EmailDTO;
import com.afonso.cursomc.security.JWTUtil;
import com.afonso.cursomc.security.UserSS;
import com.afonso.cursomc.services.AuthService;
import com.afonso.cursomc.services.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author afonso.gomes
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse pResponse) {
        UserSS oUser = UserService.authenticated();
        String token = jwtUtil.generateToken(oUser.getUsername());
        pResponse.addHeader("Authorization", "Bearer " + token);
        pResponse.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO oEmailDTO) {
        service.sendNewPassword(oEmailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
