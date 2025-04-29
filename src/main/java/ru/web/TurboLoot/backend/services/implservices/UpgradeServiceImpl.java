package ru.web.TurboLoot.backend.services.implservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.models.dto.UpgradeItemDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;
import ru.web.TurboLoot.backend.services.interfaceservices.UpgradeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UpgradeServiceImpl implements UpgradeService {

    @Autowired
    WeaponRepository weaponRepository;

    @Primary
    @Override
    public List<WeaponDTO> upInventory(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Weapon> weapons = getWeaponList(user);
        return new ArrayList<>();
    }

    private List<Weapon> getWeaponList(User user){
        List<Integer> idItems = user.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        for (Integer idItem : idItems) {
            weapons.add(weaponRepository.getWeaponById(idItem));
        }
        return weapons;
    }

    private List<Weapon> operateUpItems(UpgradeItemDTO upgradeItemDTO){
        Weapon weapon = weaponRepository.findByNameWeapon(upgradeItemDTO.getNameWeapon(), Integer.valueOf(upgradeItemDTO.getPriceWeapon()));
        return new ArrayList<>();
    }
}
