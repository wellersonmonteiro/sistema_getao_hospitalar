package br.projeto.appgestaohvida.controller.filastriagem;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listatriagem/PP")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end


public class ControllerTriListaPP {

    private int valor = 0;
    private ListasPacientes<Paciente> listaTriPacientesPP = new ListasPacientes<>(); // Instância da lista de pacientes


    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaTriPacientesPP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesPP.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";        } else {

            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "PP" + (valor + 1); // Gera uma nova senha
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);        listaTriPacientesPP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; // Incrementa o valor para a próxima senha

        return "{\"senha\":\""+novaSenha+"\"}";
    }
    @DeleteMapping
    public String retirarLista(){
        if (temListaPP()){
        listaTriPacientesPP.excluirPrimeiro();
        return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaPP() {
        return (listaTriPacientesPP.getTamanho() > 0);
    }
}