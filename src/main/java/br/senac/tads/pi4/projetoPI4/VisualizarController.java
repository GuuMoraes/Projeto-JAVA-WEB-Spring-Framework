package br.senac.tads.pi4.projetoPI4;

import br.senac.tads.pi4.projetoPI4.curso.Curso;
import br.senac.tads.pi4.projetoPI4.curso.CursoRepositoryDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/visualizar")
public class VisualizarController {

    Curso curso = new Curso();
    @GetMapping()
    public ModelAndView visualizar() {
        ModelAndView mv = new ModelAndView("Visualizar Curso");
        mv.addObject("curso", curso);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView visualizar(@PathVariable int id) {

        curso = new CursoRepositoryDAO().findById(id);

        ModelAndView mv = new ModelAndView("redirect:/visualizar");
        mv.addObject("curso", curso);
        return mv;
    }


}
