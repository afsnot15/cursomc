package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cidade;
import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.domain.Endereco;
import com.afonso.cursomc.domain.enums.Perfil;
import com.afonso.cursomc.domain.enums.TipoCliente;
import com.afonso.cursomc.dto.ClienteDTO;
import com.afonso.cursomc.dto.ClienteNewDTO;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.repositories.EnderecoRepository;
import com.afonso.cursomc.security.UserSS;
import com.afonso.cursomc.services.exception.AuthorizationException;
import com.afonso.cursomc.services.exception.DataIntegratyException;
import com.afonso.cursomc.services.exception.ObjectNotFoundException;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder enconder;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    public Cliente find(Integer pId) {
        UserSS oUser = UserService.authenticated();

        if (oUser == null || !oUser.hasRole(Perfil.ADMIN) && !pId.equals(oUser.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<Cliente> oCliente = clienteRepository.findById(pId);
        return oCliente.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + pId + ", Tipo: " + Cliente.class.getName()));
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
        return new Cliente(oClienteDTO.getId(), oClienteDTO.getNome(), oClienteDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO oClienteDTO) {
        Cliente oCliente = new Cliente(null, oClienteDTO.getNome(), oClienteDTO.getEmail(), oClienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(oClienteDTO.getTipo()), enconder.encode(oClienteDTO.getSenha()));
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

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS oUser = UserService.authenticated();
        if (oUser == null) {
            throw new AuthorizationException("Acesso Negado!");
        }

        BufferedImage jpgImage = imageService.getJpgImageFile(multipartFile);
        String fileName = prefix + oUser.getId() + ".jpg";
        InputStream is = imageService.getInputStream(jpgImage, "jpg");

        return s3Service.uploadFile(is, fileName, "image");
    }
}
