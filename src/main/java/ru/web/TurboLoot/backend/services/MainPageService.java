package ru.web.TurboLoot.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.web.TurboLoot.backend.models.Case;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.repositories.CaseRepository;
import ru.web.TurboLoot.backend.repositories.UserRepository;

@Service
public class MainPageService {

    @Autowired
    CaseRepository caseRepository;

    Model model;

    public String returnPage(Model model){
        this.model = model;
        model.addAttribute("cases",caseRepository.getAllCases());
        return "main";
    }

}