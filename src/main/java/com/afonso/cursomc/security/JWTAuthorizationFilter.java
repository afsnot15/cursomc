package com.afonso.cursomc.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 * @author afonso.gomes
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager pAuthenticationManager, JWTUtil pJwtUtil, UserDetailsService pUserDetailsService) {
        super(pAuthenticationManager);
        this.jwtUtil = pJwtUtil;
        this.userDetailsService = pUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest pRequest, HttpServletResponse pResponse, FilterChain pChain) throws IOException, ServletException {
        String header = pRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        pChain.doFilter(pRequest, pResponse);
    }

  private UsernamePasswordAuthenticationToken getAuthentication(String pToken) {
		if (jwtUtil.tokenValido(pToken)) {
			String username = jwtUtil.getUsername(pToken);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
