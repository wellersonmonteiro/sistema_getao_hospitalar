package br.projeto.appgestaohvida.controller.filasatendimento;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listas/B")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true") //Colocar o endereço do servidor front end

public class ControllerListaB {
    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesB = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesB.getPrimeiro();
            return   String.format("{\"senha\":\"%s\",\"hora\":\"%s\"}",
                    primeiroPaciente.getSenha(), primeiroPaciente.getHora());
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "B" + (valor + 1);
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaPacientesB.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "{\"senha\":\""+novaSenha+"\"}" ;
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaB()){
            listaPacientesB.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }
    public boolean temListaB() {
        return (listaPacientesB.getTamanho() > 0);
    }

    public String obterFormatado(int indice){
        if(listaPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesB.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public int tamanhoList(){
        return listaPacientesB.getTamanho();
    }
}