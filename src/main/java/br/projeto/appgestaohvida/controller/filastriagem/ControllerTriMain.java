package br.projeto.appgestaohvida.controller.filastriagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listatriagem/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:63342/", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
public class ControllerTriMain {
    @Autowired
    ControllerTriListaPP controllerTriListaPP;
    @Autowired
    ControllerTriListaP controllerTriListaP;
    @Autowired
    ControllerTriListaB controllerTriListaB;
    @Autowired
    ControllerTriListaA controllerTriListaA;
    @GetMapping
    public String main() {
        if (controllerTriListaA.temListaA()){
            String resposta = controllerTriListaA.obterPrimeiraSenha();
            controllerTriListaA.retirarLista();

            return resposta;
        }
       else if (controllerTriListaPP.temListaPP()) {
            String resposta = controllerTriListaPP.obterPrimeiraSenha();
            controllerTriListaPP.retirarLista();

            return resposta;

        } else if (controllerTriListaP.temListaP()) {

             String resposta = controllerTriListaP.obterPrimeiraSenha();
             controllerTriListaP.retirarLista();

            return resposta;

        }else{
            String resposta = controllerTriListaB.obterPrimeiraSenha();
            controllerTriListaB.retirarLista();

            return resposta;
        }
    }
}