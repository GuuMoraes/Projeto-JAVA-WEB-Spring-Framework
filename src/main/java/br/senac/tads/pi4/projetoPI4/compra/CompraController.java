package br.senac.tads.pi4.projetoPI4.compra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CompraController {

    @GetMapping("/gerenciar/listarPedidos")
    public ModelAndView listar() {

        List<Compra> listagem = new CompraRepositoryDAO().listar();
        ModelAndView mv = new ModelAndView("ListarPedidos");
        mv.addObject("lista", listagem);

        return mv;
    }

    @PostMapping("/gerenciar/listarPedidos")
    public ModelAndView buscar(@RequestParam("txtBuscar") String txtBuscar) {

        List<Compra> listagem = new CompraRepositoryDAO().listarN(txtBuscar);
        ModelAndView mv = new ModelAndView("ListarPedidos");
        mv.addObject("lista", listagem);

        return mv;
    }

    @GetMapping("/gerenciar/listarPedidos/processando/{id}")
    public ModelAndView processando(@PathVariable int id) {

        String estado = "Processando Pagamento";
        new CompraRepositoryDAO().mudarEstado(id, estado);

        return new ModelAndView("redirect:/gerenciar/listarPedidos");
    }

    @GetMapping("/gerenciar/listarPedidos/rejeitado/{id}")
    public ModelAndView rejeitado(@PathVariable int id) {

        String estado = "Pagamento Rejeitado";
        new CompraRepositoryDAO().mudarEstado(id, estado);

        return new ModelAndView("redirect:/gerenciar/listarPedidos");
    }

    @GetMapping("/gerenciar/listarPedidos/aprovado/{id}")
    public ModelAndView aprovado(@PathVariable int id) {

        String estado = "Pagamento Aprovado";
        new CompraRepositoryDAO().mudarEstado(id, estado);

        return new ModelAndView("redirect:/gerenciar/listarPedidos");
    }

    @GetMapping("/gerenciar/listarPedidos/download/{id}")
    public ModelAndView download(@PathVariable int id) {

        String estado = "Download Disponivel";
        new CompraRepositoryDAO().mudarEstado(id, estado);

        return new ModelAndView("redirect:/gerenciar/listarPedidos");
    }
}
