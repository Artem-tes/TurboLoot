package ru.web.TurboLoot.backend.services;

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

import java.util.*;
import java.util.stream.Stream;

@Service
public class CaseService {

    @Autowired
    @Qualifier("caseRepository")
    CaseRepository caseRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    UserRepository userRepository;

    public Map<String,Object> returnProm(String name,HttpServletRequest request){
        Case caseByName = caseRepository.getCaseByName(name);
        List<Integer> idItems = caseByName.getWeapons();
        List<Weapon> weapons = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (Integer idItem : idItems) {
            weapons.add(weaponRepository.getWeaponById(idItem));
        }
        map.put("case",caseByName);
        map.put("items",weapons);
        map.put("user",request.getSession().getAttribute("user"));
        map.put("chance",returnChanses(caseByName));
        return map;
    }

    private Map<String,Object> returnChanses(Case aCase){
        List<Integer> integers = aCase.getWeapons();
        Map<String,Object> response = new HashMap<>();
        float chanceMythic = getMythicChance(integers);
        float chanceCommon = getCommonChance(integers);
        response.put("mythic",Math.round(chanceMythic*100) +"%");
        response.put("common",Math.round(chanceCommon*100)+"%");
        return response;
    }

    private float getCommonChance(List<Integer> integers){
        float chance = 0;
        List<Weapon> weapons = new ArrayList<>();
        List<Weapon> commonUncommonWeapons = new ArrayList<>();
        for (Integer integer : integers) {
            weapons.add(weaponRepository.getWeaponById(integer));
        }
        for (Weapon weapon : weapons) {
            if(weapon.getRarity().equals("Uncommon")){
                commonUncommonWeapons.add(weapon);
            }
        }
        for (Weapon commonUncommonWeapon : commonUncommonWeapons) {
            chance+=commonUncommonWeapon.getChance();
        }
        return chance;
    }


    private float getMythicChance(List<Integer> integers){
        List<Weapon> weapons = new ArrayList<>();
        List<Weapon> legendaryWeapons = new ArrayList<>();
        float chanseMythic = 0;
        for (Integer i :integers) {
            weapons.add(weaponRepository.getWeaponById(i));
        }
        for (Weapon weapon : weapons) {
            if(weapon.getRarity().equals("Legendary")){
                legendaryWeapons.add(weapon);
            }
        }
        for (Weapon legendaryWeapon : legendaryWeapons) {
            chanseMythic= (float) (chanseMythic + legendaryWeapon.getChance());
        }

        return chanseMythic;
    }

    public String returnPageCase(Model model, String caseName, HttpServletRequest request){
        Case aCase = caseRepository.getCaseByName(caseName);
        model.addAttribute("case",aCase);
        model.addAttribute("user",request.getParameter("user"));
        model.addAttribute("items",getWeaponsToModel(aCase));
        return "casepage";
    }

    public Map<String,Object> openCase(String nameCase,HttpServletRequest request){
        Case caseByName = caseRepository.getCaseByName(nameCase);
        Map<String,Object> map = new HashMap<>();
        List<Weapon> weapons = getWeaponToOpenCase(caseByName);
        Weapon winWeapon = getWinItem(weapons);
        map.put("status",returnStatus(request,nameCase));
        map.put("item",winWeapon);
        updateUserInventory(request,winWeapon);
        return map;
    }
    private void updateUserInventory(HttpServletRequest request, Weapon winItem){
        User user = (User) request.getSession().getAttribute("user");
        user.getInventory().add(winItem.getId());
        userRepository.save(user);
    }

    private String returnStatus(HttpServletRequest request, String nameCase){
        Case aCase = caseRepository.getCaseByName(nameCase);
        String status = "success";
        User user = (User) request.getSession().getAttribute("user");
        if(aCase.getPrice()>user.getBalance()){
            status = "notmoney";
        }else {
            int userBalance = user.getBalance() - aCase.getPrice();
            user.setBalance(userBalance);
            userRepository.save(user);
        }
        return status;
    }

    private Weapon getWinItem(List<Weapon> weapons){
        int countItems = weapons.size();
        int winCount = new Random().nextInt(0,countItems);
        Weapon weapon = weapons.get(winCount);
        return weapon;
    }

    private List<Weapon> getWeaponToOpenCase(Case acase) {
        List<Integer> integers = acase.getWeapons();
        List<Weapon> weapons = new ArrayList<>();
        for (Integer integer : integers) {
            weapons.add(weaponRepository.getWeaponById(integer));
        }
        return weapons;
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