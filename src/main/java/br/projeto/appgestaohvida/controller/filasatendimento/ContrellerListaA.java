package br.projeto.appgestaohvida.controller.filasatendimento;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/A")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end

public class ContrellerListaA {
    private int valor = 0;
    @Autowired
    private ListasPacientes<Paciente> listaPacientesA; // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesA.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesA.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\"}";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "A" + (valor + 1);
        Paciente novoPaciente = new Paciente(novaSenha); // Cria um novo paciente com a nova senha
        listaPacientesA.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "Paciente cadastrado com senha: " + novaSenha;
    }
    @DeleteMapping
    public String retirarLista(){
        if (temListaA()){
            listaPacientesA.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        }
        else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaA() {
        return (listaPacientesA.getTamanho() > 0);
    }
}
