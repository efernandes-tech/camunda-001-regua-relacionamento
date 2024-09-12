package br.com.edersonfernandes.workflow.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edersonfernandes.workflow.dto.IniciarReguaRelacionamentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/regua-relacionamento")
@RequiredArgsConstructor
@Slf4j
public class ReguaRelacionamentoController {

    private final static String MESSAGE_START = "INICIAR_REGUA_RELACIONAMENTO";
    private final RuntimeService runtimeService;

    @PostMapping()
    public void executarReguaRelacionamento(@RequestBody IniciarReguaRelacionamentoDTO mensagem) {
        log.info("Iniciando procesamento  para {}", mensagem);

        MessageCorrelationBuilder messageCorrelationBuilder = runtimeService.createMessageCorrelation(MESSAGE_START);

        /*
         * Map<String, Object> variables = VariablesUtil.toVariableMap(mensagem);
         * messageCorrelationBuilder.setVariables(VariablesUtil.toVariableMap(
         * termoDesistenciaDto.getDto()));
         */

        messageCorrelationBuilder.setVariable("idCliente", mensagem.getIdCliente());
        messageCorrelationBuilder.setVariable("email", mensagem.getEmail());

        /*
         *
         * if (camundaMessageDto.getDto() != null) {
         * variaveis = VariablesUtil.toVariableMap(camundaMessageDto.getDto());
         * }
         *
         * ProcessInstance processo =
         * runtimeService.startProcessInstanceByMessage(messageName,
         * camundaMessageDto.getCorrelationId(), variaveis);
         *
         *
         */
        MessageCorrelationResult messageResult = messageCorrelationBuilder
                .processInstanceBusinessKey(String.valueOf(mensagem.getIdCliente()))
                .correlateWithResult();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
