package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.pacientes.PacienteDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultorio/vermelha")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")
public class ControllerListaVermelha {
    private int valor = 0;
private ListasPacientes<PacienteDTO> listaVermelha = new ListasPacientes<>();

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaVermelha.getTamanho() > 0) {
            Paciente primeiroPaciente = listaVermelha.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "Vermelha" + (valor + 1);
        Paciente novoPaciente = new Paciente(novaSenha);
        listaVermelha.adicionar(String.valueOf(novoPaciente));
        valor++;
        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaVermelha()){
            listaVermelha.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaVermelha() {
        return (listaVermelha.getTamanho() > 0);
    }
}