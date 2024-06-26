package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultorio/verde")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")
public class ControllerListaVerde {
    private int valor = 0;
    private ListasPacientes<Paciente> listaVerde = new ListasPacientes<>();

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaVerde.getTamanho() > 0) {
            Paciente primeiroPaciente = listaVerde.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "Verde" ;
        Paciente novoPaciente = new Paciente(novaSenha);
        listaVerde.adicionar(String.valueOf(novoPaciente));
        valor++;
        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaVerde()){
            listaVerde.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaVerde() {
        return (listaVerde.getTamanho() > 0);
    }
    public int tamanhoList(){
        return listaVerde.getTamanho();
    }
    public String obterFormatadoNome(int indice){

        Paciente primeiroPaciente = listaVerde.getElemento(indice);
        return String.format("\"nome\":\"%s\",\"senhaCor\":\"%s\",\"senha\":\"%s\"",
                primeiroPaciente.getNome(), primeiroPaciente.getSenhaCor(), primeiroPaciente.getSenha());
    }
    public String obterFormatado(int indice){
        if(listaVerde.getTamanho() > 0) {
            Paciente primeiroPaciente = listaVerde.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaVerde.adicionarGererico(paciente);
        String novaSenha = "Verde";
        paciente.setSenhaCor(novaSenha);
        valor++;
        return "{\"senha\":\""+paciente.getSenha()+"\"}";
    }
}
