package br.senac.tads.pi4.projetoPI4.carrinho;

import br.senac.tads.pi4.projetoPI4.cliente.Cliente;
import br.senac.tads.pi4.projetoPI4.cliente.ClienteRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.compra.Compra;
import br.senac.tads.pi4.projetoPI4.compra.CompraRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.compra.ItensCompra;
import br.senac.tads.pi4.projetoPI4.compra.ItensCompraRepositoryDAO;
import br.senac.tads.pi4.projetoPI4.curso.Curso;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepository;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepositoryDAO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String sessionC = "session";

    private List<ItensCompra> itensCompras = new ArrayList<>();
    private Compra compra = new Compra();
    private Cliente cliente = new Cliente();
    private ItensCompraRepositoryDAO itensCompraRepositoryDAO;


    @GetMapping
    public ModelAndView carrinho() {
        ModelAndView mv = new ModelAndView("Carrinho");
        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaCompras", itensCompras);
        return mv;
    }

    @GetMapping("/addCarrinho/{id}")
    public ModelAndView adicionarCurso(@PathVariable int id) {

        Curso cs = new CursoRepositoryDAO().findById(id);
        for (ItensCompra it : itensCompras) {
            if (it.getCurso().getCodCurso() == cs.getCodCurso()) {
                return new ModelAndView("redirect:/carrinho");
            }
        }
        ItensCompra item = new ItensCompra();
        item.setCurso(cs);
        item.setValorUnitario(cs.getPreco());

        itensCompras.add(item);

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/remover/{id}")
    public ModelAndView remover(@PathVariable int id) {
        for (ItensCompra it : itensCompras) {
            if (it.getCurso().getCodCurso() == id) {
                itensCompras.remove(it);
                break;
            }
        }

        return new ModelAndView("redirect:/carrinho");
    }

    private void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItensCompra it : itensCompras) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorUnitario());
        }
    }

    private void buscarlogado() {
        Authentication authe = SecurityContextHolder.getContext().getAuthentication();
        if (!(authe instanceof AnonymousAuthenticationToken)) {
            String email = authe.getName();
            cliente = new ClienteRepositoryDAO().buscarEmail(email);
        }
    }

    @GetMapping("/cliente/finalizar")
    public ModelAndView finalizar() {
        buscarlogado();
        if (itensCompras.isEmpty()){
            return new ModelAndView("redirect:/carrinho");
        }else {
            ModelAndView mv = new ModelAndView("FinalizarCompra");
            mv.addObject("compra", compra);
            mv.addObject("listaCompras", itensCompras);
            mv.addObject("cliente", cliente);
            return mv;
        }
    }

    @PostMapping("/cliente/concluir")
    public ModelAndView concluircompra(@RequestParam("formPag") String formPag) {
        buscarlogado();

        compra.setCliente(cliente);
        compra.setFormaPag(formPag);
        compra.setEstado("Aguardando Pagamento");

        compra = new CompraRepositoryDAO().salvar(compra);
        ModelAndView mv = new ModelAndView("redirect:/carrinho/cliente/concluir/"+compra.getCodCompra()+"");
        for (ItensCompra i : itensCompras) {
            i.setCompra(compra);
            new ItensCompraRepositoryDAO().salvar(i);

        }
        itensCompras = new ArrayList<>();
        compra = new Compra();

        return mv;
    }

    @GetMapping("/cliente/concluir/{id}")
    public ModelAndView concluircompra(@PathVariable int id) {
        buscarlogado();
        ModelAndView mv = new ModelAndView("ConcluirCompra");
        Compra compra = new CompraRepositoryDAO().findById(id);
        List<ItensCompra> itenscompras = new ItensCompraRepositoryDAO().listarI(id);
        mv.addObject("compra", compra);
        mv.addObject("itenscompras", itenscompras);
        mv.addObject("cliente", cliente);

        return mv;
    }

}
