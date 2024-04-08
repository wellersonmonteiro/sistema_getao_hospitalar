package br.projeto.appgestaohvida.Controller;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/PP")

public class ControllerListaPP {

    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesPP = new ListasPacientes<>(); // Instância da lista de pacientes


    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesPP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesPP.getPrimeiro();
            listaPacientesPP.excluirPrimeiro();
            return primeiroPaciente.getSenha(); // Retorna a senha do primeiro paciente
        } else {

            return "Nenhuma senha disponível";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "PP" + (valor + 1); // Gera uma nova senha
        Paciente novoPaciente = new Paciente(novaSenha); // Cria um novo paciente com a nova senha
        listaPacientesPP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; // Incrementa o valor para a próxima senha

        return "Paciente cadastrado com senha: " + novaSenha;
    }
    public boolean temListaPP() {
        return (listaPacientesPP.getTamanho() > 0);
    }
}