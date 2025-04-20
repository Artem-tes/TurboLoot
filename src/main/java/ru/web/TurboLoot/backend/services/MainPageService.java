package ru.web.TurboLoot.backend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.web.TurboLoot.backend.models.Case;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.repositories.CaseRepository;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MainPageService {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    WeaponRepository weaponRepository;

    Model model;

    public String returnPage(Model model, HttpServletRequest request) throws IOException {
        this.model = model;
        model.addAttribute("cases",caseRepository.getAllCases());
        model.addAttribute("user",request.getSession().getAttribute("user"));
        return "main";
    }

}