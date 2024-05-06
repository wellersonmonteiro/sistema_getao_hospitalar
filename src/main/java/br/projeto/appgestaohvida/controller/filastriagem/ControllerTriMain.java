package br.projeto.appgestaohvida.controller.filastriagem;

import br.projeto.appgestaohvida.controller.filasatendimento.ContrellerListaA;
import br.projeto.appgestaohvida.controller.filasatendimento.ControllerListaP;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.pacientes.PacienteTriagemDTO;
import br.projeto.appgestaohvida.model.pacientes.SenhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/listatriagem/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
public class ControllerTriMain {
    @Autowired
    ControllerTriListaPP controllerTriListaPP;
    @Autowired
    ControllerTriListaP controllerTriListaP;
    @Autowired
    ControllerTriListaB controllerTriListaB;
    @Autowired
    ControllerTriListaA controllerTriListaA;
    @Autowired
    private ContrellerListaA contrellerListaA;
    @Autowired
    private ControllerListaP controllerListaP;

    @GetMapping
    public String main() {
        if (controllerTriListaA.temListaA()){
            String resposta = controllerTriListaA.obterPrimeiraSenha();
            controllerTriListaA.retirarLista();

            return resposta;
        }
       else if (controllerTriListaPP.temListaPP()) {
            String resposta = controllerTriListaPP.obterPrimeiraSenha();
            controllerTriListaPP.retirarLista();

            return resposta;

        } else if (controllerTriListaP.temListaP()) {

             String resposta = controllerTriListaP.obterPrimeiraSenha();
             controllerTriListaP.retirarLista();

            return resposta;

        }else{
            String resposta = controllerTriListaB.obterPrimeiraSenha();
            controllerTriListaB.retirarLista();

            return resposta;
        }
    }
    @GetMapping
    @RequestMapping("/conjuntolistas")
    public String conjuntoListas2() {
        StringBuilder resposta2 = new StringBuilder("[");
        List<String> itens2 = new ArrayList<>();

        int contadorDasListas = 0;
        int numeroDeListasPercorrer = 1;

        if (controllerTriListaA.temListaA()) {
            int tamanhoA = Math.min(controllerTriListaA.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoA; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s ,%s, \"Tipo de atendimento\": \"Emergência\"}",
                        numeroDeListasPercorrer++,controllerTriListaA.obterFormatadoNome(i) ,controllerTriListaA.obterFormatado(i)));
            }
            contadorDasListas += tamanhoA;
        }

        if (contadorDasListas < 10 && controllerTriListaPP.temListaPP()) {
            int tamanhoPP = Math.min(controllerTriListaPP.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoPP; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d,%s ,%s, \"Tipo de atendimento\": \"Prioritário I\"}",
                        numeroDeListasPercorrer++,controllerTriListaPP.obterFormatadoNome(i),controllerTriListaPP.obterFormatado(i)));
            }
            contadorDasListas += tamanhoPP;
        }

        if (contadorDasListas < 10 && controllerTriListaP.temListaP()) {
            int tamanhoP = Math.min(controllerTriListaP.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoP; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s, %s, \"Tipo de atendimento\": \"Prioritário II\"}",
                        numeroDeListasPercorrer++,controllerTriListaP.obterFormatadoNome(i),controllerTriListaP.obterFormatado(i)));
            }
            contadorDasListas += tamanhoP;
        }

        if (contadorDasListas < 10 && controllerTriListaB.temListaB()) {
            int tamanhoB = Math.min(controllerTriListaB.tamanhoList(), 10 - contadorDasListas);
            for (int i = 0; i < tamanhoB; i++) {
                itens2.add(String.format("{\"Número na Fila\": %d, %s,%s, \"Tipo de atendimento\": \"Geral\"}",
                        numeroDeListasPercorrer++,controllerTriListaB.obterFormatadoNome(i) ,controllerTriListaB.obterFormatado(i)));
            }
            contadorDasListas += tamanhoB;
        }

        resposta2.append(String.join(", ", itens2));
        resposta2.append("]");

        System.out.println("Resposta JSON gerada: " + resposta2);

        return resposta2.toString();
    }

    @DeleteMapping
    public String deletar(@RequestBody SenhaDTO senhaDTO){
        String senha = senhaDTO.getSenha();
        System.out.println("Tentando remover a senha: " + senha);
       boolean retornoA = controllerTriListaA.retirarFila(senha);
       boolean retornoB = controllerTriListaB.retirarFila(senha);
       boolean retornoPP = controllerTriListaPP.retirarFila(senha);
       boolean retornoP = controllerTriListaP.retirarFila(senha);
       if (retornoA){
           return  "feitoA";
       }else if(retornoB){
           return  "feitoB";
       }else if(retornoPP){return "feitoPP";} else if (retornoP) {
           return  "feitoP";
       }
        return  "Não feito";
    }

    @PostMapping
    public String inserirPaciente(@RequestBody PacienteTriagemDTO triagemDTO){
        Paciente paciente = new Paciente(triagemDTO.getSenha(), triagemDTO.getNome_completo(),
                triagemDTO.getCPF(), triagemDTO.getTelefone(), triagemDTO.getEmail(),triagemDTO.getRua(),
                triagemDTO.getCep(), triagemDTO.getGenero(), triagemDTO.getData_nascimento(), triagemDTO.getTipo_de_atendimento());

        System.out.println(paciente.getNome());
        if ("Emergência".equals(triagemDTO.getTipo_de_atendimento())) {
            controllerTriListaA.adicionarNovoPaciente(paciente);
        } else if ("Prioritário I".equals(triagemDTO.getTipo_de_atendimento())) {
            controllerTriListaPP.adicionarNovoPaciente(paciente);
        } else if ("Prioritário II".equals(triagemDTO.getTipo_de_atendimento())) {
            controllerTriListaP.adicionarNovoPaciente(paciente);
        }else if ("Geral".equals(triagemDTO.getTipo_de_atendimento())) {
            controllerTriListaB.adicionarNovoPaciente(paciente);
        }
        return "Criado";
    }
}