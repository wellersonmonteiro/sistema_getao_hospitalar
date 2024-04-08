package br.projeto.appgestaohvida.Controller;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/B")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")

public class ControllerListaB {
    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesB = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesB.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesB.getPrimeiro();
            listaPacientesB.excluirPrimeiro();
            return primeiroPaciente.getSenha();
        } else {
            return "Nenhuma senha disponível";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "B" + (valor + 1);
        Paciente novoPaciente = new Paciente(novaSenha); // Cria um novo paciente com a nova senha
        listaPacientesB.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "Paciente cadastrado com senha: " + novaSenha;
    }
    public boolean temListaP() {
        return (listaPacientesB.getTamanho() > 0);
    }
}