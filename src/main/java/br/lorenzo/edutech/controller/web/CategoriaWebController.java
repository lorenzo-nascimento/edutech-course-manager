package br.lorenzo.edutech.controller.web;

import br.lorenzo.edutech.dto.CategoriaDTO;
import br.lorenzo.edutech.exception.CategoriaNaoEncontradaException;
import br.lorenzo.edutech.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/categorias")
public class CategoriaWebController {

    private final CategoriaService categoriaService;

    public CategoriaWebController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categorias/listar-categorias";
    }

    @GetMapping("/nova")
    public String mostrarFormulario(Model model) {
        model.addAttribute("categoria", new CategoriaDTO(null, ""));
        return "categorias/nova-categoria-form";
    }

    @PostMapping("/nova")
    public String cadastrar(@Valid @ModelAttribute("categoria") CategoriaDTO categoriaDTO,
                            BindingResult result,
                            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "categorias/nova-categoria-form";
        }

        try {
            categoriaService.create(categoriaDTO);
            attributes.addFlashAttribute("success", "Categoria cadastrada com sucesso!");
            return "redirect:/web/categorias";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "categorias/nova-categoria-form";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        CategoriaDTO categoriaDTO = categoriaService.findById(id);
        model.addAttribute("categoria", categoriaDTO);
        return "categorias/editar-categoria-form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id,
                                     @Valid @ModelAttribute("categoria") CategoriaDTO categoriaDTO,
                                     BindingResult result,
                                     RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "categorias/editar-categoria-form";
        }

        try {
            categoriaService.update(id, categoriaDTO);
            attributes.addFlashAttribute("success", "Categoria atualizada com sucesso!");
            return "redirect:/web/categorias";
        } catch (Exception e) {
            result.rejectValue("nome", "error.categoria", e.getMessage());
            return "categorias/editar-categoria-form";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            categoriaService.delete(id);
            attributes.addFlashAttribute("success", "Categoria excluída com sucesso!");
        } catch (IllegalStateException | CategoriaNaoEncontradaException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/categorias";
    }


}