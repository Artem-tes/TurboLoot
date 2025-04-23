package ru.web.TurboLoot.backend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.repositories.CaseRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;
import ru.web.TurboLoot.backend.services.MainPageService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MainPageController {

    @Autowired
    @Qualifier("mainPageService")
    MainPageService mainPageService;

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @GetMapping("/")
    private String getMainPage(Model model, HttpServletRequest request) throws IOException {
        return mainPageService.returnPage(model, request);
    }

    @GetMapping("/get-data/main-page")
    public ResponseEntity<Map<String, Object>> getUserData(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("user",request.getSession().getAttribute("user"));
        map.put("cases",caseRepository.getAllCases());


        /*String json = "src/main/java/ru/web/TurboLoot/backend/models/jsondata/weapons.json";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Чтение JSON из файла
            File file = new File(json);

            // Преобразуем JSON в список объектов Weapon
            List<Weapon> weapons = objectMapper.readValue(file, new TypeReference<List<Weapon>>() {});
            for (Weapon weapon : weapons) {
                weaponRepository.save(weapon);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/



        return ResponseEntity.ok(map);
    }


}
