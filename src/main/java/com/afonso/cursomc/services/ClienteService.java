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
import org.springframework.transaction.annotation.Transactional;
import com.afonso.cursomc.domain.Endereco;
import com.afonso.cursomc.domain.Cidade;
import com.afonso.cursomc.domain.enums.TipoCliente;
import com.afonso.cursomc.dto.ClienteNewDTO;
import com.afonso.cursomc.repositories.EnderecoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {
        Optional<Cliente> oCliente = clienteRepository.findById(id);
        return oCliente.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
   public Cliente insert(Cliente oCliente) {
		oCliente.setId(null);
		oCliente = clienteRepository.save(oCliente);
		enderecoRepository.saveAll(oCliente.getEnderecos());
		return oCliente;
	}

    public Cliente update(Cliente oCliente) {
        Cliente oClienteUpdate = find(oCliente.getId());
        updateData(oClienteUpdate, oCliente);

        return clienteRepository.save(oClienteUpdate);
    }

    public void delete(Integer id) {
        try {
            clienteRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratyException("Não é possível excluir porque há entidades relacionadas!");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO oClienteDTO) {
        return new Cliente(oClienteDTO.getId(), oClienteDTO.getNome(), oClienteDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO oClienteDTO) {
        Cliente oCliente = new Cliente(null, oClienteDTO.getNome(), oClienteDTO.getEmail(), oClienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(oClienteDTO.getTipo()));
        Cidade oCidade = new Cidade(oClienteDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, oClienteDTO.getLogradouro(), oClienteDTO.getNumero(), oClienteDTO.getComplemento(), oClienteDTO.getBairro(), oClienteDTO.getCep(), oCliente, oCidade);
        oCliente.getEnderecos().add(end);
        oCliente.getTelefones().add(oClienteDTO.getTelefone1());
       
        if (oClienteDTO.getTelefone2() != null) {
            oCliente.getTelefones().add(oClienteDTO.getTelefone2());
        }
       
        if (oClienteDTO.getTelefone3() != null) {
            oCliente.getTelefones().add(oClienteDTO.getTelefone3());
        }
        
        return oCliente;
    }

    private void updateData(Cliente oClienteUpdate, Cliente oCliente) {
        oClienteUpdate.setNome(oCliente.getNome());
        oClienteUpdate.setEmail(oCliente.getEmail());
    }
}
