package br.projeto.appgestaohvida.controller.filasatendimento;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listas/PP")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end


public class ControllerListaPP {

    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesPP = new ListasPacientes<>(); // Instância da lista de pacientes


    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesPP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesPP.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";        } else {

            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "PP" + (valor + 1); // Gera uma nova senha
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaPacientesPP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; // Incrementa o valor para a próxima senha

        return "{\"senha\":\""+novaSenha+"\"}";
    }
    @DeleteMapping
    public String retirarLista(){
        if (temListaPP()){
        listaPacientesPP.excluirPrimeiro();
        return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaPP() {
        return (listaPacientesPP.getTamanho() > 0);
    }

    public String obterFormatado(int indice){
        if(listaPacientesPP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesPP.getElemento(indice);
            return "hora\":\"" +primeiroPaciente.getHora() +"\",\"senha\"" +
                    ":"+primeiroPaciente.getSenha();
        }else{
            return "";
        }
    }
    public int tamanhoList(){
        return listaPacientesPP.getTamanho();
    }
}