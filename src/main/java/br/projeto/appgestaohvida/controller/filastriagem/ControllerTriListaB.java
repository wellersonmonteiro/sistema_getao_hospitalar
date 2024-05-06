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
        listaTriPacientesB.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "{\"senha\":\""+novaSenha+"\"}" ;
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaB()){
            listaTriPacientesB.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }
    public boolean temListaB() {
        return (listaTriPacientesB.getTamanho() > 0);
    }

    public String obterFormatado(int indice){
        if(listaTriPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesB.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public int tamanhoList(){
        return listaTriPacientesB.getTamanho();
    }
    @DeleteMapping("/tirarFila")
    public boolean retirarFila(String senha) {
        int indexToRemove = -1; // Inicializa como -1 para indicar que nenhum elemento correspondente foi encontrado
        for (int i = 0; i < listaTriPacientesB.getTamanho(); i++) {
            String senhaComparar = listaTriPacientesB.getElemento(i).getSenha();
            if (senhaComparar.equals(senha)) {
                indexToRemove = i; // Armazena a posição do elemento a ser removido
                break; // Sai do loop assim que encontrar o elemento
            }
        }

        if (indexToRemove != -1) { // Verifica se um elemento correspondente foi encontrado
            listaTriPacientesB.removerPorIndice(indexToRemove); // Remove o elemento da lista
            return true;
        } else {
            return false;
        }
    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaTriPacientesB.adicionarGererico(paciente);;
        return "{\"senha\":\""+paciente.getSenha()+"\"}";
    }
    public String obterFormatadoNome(int indice){
        if(listaTriPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesB.getElemento(indice);
            return String.format("\"nome\":\"%s\"",
                    primeiroPaciente.getNome());
        }else{
            return "";
        }
    }
}