package br.projeto.appgestaohvida.controller.filasatendimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/listas/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:63342/", "https://example.com"}, allowCredentials = "true")
public class ControllerMain {

    @Autowired
    private ControllerListaPP controllerListaPP;

    @Autowired
    private ControllerListaP controllerListaP;

    @Autowired
    private ControllerListaB controllerListaB;

    @Autowired
    private ContrellerListaA controllerListaA;

    @GetMapping
    public String main() {
        String resposta;

        if (controllerListaA.temListaA()) {
            resposta = controllerListaA.obterPrimeiraSenha();
            controllerListaA.retirarLista();
            return resposta;
        } else if (controllerListaPP.temListaPP()) {
            resposta = controllerListaPP.obterPrimeiraSenha();
            controllerListaPP.retirarLista();
            return resposta;
        } else if (controllerListaP.temListaP()) {
            resposta = controllerListaP.obterPrimeiraSenha();
            controllerListaP.retirarLista();
            return resposta;
        } else {
            resposta = controllerListaB.obterPrimeiraSenha();
            controllerListaB.retirarLista();
            return resposta;
        }
    }

    @GetMapping
    @RequestMapping("/conjuntolistas")
    public String conjuntoListas() {
        StringBuilder resposta = new StringBuilder("[");
        List<String> itens = new ArrayList<>();

        int contadorDasListas = 0;
        int numeroDeListasPercorrer = 1;

        if (controllerListaA.temListaA()) {
            int tamanhoA = Math.min(controllerListaA.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoA; i++) {
                itens.add(String.format("{\"Número na Fila\": %d, %s, \"Tipo de atendimento\": \"Emergência\"}",
                        numeroDeListasPercorrer++, controllerListaA.obterFormatado(i)));
            }
            contadorDasListas += tamanhoA;
        }

        if (contadorDasListas < 10 && controllerListaPP.temListaPP()) {
            int tamanhoPP = Math.min(controllerListaPP.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoPP; i++) {
                itens.add(String.format("{\"Número na Fila\": %d, %s, \"Tipo de atendimento\": \"Prioritário I\"}",
                        numeroDeListasPercorrer++, controllerListaPP.obterFormatado(i)));
            }
            contadorDasListas += tamanhoPP;
        }

        if (contadorDasListas < 10 && controllerListaP.temListaP()) {
            int tamanhoP = Math.min(controllerListaP.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoP; i++) {
                itens.add(String.format("{\"Número na Fila\": %d, %s, \"Tipo de atendimento\": \"Prioritário II\"}",
                        numeroDeListasPercorrer++, controllerListaP.obterFormatado(i)));
            }
            contadorDasListas += tamanhoP;
        }

        if (contadorDasListas < 10 && controllerListaB.temListaB()) {
            int tamanhoB = Math.min(controllerListaB.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoB; i++) {
                itens.add(String.format("{\"Número na Fila\": %d, %s, \"Tipo de atendimento\": \"Geral\"}",
                        numeroDeListasPercorrer++, controllerListaB.obterFormatado(i)));
            }
            contadorDasListas += tamanhoB;
        }

        resposta.append(String.join(", ", itens));
        resposta.append("]");

        System.out.println("Resposta JSON gerada: " + resposta);

        return resposta.toString();
    }
}
