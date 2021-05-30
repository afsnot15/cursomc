package com.afonso.cursomc.services;

import com.afonso.cursomc.domain.Cidade;
import com.afonso.cursomc.dto.CidadeDTO;
import com.afonso.cursomc.repositories.CidadeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    @Autowired
    private CidadeService cidadeService;

    public List<Cidade> findByEstado(Integer estadoId) {
        return repo.findCidades(estadoId);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer idEstado) {
        List<Cidade> list = cidadeService.findByEstado(idEstado);
        List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
