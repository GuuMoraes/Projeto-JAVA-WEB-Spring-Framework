package br.senac.tads.pi4.projetoPI4.Usuario;

import br.senac.tads.pi4.projetoPI4.cliente.Cliente;
import br.senac.tads.pi4.projetoPI4.cliente.ClienteRepositoryDAO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/gerenciar/listarUsuarios")
public class UsuarioController {

    //caminho pasta de imagens
    private String caminhopasta = "/Users/gusta/OneDrive/Documentos/Intellij/Nova pasta (2)/projetoPI4/src/main/resources/static/Imagens/";

    private Usuario usuario;

    @GetMapping
    public ModelAndView listar() {
        buscarlogado();
        List<Usuario> listagem = new UsuarioRepositoryDAO().listar();
        ModelAndView mv = new ModelAndView("ListarUsuarios");
        mv.addObject("lista", listagem);
        mv.addObject("user", usuario);
        return mv;
    }

    @PostMapping()
    public ModelAndView buscar(@RequestParam("txtBuscar") String txtBuscar) {

        List<Usuario> listagem = new UsuarioRepositoryDAO().buscarNome(txtBuscar);
        ModelAndView mv = new ModelAndView("ListarUsuarios");
        mv.addObject("lista", listagem);
        mv.addObject("user", usuario);

        return mv;
    }

    @GetMapping("/cadastroUsuario")
    public ModelAndView cadastro() {

        return new ModelAndView("CadastrarUsuario");
    }

    @PostMapping("/cadastrarUsuario")
    public ModelAndView cadastrar(@ModelAttribute Usuario u, @RequestParam("file") MultipartFile img) {

        u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
        try {
            if (!img.isEmpty()) {
                //salva a imagem na pasta definida
                byte[] bytes = img.getBytes();
                Path caminho = Paths.get(caminhopasta + img.getOriginalFilename());
                Files.write(caminho, bytes);

                //salva o caminho da imagem no banco
                u.setImg(img.getOriginalFilename());
                new UsuarioRepositoryDAO().cadastrar(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView("redirect:/gerenciar/listarUsuarios");

        return mv;
    }

    @GetMapping("/editarUsuario/{id}")
    public ModelAndView editarUsuario(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("EditarUsuario");
        Usuario usuario = new UsuarioRepositoryDAO().findById(id);
        mv.addObject("usuario", usuario);

        return mv;
    }

    @PostMapping("/editarUsuario")
    public ModelAndView editarUsuario(@ModelAttribute Usuario u, @RequestParam("file") MultipartFile img) {

        try {
            if (!img.isEmpty()) {
                //salva a imagem na pasta definida
                byte[] bytes = img.getBytes();
                Path caminho = Paths.get(caminhopasta + img.getOriginalFilename());
                Files.write(caminho, bytes);

                //salva o caminho da imagem no banco
                u.setImg(img.getOriginalFilename());
                new UsuarioRepositoryDAO().editar(u);
            }else {
                new UsuarioRepositoryDAO().editar(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/gerenciar/listarUsuarios");

    }

    @GetMapping("/verUsuario/{id}")
    public ModelAndView verUsuario(@PathVariable int id) {

        ModelAndView mv = new ModelAndView("VisualizarUsuario");
        Usuario usuario = new UsuarioRepositoryDAO().findById(id);
        mv.addObject("usuario", usuario);

        return mv;
    }

    @GetMapping("/onoffUsuario/{id}")
    public ModelAndView onoffUsuario(@PathVariable int id) {

        Usuario usuario = new UsuarioRepositoryDAO().findById(id);
        new UsuarioRepositoryDAO().onoff(usuario.getEstado(), id);

        return new ModelAndView("redirect:/gerenciar/listarUsuarios");
    }

    private void buscarlogado() {
        Authentication authe = SecurityContextHolder.getContext().getAuthentication();
        if (!(authe instanceof AnonymousAuthenticationToken)) {
            String email = authe.getName();
            usuario = new UsuarioRepositoryDAO().buscarEmail(email);
        }
    }


}
