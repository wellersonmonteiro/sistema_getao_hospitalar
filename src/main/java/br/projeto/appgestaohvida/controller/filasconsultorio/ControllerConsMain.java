package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String adicionarPaciente(@RequestBody Paciente condicoes) {
        String corFila;

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
                controllerListaVermelha.cadastrarNovoPaciente();
                break;
            case "amarela":
                // Adicionar a lista amarela
                controllerListaAmarela.cadastrarNovoPaciente();
                break;
            case "verde":
                // Adicionar a lista verde
                controllerListaVerde.cadastrarNovoPaciente();
                break;
            case "azul":
                // Adicionar a lista azul
                controllerListaAzul.cadastrarNovoPaciente();
                break;
            default:

                break;
        }

        return "Paciente adicionado com sucesso a fila " + corFila;
    }



}
