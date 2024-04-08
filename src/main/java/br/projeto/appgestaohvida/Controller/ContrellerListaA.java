package br.projeto.appgestaohvida.Controller;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas/A")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")

public class ContrellerListaA {
    private int valor = 0;
    private ListasPacientes<Paciente> listaPacientesA = new ListasPacientes<>(); // Instância da lista de pacientes

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaPacientesA.getTamanho() > 0) {
            Paciente primeiroPaciente = listaPacientesA.getPrimeiro();
            listaPacientesA.excluirPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\"}";
        } else {
            return "Nenhuma senha disponível";
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
    public boolean temListaA() {
        return (listaPacientesA.getTamanho() > 0);
    }
}
