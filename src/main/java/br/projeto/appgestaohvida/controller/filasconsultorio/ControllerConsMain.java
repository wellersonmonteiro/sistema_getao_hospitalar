package br.projeto.appgestaohvida.controller.filasconsultorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/consultorio/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
public class ControllerConsMain {
    @Autowired
    ControllerListaAmarela controllerListaAmarela;
    @Autowired
    ControllerListaVermelha controllerListaVermelha;
    @Autowired
    ControllerListaVerde controllerListaVerde;
    @Autowired
    ControllerListaAzul controllerListaAzul;

    @GetMapping
    public String main() {
        // Verificar e encaminhar para o consultório adequado
        if (controllerListaVermelha.temListaVermelha()){
            String resposta = controllerListaVermelha.obterPrimeiraSenha();
            controllerListaVermelha.retirarLista();
            return resposta;
        } else if (controllerListaAmarela.temListaAmarela()) {
            String resposta = controllerListaAmarela.obterPrimeiraSenha();
            controllerListaAmarela.retirarLista();
            return resposta;
        } else if (controllerListaVerde.temListaVerde()) {
            String resposta = controllerListaVerde.obterPrimeiraSenha();
            controllerListaVerde.retirarLista();
            return resposta;
        } else if (controllerListaAzul.temListaAzul()) {
            String resposta = controllerListaAzul.obterPrimeiraSenha();
            controllerListaAzul.retirarLista();
            return resposta;
        } else {
            return "{\"senha\":\"Sem atendimento\"}";
        }
    }
}
