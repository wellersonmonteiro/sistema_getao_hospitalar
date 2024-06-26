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
            return String.format("{\"senha\":\"%s\",\"hora\":\"%s\"}",
                    primeiroPaciente.getSenha(), primeiroPaciente.getHora());
        } else {

            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "PP" + (valor + 1); // Gera uma nova senha
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaTriPacientesPP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
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

    public String obterFormatado(int indice){
        if(listaTriPacientesPP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesPP.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                   primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public int tamanhoList(){
        return listaTriPacientesPP.getTamanho();
    }
    @DeleteMapping("/tirarFila")
    public boolean retirarFila(String senha) {
        int indexToRemove = -1; // Inicializa como -1 para indicar que nenhum elemento correspondente foi encontrado
        for (int i = 0; i < listaTriPacientesPP.getTamanho(); i++) {
            String senhaComparar = listaTriPacientesPP.getElemento(i).getSenha();
            if (senhaComparar.equals(senha)) {
                indexToRemove = i; // Armazena a posição do elemento a ser removido
                break; // Sai do loop assim que encontrar o elemento
            }
        }

        if (indexToRemove != -1) { // Verifica se um elemento correspondente foi encontrado
            listaTriPacientesPP.removerPorIndice(indexToRemove); // Remove o elemento da lista
            return true;
        } else {
            return false;
        }
    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaTriPacientesPP.adicionarGererico(paciente);
        return "{\"senha\":\"" + paciente.getSenha() + "\"}";
    }
    public String obterFormatadoNome(int indice){

            Paciente primeiroPaciente = listaTriPacientesPP.getElemento(indice);
            return String.format("\"nome\":\"%s\"",
                   primeiroPaciente.getNome() );
    }

}