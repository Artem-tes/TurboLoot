package ru.web.TurboLoot.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.web.TurboLoot.backend.services.MainPageService;

@Controller
public class MainPageController {

    @Autowired
    @Qualifier("mainPageService")
    MainPageService mainPageService;

    @GetMapping("/")
    private String getMainPage(Model model){
        return mainPageService.returnPage(model);
    }


}
