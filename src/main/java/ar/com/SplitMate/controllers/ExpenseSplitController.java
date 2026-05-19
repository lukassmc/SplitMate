package ar.com.splitmate.controllers;


import ar.com.splitmate.servicios.ExpenseSplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/expenseSplit")
public class ExpenseSplitController {
    @Autowired
    private ExpenseSplitService splitService;

    @PostMapping("/splits/{id}/pay")
    public String paySplit(@PathVariable Long id,
                           @RequestParam Long groupId) {

        splitService.marcarPagado(id);

        return "redirect:/groups/" + groupId;
    }

}
