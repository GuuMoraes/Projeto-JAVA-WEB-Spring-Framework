package br.senac.tads.pi4.projetoPI4;

import br.senac.tads.pi4.projetoPI4.Usuario.Usuario;
import br.senac.tads.pi4.projetoPI4.Usuario.UsuarioRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.cliente.ClienteRepositoryDAO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GerenciarController {

    private Usuario usuario;

    @GetMapping("/gerenciar")
    public ModelAndView gerenciar() {
        ModelAndView mv =new ModelAndView("Gerenciar");
        buscarlogado();
        mv.addObject("user", usuario);
        return mv;
    }

    private void buscarlogado() {
        Authentication authe = SecurityContextHolder.getContext().getAuthentication();
        if (!(authe instanceof AnonymousAuthenticationToken)) {
            String email = authe.getName();
            usuario = new UsuarioRepositoryDAO().buscarEmail(email);
        }
    }
}
