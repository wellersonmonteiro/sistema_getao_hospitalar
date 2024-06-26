package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultorio/amarela")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")
public class ControllerListaAmarela {
    private int valor = 0;
    private ListasPacientes<Paciente> listaAmarela = new ListasPacientes<>();

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaAmarela.getTamanho() > 0) {
            Paciente primeiroPaciente = listaAmarela.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "Amarela" ;
        Paciente novoPaciente = new Paciente(novaSenha);
        listaAmarela.adicionar(String.valueOf(novoPaciente));
        valor++;
        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaAmarela()){
            listaAmarela.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaAmarela() {
        return (listaAmarela.getTamanho() > 0);
    }
    public int tamanhoList(){
        return listaAmarela.getTamanho();
    }

    public String obterFormatadoNome(int indice){

        Paciente primeiroPaciente = listaAmarela.getElemento(indice);
        return String.format("\"nome\":\"%s\",\"senhaCor\":\"%s\",\"senha\":\"%s\"",
                primeiroPaciente.getNome(), primeiroPaciente.getSenhaCor(), primeiroPaciente.getSenha());
    }
    public String obterFormatado(int indice){
        if(listaAmarela.getTamanho() > 0) {
            Paciente primeiroPaciente = listaAmarela.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }
    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaAmarela.adicionarGererico(paciente);
        String novaSenha = "Amarela";
        paciente.setSenhaCor(novaSenha);
        valor++;
        return "{\"senha\":\""+paciente.getSenha()+"\"}";
    }
}
