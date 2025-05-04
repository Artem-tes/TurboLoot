package ru.web.TurboLoot.backend.services.implservices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.Upgrade;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.models.dto.UpTransactionDTO;
import ru.web.TurboLoot.backend.models.dto.UpgradeItemDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;
import ru.web.TurboLoot.backend.repositories.UpgradeRepository;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;
import ru.web.TurboLoot.backend.services.interfaceservices.UpgradeService;

import java.math.BigInteger;
import java.util.*;

@Primary
@Service
@RequiredArgsConstructor
public class UpgradeServiceImpl implements UpgradeService {


    private final WeaponRepository weaponRepository;

    private final UpgradeRepository upgradeRepository;

    private final UserRepository userRepository;


    @Override
    public List<UpTransactionDTO> upTransactions(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return operateListTransaction(user);
    }

    private List<UpTransactionDTO> operateListTransaction(User user){
        Integer idUser = user.getId();
        List<Upgrade> upgrades = upgradeRepository.findAll();
        List<Upgrade> userUpgrades = new ArrayList<>();
        List<UpTransactionDTO> responseToMethod = new ArrayList<>();
        for (Upgrade upgrade : upgrades) {
            if(upgrade.getIdOwner() == idUser){
                userUpgrades.add(upgrade);
            }
        }
        responseToMethod = formDTOTransactions(userUpgrades);
        return responseToMethod;
    }

    private List<UpTransactionDTO> formDTOTransactions(List<Upgrade> upgrades){
        List<UpTransactionDTO> upTransactionDTOS = new ArrayList<>();
        for (Upgrade upgrade : upgrades) {
            UpTransactionDTO upTransactionDTO = new UpTransactionDTO(
                    formWeaponDTO(weaponRepository.getWeaponById(upgrade.getIdItem())),
                    formWeaponDTO(weaponRepository.getWeaponById(upgrade.getIdItemUpgrade())),
                    upgrade.getMove(),
                    upgrade.getIdOwner()
            );
            upTransactionDTOS.add(upTransactionDTO);
        }
        return upTransactionDTOS;
    }

    private WeaponDTO formWeaponDTO(Weapon weapon){
        WeaponDTO weaponDTO = new WeaponDTO(
                weapon.getNameWeapon(),
                weapon.getRarity(),
                weapon.getPrice()
        );
        return weaponDTO;
    }




    @Override
    public Map<String,Object> rollWheel(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request) {
        Map<String,Object> response = new HashMap<>();
        //возвращает победу/поражение уже со всеми операциями
        if(operateRollItem(upgradeItemDTO,request)){
            response.put("status","success");
        }else {
            response.put("status","lose");
        }
        return response;
    }

    private boolean operateRollItem(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request){
        boolean responseToService = false;
        User user = (User) request.getSession().getAttribute("user");
        Weapon weapon = weaponRepository.findByNameWeapon(upgradeItemDTO.getNameWeapon(), upgradeItemDTO.getPriceWeapon());
        Weapon upgradeWeapon = weaponRepository.findByNameWeapon(upgradeItemDTO.getUpgradeWeapon(), Integer.valueOf(upgradeItemDTO.getUpgradePrice()));
        Integer chance = 100-upgradeItemDTO.getChance();
        //todo сделай вместо random полный алгоритм
        if(rollOperate(chance)){
            operateUpdateInventory(weapon,upgradeWeapon,user);
            responseToService = true;
        }else {
            operateRemove(weapon, user);
        }
        saveHistoryUpgrade(responseToService,weapon,upgradeWeapon,user);
        return responseToService;
    }

    private boolean rollOperate(Integer chance){
        Integer countChance = new Random().nextInt(100);
        boolean isWin = true;
        if(!(countChance <chance)){
            isWin = false;
        }
        return isWin;
    }

    private void operateRemove(Weapon weapon, User user){
        List<Integer> integers = user.getInventory();
        integers.remove(weapon.getId());
        user.setInventory(integers);
        userRepository.save(user);
    }

    private void saveHistoryUpgrade(boolean isWin, Weapon weapon, Weapon upWeapon, User user){
        Integer move = 0;
        if(isWin){
            move = 1;
        }
        upgradeRepository.save(new Upgrade(
                null,
                weapon.getId(),
                upWeapon.getId(),
                move,
                user.getId()
        ));
    }

    private void operateUpdateInventory(Weapon weapon,Weapon upgradeWeapon, User user){
        List<Integer> userInventory = user.getInventory();
        userInventory.remove(weapon.getId());
        userInventory.add(upgradeWeapon.getId());
        user.setInventory(userInventory);
        userRepository.save(user);
    }





    @Override
    public List<WeaponDTO> upInventory(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request) {
        List<Weapon> upWeapons = operateUpItems(upgradeItemDTO);
        List<WeaponDTO> dtos = formWeaponDTO(upWeapons);
        return dtos;
    }

    private List<Weapon> getWeaponList(User user){
        List<Integer> idItems = user.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        for (Integer idItem : idItems) {
            weapons.add(weaponRepository.getWeaponById(idItem));
        }
        return weapons;
    }

    private List<WeaponDTO> formWeaponDTO(List<Weapon> weapons){
        List<WeaponDTO> weaponDTOS = new ArrayList<>();
        for (Weapon weapon : weapons) {
            weaponDTOS.add(new WeaponDTO(
                   weapon.getNameWeapon(),
                   weapon.getRarity(),
                   weapon.getPrice()
            ));
        }
        return weaponDTOS;
    }

    private List<Weapon> operateUpItems(UpgradeItemDTO upgradeItemDTO){
        List<Weapon> upWeapons = new ArrayList<>();
        BigInteger startPrice = BigInteger.valueOf(upgradeItemDTO.getPriceWeapon());
        float chance = (float) upgradeItemDTO.getChance() /100;
        Integer jumpPrice = Integer.valueOf((int) (upgradeItemDTO.getPriceWeapon()*chance)*2);
        BigInteger finalPrice =
                BigInteger.valueOf(upgradeItemDTO.getPriceWeapon()+jumpPrice);
        List<Weapon> weaponsMore =
                weaponRepository.getWeaponsMore(startPrice,finalPrice);
        upWeapons.addAll(weaponsMore);
        return upWeapons;
    }
}
