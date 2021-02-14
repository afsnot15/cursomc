package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Afonso
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private ClienteRepository oClienteRepository;

    @Override
    public UserDetails loadUserByUsername(String pEmail) throws UsernameNotFoundException {
        Cliente oCliente = oClienteRepository.findByEmail(pEmail);
        
        if(oCliente == null){
            throw new UsernameNotFoundException(pEmail);
        }
        
        return new UserSS(oCliente.getId(), oCliente.getEmail(), oCliente.getSenha(), oCliente.getPerfis());
    }
}
