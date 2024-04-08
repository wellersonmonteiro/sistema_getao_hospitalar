package br.projeto.appgestaohvida.Controller;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/P")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")

public class ControllerListaP {
    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesP = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesP.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesP.getPrimeiro();
            listaPacientesP.excluirPrimeiro();
            return primeiroPaciente.getSenha();
        } else {
            return "Nenhuma senha disponível";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "P" + (valor + 1);
        Paciente novoPaciente = new Paciente(novaSenha); // Cria um novo paciente com a nova senha
        listaPacientesP.adicionar(String.valueOf(novoPaciente)); // Adiciona o novo paciente à lista
        valor++; //

        return "Paciente cadastrado com senha: " + novaSenha;
    }
    public boolean temListaP() {
        return (listaPacientesP.getTamanho() > 0);
    }
}
