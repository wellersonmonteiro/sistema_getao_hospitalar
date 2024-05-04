package br.projeto.appgestaohvida.controller.filastriagem;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.infra.Hora;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/listatriagem/P")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end

public class ControllerTriListaP {
    private int valor = 0;
    private ListasPacientes<Paciente> listaTriPacientesP = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaTriPacientesP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesP.getPrimeiro();
            return String.format("{\"senha\":\"%s\",\"hora\":\"%s\"}",
                    primeiroPaciente.getSenha(), primeiroPaciente.getHora());
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "P" + (valor + 1);
        Hora horaAtual = ()-> LocalTime.now();
        Paciente novoPaciente = new Paciente(horaAtual.toString(),novaSenha);
        listaTriPacientesP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaP()){
            listaTriPacientesP.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }
    public boolean temListaP() {
        return (listaTriPacientesP.getTamanho() > 0);
    }
    public String obterFormatado(int indice){
        if(listaTriPacientesP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaTriPacientesP.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public int tamanhoList(){
        return listaTriPacientesP.getTamanho();
    }
    @DeleteMapping("/tirarFila")
    public boolean retirarFila(String senha) {
        int indexToRemove = -1; // Inicializa como -1 para indicar que nenhum elemento correspondente foi encontrado
        for (int i = 0; i < listaTriPacientesP.getTamanho(); i++) {
            String senhaComparar = listaTriPacientesP.getElemento(i).getSenha();
            if (senhaComparar.equals(senha)) {
                indexToRemove = i; // Armazena a posição do elemento a ser removido
                break; // Sai do loop assim que encontrar o elemento
            }
        }

        if (indexToRemove != -1) { // Verifica se um elemento correspondente foi encontrado
            listaTriPacientesP.removerPorIndice(indexToRemove); // Remove o elemento da lista
            return true;
        } else {
            return false;
        }
    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaTriPacientesP.adicionar(String.valueOf(paciente));
        return "{\"senha\":\""+paciente.getSenha()+"\"}";
    }
}
