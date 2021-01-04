package com.afonso.cursomc.services.exception.validation;

import com.afonso.cursomc.domain.Cliente;
import com.afonso.cursomc.dto.ClienteNewDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.afonso.cursomc.resources.exception.FieldMessage;
import com.afonso.cursomc.domain.enums.TipoCliente;
import com.afonso.cursomc.repositories.ClienteRepository;
import com.afonso.cursomc.services.exception.validation.utils.BR;

/**
 *
 * @author Afonso
 */
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO oClienteDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (oClienteDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getId()) && !BR.isValidCPF(oClienteDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (oClienteDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(oClienteDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente oClienteAux = repo.findByEmail(oClienteDTO.getEmail());
        if (oClienteAux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        //Transportando erros personalizados para a lista de erros do Framework.
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

}
