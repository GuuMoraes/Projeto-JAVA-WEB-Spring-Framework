package br.senac.tads.pi4.projetoPI4.cliente;

import br.senac.tads.pi4.projetoPI4.Usuario.Usuario;
import br.senac.tads.pi4.projetoPI4.Usuario.UsuarioRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.compra.Compra;
import br.senac.tads.pi4.projetoPI4.compra.CompraRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.compra.ItensCompra;
import br.senac.tads.pi4.projetoPI4.compra.ItensCompraRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.curso.Curso;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepositoryDAO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {
    private Cliente cliente;

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("CadastroCliente");
        return mv;

    }

    @GetMapping("/cliente/conta")
    public ModelAndView conta() {
        buscarlogado();
        ModelAndView mv = new ModelAndView("PaginaCliente");
        mv.addObject("cliente", cliente);
        return mv;

    }

    @GetMapping("/cliente/pedido")
    public ModelAndView pedido() {
        buscarlogado();
        ModelAndView mv = new ModelAndView("PaginaPedido");
        List<Compra> compras = new ClienteRepositoryDAO().listarPedidos(cliente.getCodCliente());
        mv.addObject("compras", compras);
        return mv;
    }

    @GetMapping("/cliente/endereco")
    public ModelAndView endereco() {
        buscarlogado();
        ModelAndView mv = new ModelAndView("PaginaEndereco");
        mv.addObject("cliente", cliente);
        return mv;

    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrar(@ModelAttribute Cliente c) {

        c.setSenha(new BCryptPasswordEncoder().encode(c.getSenha()));
        Cliente cliente = new ClienteRepositoryDAO().cadastrar(c);
        ModelAndView mv = new ModelAndView("redirect:/login");

        return mv;
    }


    private void buscarlogado() {
        Authentication authe = SecurityContextHolder.getContext().getAuthentication();
        if (!(authe instanceof AnonymousAuthenticationToken)) {
            String email = authe.getName();
            cliente = new ClienteRepositoryDAO().buscarEmail(email);
        }
    }

    @GetMapping("/cliente/detalhespedido/{id}")
    public ModelAndView detalhepedido(@PathVariable int id) {
        ModelAndView mv = new ModelAndView("DetalhesPedido");
        Compra compra = new CompraRepositoryDAO().findById(id);
        List<ItensCompra> itenscompras = new ItensCompraRepositoryDAO().listarI(id);
        mv.addObject("compra", compra);
        mv.addObject("itenscompras", itenscompras);
        return mv;
    }

    @GetMapping("/gerenciar/listarClientes")
    public ModelAndView listarC() {
        buscarlogado();
        List<Cliente> listagem = new ClienteRepositoryDAO().listar();
        ModelAndView mv = new ModelAndView("ListarClientes");
        mv.addObject("lista", listagem);
        mv.addObject("user", cliente);

        return mv;
    }

    @PostMapping("/gerenciar/listarClientes")
    public ModelAndView buscarC(@RequestParam("txtBuscar") String txtBuscar) {

        List<Cliente> listagem = new ClienteRepositoryDAO().listarcpf(txtBuscar);
        ModelAndView mv = new ModelAndView("ListarClientes");
        mv.addObject("lista", listagem);
        mv.addObject("user", cliente);
        return mv;
    }

    @GetMapping("/editarEndereco")
    public ModelAndView editarEndereco() {
        buscarlogado();
        ModelAndView mv = new ModelAndView("EditarEndereco");
        mv.addObject("cliente", cliente);
        return mv;

    }

    @PostMapping("/editarEndereco")
    public ModelAndView editarEndereco(@ModelAttribute Cliente c) {

        c.setCodCliente(cliente.getCodCliente());
        new ClienteRepositoryDAO().editar(c);
        ModelAndView mv = new ModelAndView("redirect:/carrinho/cliente/finalizar");

        return mv;

    }
}
