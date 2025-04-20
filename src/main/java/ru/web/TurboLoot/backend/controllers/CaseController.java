package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.Thymeleaf;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.repositories.CaseRepository;
import ru.web.TurboLoot.backend.services.CaseService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/cases/get-item")
    public ResponseEntity<Map<String,Object>> getWeapons(@RequestParam("case") String name, HttpServletRequest request){
        return ResponseEntity.ok(caseService.returnProm(name,request));
    }

    @PostMapping("/cases/open-case")
    public ResponseEntity<Map<String,Object>> openCase(@RequestBody Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = caseService.openCase((String) data.get("caseName"),request);
        return ResponseEntity.ok(response);
    }
}
