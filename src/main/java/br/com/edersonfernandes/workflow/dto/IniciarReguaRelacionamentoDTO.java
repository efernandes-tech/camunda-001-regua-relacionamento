package br.com.edersonfernandes.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IniciarReguaRelacionamentoDTO {

    @Getter
    @Setter
    private int idCliente;

    @Getter
    @Setter
    private String Email;

}
