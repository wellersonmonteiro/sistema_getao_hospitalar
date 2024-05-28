package br.projeto.appgestaohvida.controller.filasconsultorio;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultorio/azul")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")
public class ControllerListaAzul {
    private final ControllerListaVerde controllerListaVerde;
    private int valor = 0;
    private ListasPacientes<Paciente> listaAzul = new ListasPacientes<>();

    public ControllerListaAzul(ControllerListaVerde controllerListaVerde) {
        this.controllerListaVerde = controllerListaVerde;
    }

    @GetMapping
    public String obterPrimeiraSenha() {
        if (listaAzul.getTamanho() > 0) {
            Paciente primeiroPaciente = listaAzul.getPrimeiro();
            return "{\"senha\":\""+  primeiroPaciente.getSenha()+"\",\"hora\"" +
                    ":"+primeiroPaciente.getHora()+"}";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    @PostMapping
    public String cadastrarNovoPaciente() {
        String novaSenha = "Azul";
        Paciente novoPaciente = new Paciente(novaSenha);
        listaAzul.adicionar(String.valueOf(novoPaciente));
        valor++;
        return "{\"senha\":\""+novaSenha+"\"}";
    }

    @DeleteMapping
    public String retirarLista(){
        if (temListaAzul()){
            listaAzul.excluirPrimeiro();
            return "Paciente removido com sucesso!";
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }

    public boolean temListaAzul() {
        return (listaAzul.getTamanho() > 0);
    }

    public int tamanhoList(){
        return listaAzul.getTamanho();
    }
    public String obterFormatadoNome(int indice){

        Paciente primeiroPaciente = listaAzul.getElemento(indice);
        return String.format("\"nome\":\"%s\",\"senhaCor\":\"%s\",\"senha\":\"%s\"",
                primeiroPaciente.getNome(), primeiroPaciente.getSenhaCor(), primeiroPaciente.getSenha());
    }
    public String obterFormatado(int indice){
        if(listaAzul.getTamanho() > 0) {
            Paciente primeiroPaciente = listaAzul.getElemento(indice);
            return String.format("\"hora\":\"%s\",\"senha\":\"%s\"",
                    primeiroPaciente.getHora(), primeiroPaciente.getSenha());
        }else{
            return "";
        }

    }
    public String adicionarNovoPaciente(Paciente paciente) {
        listaAzul.adicionarGererico(paciente);
        String novaSenha = "Azul";
        paciente.setSenhaCor(novaSenha);
        valor++;
        return "{\"senha\":\""+paciente.getSenha()+"\"}";
    }
}
