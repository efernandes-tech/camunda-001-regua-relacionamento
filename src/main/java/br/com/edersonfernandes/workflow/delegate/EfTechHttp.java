package br.com.edersonfernandes.workflow.delegate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EfTechHttp implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.debug("Iniciando Http{}");

        URI url = new URI(delegateExecution.getVariable("url").toString());
        String method = delegateExecution.getVariable("method").toString();

        Object body = delegateExecution.getVariable("body");
        Object autenticacaoWeb = delegateExecution.getVariable("autenticacaoWeb");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request;

        if (method.equals("POST")) {
            request = HttpRequest.newBuilder()
                    .uri(url)
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(body.toString()))
                    .build();
        } else if (method.equals("GET")) {
            request = HttpRequest.newBuilder()
                    .uri(url)
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json") // Set the request headers
                    .GET() // Set the request body
                    .build();
        } else {
            return;
        }

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        delegateExecution.setVariable("responseStatus", response.statusCode());

        /*
         *
         */

        String resposta = response.body();
        if (response.body().length() > 500)
            resposta = response.body().substring(0, 500);

        delegateExecution.setVariable("response", resposta);

    }
}
