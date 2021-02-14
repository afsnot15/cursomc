package com.afonso.cursomc.security;

import com.afonso.cursomc.dto.CredenciaisDTO;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;

/**
 *
 * @author Afonso
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager pAuthenticationManager, JWTUtil pJwtUtil) {
        this.authenticationManager = pAuthenticationManager;
        this.jwtUtil = pJwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest pReq, HttpServletResponse pRes) throws AuthenticationException {
        try {
            CredenciaisDTO oCredenciais = new ObjectMapper().readValue(pReq.getInputStream(), CredenciaisDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(oCredenciais.getEmail(), oCredenciais.getSenha(), new ArrayList<>());

            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest pReq, HttpServletResponse pRes, FilterChain pChain, Authentication pAuth) throws IOException, ServletException {
        String username = ((UserSS) pAuth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        pRes.addHeader("Authorization", "Bearer " + token);
        pRes.addHeader("access-control-expose-headers", "Authorization");
    }
}
