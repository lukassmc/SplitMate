package ar.com.splitmate.api.rest;


import ar.com.splitmate.servicios.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/split")
public class ExpenseSplitRestController {


    private final ExpenseSplitService splitService;

    public ExpenseSplitRestController(ExpenseSplitService splitService)
    {

        this.splitService = splitService;

    }


    @PostMapping("/paid")
    public void markAsPaid(@RequestParam("splitId") Long splitId ){

        if (splitId == null){
            throw new NullPointerException("Id de split invalido.");
        }

        this.splitService.markAsPaid(splitId);

    }
}
