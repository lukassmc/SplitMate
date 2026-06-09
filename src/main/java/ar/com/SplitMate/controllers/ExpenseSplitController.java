package ar.com.splitmate.controllers;


import ar.com.splitmate.servicios.ExpenseSplitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/splits")
public class ExpenseSplitController {

    private final ExpenseSplitService splitService;

    public ExpenseSplitController(ExpenseSplitService splitService) {
        this.splitService = splitService;
    }

    /**
     * Marca un split como pagado y redirige de vuelta al detalle del grupo.
     * groupId viene como parámetro para saber a dónde volver.
     */
    @PostMapping("/{splitId}/paid")
    public String markAsPaid(@PathVariable Long splitId,
                             @RequestParam Long groupId,
                             HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        splitService.markAsPaid(splitId);
        return "redirect:/groups/" + groupId;
    }
}
