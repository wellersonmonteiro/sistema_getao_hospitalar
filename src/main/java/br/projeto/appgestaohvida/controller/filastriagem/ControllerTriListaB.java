package br.projeto.appgestaohvida.controller.filastriagem;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listatriagem/B")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true") //Colocar o endereço do servidor front end

public class ControllerTriListaB {
    private int valor = 0;
    private ListasPacientes<Paciente> listaTriPacientesB = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaTriPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesB.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "B" + (valor + 1);
        Hora horaAtual = LocalTime::now;
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaTriPacientesB.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaP()){
            listaTriPacientesB.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }
    public boolean temListaP() {
        return (listaTriPacientesB.getTamanho() > 0);
    }
}