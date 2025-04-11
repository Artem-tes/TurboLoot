package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.web.TurboLoot.backend.services.CaseService;

@Controller
public class CaseController {

    @Autowired
    CaseService caseService;

    @GetMapping("/case")
    private String returnCasePage(Model model,
                                  @RequestParam("case") String caseName,
                                  HttpServletRequest request){
        return caseService.returnPageCase(model,caseName, request);

    }
}
