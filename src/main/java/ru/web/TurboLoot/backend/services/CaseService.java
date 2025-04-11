package ru.web.TurboLoot.backend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.web.TurboLoot.backend.models.Case;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.repositories.CaseRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class CaseService {

    @Autowired
    @Qualifier("caseRepository")
    CaseRepository caseRepository;

    @Autowired
    WeaponRepository weaponRepository;

    public String returnPageCase(Model model, String caseName, HttpServletRequest request){
        Case aCase = caseRepository.getCaseByName(caseName);
        model.addAttribute("case",aCase);
        model.addAttribute("user",request.getParameter("user"));
        model.addAttribute("items",getWeaponsToModel(aCase));
        return "casepage";
    }


    private List<Weapon> getWeaponsToModel(Case aCase){
        List<Weapon> weapons = new ArrayList<>();
        List<Integer> integers = aCase.getWeapons();
        for (Integer integer : integers) {
            weapons.add(weaponRepository.getWeaponById(integer));
        }
//        for (Weapon weapon : weapons) {
//            weapon.setPrice(new Random().nextInt());
//        }
        return weapons;
    }
}