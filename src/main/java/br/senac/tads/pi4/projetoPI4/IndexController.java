package br.senac.tads.pi4.projetoPI4;

import br.senac.tads.pi4.projetoPI4.Usuario.UsuarioRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.carrinho.CarrinhoController;
import br.senac.tads.pi4.projetoPI4.cliente.Cliente;
import br.senac.tads.pi4.projetoPI4.cliente.ClienteRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.curso.Curso;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepositoryDAO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class IndexController {

    @GetMapping
    public ModelAndView listar(){

        List<Curso> listagem = new CursoRepositoryDAO().listarAtivos();

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("lista", listagem);
        return mv;
    }

    @PostMapping()
    public ModelAndView buscar(@RequestParam("txtBuscar") String txtBuscar) {

        List<Curso> listagem = new CursoRepositoryDAO().listarNome(txtBuscar);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("lista", listagem);
        return mv;
    }

}
