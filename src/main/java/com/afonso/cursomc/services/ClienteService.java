package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.dto.ClienteDTO;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.services.exception.DataIntegratyException;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository rCliente;

    public Cliente find(Integer id) {
        Optional<Cliente> oCliente = rCliente.findById(id);
        return oCliente.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente insert(Cliente oCliente) {
        oCliente.setId(null);
        return rCliente.save(oCliente);
    }

    public Cliente update(Cliente oCliente) {
        Cliente oClienteUpdate = find(oCliente.getId());
        updateData(oClienteUpdate, oCliente);
       
        return rCliente.save(oClienteUpdate);
    }

    public void delete(Integer id) {
        try {
            rCliente.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratyException("Não é possível excluir porque há entidades relacionadas!");
        }
    }

    public List<Cliente> findAll() {
        return rCliente.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return rCliente.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO oClienteDTO) {
        return new Cliente(oClienteDTO.getId(), oClienteDTO.getNome(), oClienteDTO.getEmail(), null, null);
    }
    
    private void updateData(Cliente oClienteUpdate, Cliente oCliente){
        oClienteUpdate.setNome(oCliente.getNome());
        oClienteUpdate.setEmail(oCliente.getEmail());
    }
}
