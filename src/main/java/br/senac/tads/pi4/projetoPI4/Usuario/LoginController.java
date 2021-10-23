package br.senac.tads.pi4.projetoPI4.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @GetMapping("/login")
    public ModelAndView loginCliente() {

        return new ModelAndView("ClienteLogin");
    }

    @GetMapping("/negado")
    public ModelAndView negado() {

        return new ModelAndView("AcessoNegado");
    }
}
