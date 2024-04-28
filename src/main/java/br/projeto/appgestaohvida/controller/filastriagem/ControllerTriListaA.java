package br.projeto.appgestaohvida.controller.filastriagem;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listatriagem/A")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end

public class ControllerTriListaA {
    private int valor = 0;

    private ListasPacientes<Paciente> listaTriPacientesA = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaTriPacientesA.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesA.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "A" + (valor + 1);
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaTriPacientesA.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "Paciente cadastrado com senha: " + novaSenha;
    }
    @DeleteMapping
    public String retirarLista(){
        if (temListaA()){
            listaTriPacientesA.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaA() {
        return (listaTriPacientesA.getTamanho() > 0);
    }
}