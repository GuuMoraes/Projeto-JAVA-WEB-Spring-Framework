package br.senac.tads.pi4.projetoPI4.curso;

import br.senac.tads.pi4.projetoPI4.Usuario.Usuario;
import br.senac.tads.pi4.projetoPI4.Usuario.UsuarioRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/gerenciar/listarCursos")
public class CursoController {

    //caminho pasta de imagens
    private String caminhopasta = "/Users/gusta/OneDrive/Documentos/Intellij/Nova pasta (2)/projetoPI4/src/main/resources/static/Imagens/";

    private Usuario usuario;
    private Curso curso;

    @GetMapping
    public ModelAndView listar() {
        buscarlogado();
        List<Curso> listagem = new CursoRepositoryDAO().listar();
        ModelAndView mv = new ModelAndView("ListarCursos");
        mv.addObject("lista", listagem);
        mv.addObject("user", usuario);
        return mv;
    }

    @PostMapping()
    public ModelAndView buscar(@RequestParam("txtBuscar") String txtBuscar) {

        List<Curso> listagem = new CursoRepositoryDAO().listarNome(txtBuscar);
        ModelAndView mv = new ModelAndView("ListarCursos");
        mv.addObject("lista", listagem);
        mv.addObject("user", usuario);
        return mv;
    }

    @GetMapping("/cadastroCurso")
    public ModelAndView cadastro() {

        return new ModelAndView("CadastrarCurso");
    }

    @PostMapping("/cadastrarCurso")
    public ModelAndView cadastrar(@ModelAttribute Curso c) {

        curso = new CursoRepositoryDAO().cadastrar(c);
        ModelAndView mv = new ModelAndView("redirect:/gerenciar/listarCursos/cadastroCursoImagens");
        mv.addObject("curso", c);
        return mv;
    }

    @GetMapping("/cadastroCursoImagens")
    public ModelAndView cadastroimg() {

        ModelAndView mv = new ModelAndView("CadastroImagensCurso");
        List<CursoImagens> imgs = new CursoRepositoryDAO().listarImgs(curso.getCodCurso());
        mv.addObject("imgs", imgs);
        mv.addObject("id", curso.getCodCurso());
        return mv;
    }

    @PostMapping("/cadastroCursoImagens")
    public ModelAndView cadastroimg(@RequestParam("file") MultipartFile img) {

        ModelAndView mv = new ModelAndView("redirect:/gerenciar/listarCursos/cadastroCursoImagens");

        try {
            if (!img.isEmpty()) {
                //salva a imagem na pasta definida
                byte[] bytes = img.getBytes();
                Path caminho = Paths.get(caminhopasta + img.getOriginalFilename());
                Files.write(caminho, bytes);

                //salva o caminho da imagem no banco
                CursoImagens imgs = new CursoImagens();
                imgs.setCaminho( img.getOriginalFilename());
                imgs.setPrincipal(0);
                imgs.setCursoId(curso.getCodCurso());
                new CursoRepositoryDAO().cadastrarImg(imgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    @GetMapping("/editarCurso/{id}")
    public ModelAndView editar(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("EditarCurso");
        Curso curso = new CursoRepositoryDAO().findById(id);
        mv.addObject("curso", curso);

        return mv;
    }

    @PostMapping("/editarCurso")
    public ModelAndView editar(@ModelAttribute Curso c) {

        new CursoRepositoryDAO().editar(c);

        return new ModelAndView("redirect:/gerenciar/listarCursos");
    }

    @GetMapping("/editarCursoImagens/{id}")
    public ModelAndView editarimg(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("EditarImagensCurso");

        List<CursoImagens> imgs = new CursoRepositoryDAO().listarImgs(id);
        mv.addObject("imgs", imgs);
        mv.addObject("id", id);
        return mv;
    }

    @PostMapping("/editarCursoImagens")
    public ModelAndView editarimg(@RequestParam("file") MultipartFile img, @RequestParam("codCurso") int id) {

        ModelAndView mv = new ModelAndView("redirect:/gerenciar/listarCursos/editarCursoImagens/"+id+"");

        try {
            if (!img.isEmpty()) {
                //salva a imagem na pasta definida
                byte[] bytes = img.getBytes();
                Path caminho = Paths.get(caminhopasta + img.getOriginalFilename());
                Files.write(caminho, bytes);

                //salva o caminho da imagem no banco
                CursoImagens imgs = new CursoImagens();
                imgs.setCaminho(img.getOriginalFilename());
                imgs.setPrincipal(0);
                imgs.setCursoId(id);
                new CursoRepositoryDAO().cadastrarImg(imgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    @GetMapping("/deletarImg/{id}")
    public ModelAndView deletarImg(@PathVariable int id) {

        new CursoRepositoryDAO().deletarImg(id);

        return new ModelAndView("redirect:/gerenciar/listarCursos/cadastroCursoImagens");
    }

    @GetMapping("/deletarTudo/{id}")
    public ModelAndView deletarTudo(@PathVariable int id) {

        new CursoRepositoryDAO().deletarTudo(id);

        return new ModelAndView("redirect:/gerenciar/listarCursos");
    }


    @GetMapping("/onoffCurso/{id}")
    public ModelAndView onoffCurso(@PathVariable int id) {

        Curso curso = new CursoRepositoryDAO().findById(id);
        new CursoRepositoryDAO().onoff(curso.getEstado(), id);

        return new ModelAndView("redirect:/gerenciar/listarCursos");
    }

    private void buscarlogado() {
        Authentication authe = SecurityContextHolder.getContext().getAuthentication();
        if (!(authe instanceof AnonymousAuthenticationToken)) {
            String email = authe.getName();
            usuario = new UsuarioRepositoryDAO().buscarEmail(email);
        }
    }
}
