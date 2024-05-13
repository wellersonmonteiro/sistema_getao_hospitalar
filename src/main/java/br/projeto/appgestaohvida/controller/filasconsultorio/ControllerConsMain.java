package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.pacientes.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consultorio/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
public class ControllerConsMain {
    @Autowired
    ControllerListaAmarela controllerListaAmarela;
    @Autowired
    ControllerListaVermelha controllerListaVermelha;
    @Autowired
    ControllerListaVerde controllerListaVerde;
    @Autowired
    ControllerListaAzul controllerListaAzul;

    @GetMapping
    public String main() {
        // Verificar e encaminhar para o consultório adequado
        if (controllerListaVermelha.temListaVermelha()){
            String resposta = controllerListaVermelha.obterPrimeiraSenha();
            controllerListaVermelha.retirarLista();
            return resposta;
        } else if (controllerListaAmarela.temListaAmarela()) {
            String resposta = controllerListaAmarela.obterPrimeiraSenha();
            controllerListaAmarela.retirarLista();
            return resposta;
        } else if (controllerListaVerde.temListaVerde()) {
            String resposta = controllerListaVerde.obterPrimeiraSenha();
            controllerListaVerde.retirarLista();
            return resposta;
        } else if (controllerListaAzul.temListaAzul()) {
            String resposta = controllerListaAzul.obterPrimeiraSenha();
            controllerListaAzul.retirarLista();
            return resposta;
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String adicionarPaciente(@RequestBody PacienteDTO condicoes) {
        String corFila;

        Paciente paciente = new Paciente(condicoes.getNomeCompleto(), condicoes.getSenha(), null);

        // Verifica as condicoes do paciente para determinar a cor da fila
        if (condicoes.isViaAerea() || condicoes.isRespiracaoIneficaz() || condicoes.isChoque() ||
                condicoes.isNaoRespondeEstimulo() || condicoes.isEmConvulsao()) {
            corFila = "vermelha";
        } else if (condicoes.isDorSevera() || condicoes.isGrandeHemorragia() || condicoes.isAlteracaoConsciencia() ||
                condicoes.isTemperaturaAlta() || condicoes.isTraumaCraniano()) {
            corFila = "amarela";
        } else if (condicoes.isPequenaHemorragia() || condicoes.isDorModerada() || condicoes.isVomitoPersistente()) {
            corFila = "amarela";
        } else if (condicoes.isTemperaturaEntre38e39() || condicoes.isIdosoOuGravidaSintomatico()) {
            corFila = "verde";
        } else if (condicoes.isDorLeve() || condicoes.isNauseas() || condicoes.isTemperaturaEntre37e38()) {
            corFila = "azul";
        } else {
            corFila = "azul"; // Definir uma cor padrao se nao houver correspondencia com as condicoes conhecidas
        }

        // Adiciona o paciente a fila correspondente
        switch (corFila) {
            case "vermelha":
                // Adicionar a lista vermelha
                controllerListaVermelha.adicionarNovoPaciente(paciente);
                break;
            case "amarela":
                // Adicionar a lista amarela
                controllerListaAmarela.adicionarNovoPaciente(paciente);
                break;
            case "verde":
                // Adicionar a lista verde
                controllerListaVerde.adicionarNovoPaciente(paciente);
                break;
            case "azul":
                // Adicionar a lista azul
                controllerListaAzul.adicionarNovoPaciente(paciente);
                break;
            default:

                break;
        }

        return "Paciente adicionado com sucesso a fila " + corFila;
    }

    @GetMapping
    @RequestMapping("/conjuntolistas")
    public String conjuntoListas2() {
        StringBuilder resposta2 = new StringBuilder("[");
        List<String> itens2 = new ArrayList<>();

        int contadorDasListas = 0;
        int numeroDeListasPercorrer = 1;

        if (controllerListaVermelha.temListaVermelha()) {
            int tamanhoA = Math.min(controllerListaVermelha.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoA; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s, \"Tipo de atendimento\": \"Emergência\"}",
                        numeroDeListasPercorrer++,controllerListaVermelha.obterFormatadoNome(i) ));
            }
            contadorDasListas += tamanhoA;
        }

        if (contadorDasListas < 10 && controllerListaAmarela.temListaAmarela()) {
            int tamanhoPP = Math.min(controllerListaAmarela.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoPP; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d,%s,  \"Tipo de atendimento\": \"Prioritário I\"}",
                        numeroDeListasPercorrer++,controllerListaAmarela.obterFormatadoNome(i)));
            }
            contadorDasListas += tamanhoPP;
        }

        if (contadorDasListas < 10 && controllerListaVerde.temListaVerde()) {
            int tamanhoP = Math.min(controllerListaVerde.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoP; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s,  \"Tipo de atendimento\": \"Prioritário II\"}",
                        numeroDeListasPercorrer++,controllerListaVerde.obterFormatadoNome(i),controllerListaVerde.obterFormatado(i)));
            }
            contadorDasListas += tamanhoP;
        }

        if (contadorDasListas < 10 && controllerListaAzul.temListaAzul()) {
            int tamanhoB = Math.min(controllerListaAzul.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoB; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s,%s, \"Tipo de atendimento\": \"Geral\"}",
                        numeroDeListasPercorrer++,controllerListaAzul.obterFormatadoNome(i) ,controllerListaAzul.obterFormatado(i)));
            }
            contadorDasListas += tamanhoB;
        }

        resposta2.append(String.join(", ", itens2));
        resposta2.append("]");

        System.out.println("Resposta JSON gerada: " + resposta2);

        return resposta2.toString();
    }

}