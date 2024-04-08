package br.projeto.appgestaohvida.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listas/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")
public class controllerMain {
    @Autowired
    ControllerListaPP controllerListaPP;
    @Autowired
    ControllerListaP controllerListaP;
    @Autowired
    ControllerListaB controllerListaB;
    @Autowired
    ContrellerListaA contrellerListaA;
    @GetMapping
    public String main() {
        if (contrellerListaA.temListaA()){
            return contrellerListaA.obterPrimeiraSenha();
        }
       else if (controllerListaPP.temListaPP()) {
            return controllerListaPP.obterPrimeiraSenha();
        } else if (controllerListaP.temListaP()) {
            return controllerListaP.obterPrimeiraSenha();
        }else{
            return controllerListaB.obterPrimeiraSenha();
        }
    }
}