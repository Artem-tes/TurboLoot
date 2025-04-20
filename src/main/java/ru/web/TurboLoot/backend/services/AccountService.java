package ru.web.TurboLoot.backend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.models.dto.UserDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("weaponRepository")
    WeaponRepository weaponRepository;

    public Map<String,Object> getInventoryToGetDataController(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> response = new HashMap<>();
        List<WeaponDTO> weapons = getListWeaponDTO(user);
        response.put("items",weapons);
        return response;
    }

    public Map<String,Object> getProfileData(HttpServletRequest request){
        Map<String,Object> response = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        response.put("user",formUserDTO(user));
        return response;
    }

    private UserDTO formUserDTO(User user){
        UserDTO userDTO = new UserDTO(
                user.getEmail(),
                user.getUsername(),
                user.getBalance(),
                user.getInventory()
                ,user.getCountCases()
                ,user.getCountInventory(),
                user.getTransactional());
        return userDTO;
    }

    private List<WeaponDTO> getListWeaponDTO(User user){
        List<Integer> idItems = user.getInventory();
        List<WeaponDTO> weapons = new ArrayList<>();
        for (Integer idItem : idItems) {
            WeaponDTO weaponDTO = new WeaponDTO(String.valueOf(weaponRepository.getWeaponById(idItem).getNameWeapon()),
                    String.valueOf(weaponRepository.getWeaponById(idItem).getRarity()
                    ), weaponRepository.getWeaponById(idItem).getPrice());
            weapons.add(weaponDTO);
        }
        return weapons;
    }

    public Map<String,Object> putMoneyToBalance(Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null) {
            String password = String.valueOf(data.get("password"));
            if (user.getPassword().equals(password)) {
                balanceUserUpdate(user, Integer.parseInt((String) data.get("amount")));
                response.put("status", "success");
            } else {
                response.put("status", "incorrectpass");
            }
        }else {
            response.put("status","nulluser");
        }
        return response;
    }

    private void balanceUserUpdate(User user, Integer amount){
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
    }

}
