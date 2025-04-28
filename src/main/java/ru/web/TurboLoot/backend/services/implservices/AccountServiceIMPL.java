package ru.web.TurboLoot.backend.services.implservices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.UserTransaction;
import ru.web.TurboLoot.backend.models.Weapon;
import ru.web.TurboLoot.backend.models.dto.InventoryDTO;
import ru.web.TurboLoot.backend.models.dto.TransactionDTO;
import ru.web.TurboLoot.backend.models.dto.UserDTO;
import ru.web.TurboLoot.backend.repositories.TransactionRepository;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.repositories.WeaponRepository;
import ru.web.TurboLoot.backend.services.interfaceservices.AccountService;

import java.util.*;

@Slf4j
@Service
public class AccountServiceIMPL implements AccountService {

    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;

    @Autowired
    @Qualifier("weaponRepository")
    WeaponRepository weaponRepository;

    @Autowired
    @Qualifier("transactionRepository")
    TransactionRepository transactionRepository;


    /// /// реализация продажи всех предметов
    @Primary
    @Override
    public Map<String, Object> soldAllItems(HttpServletRequest request) {
        Map<String,Object> response = new HashMap<>();
        User user = (User)request.getSession().getAttribute("user");
        response.put("totalEarned",formAllEarned(user));
        sellAllItemOperation(user);
        response.put("status","success");
        response.put("newBalance",user.getBalance());
        return response;
    }

    /// /// реализация получения транзакций
    @Primary
    @Override
    public Map<String, Object> getTransactionsToPage(HttpServletRequest request) {
        Map<String,Object> response = new HashMap<>();
        User user =   (User) request.getSession().getAttribute("user");
        response.put("transactions",transListToJson(user,response));
        response.put("user", formUserDTO(user));
        return response;
    }

    /// /// реализация продажи предмета
    @Primary
    @Override
    public Map<String, Object> sellItemOnInventory(Map<String, Object> data, HttpServletRequest request) {
        Map<String,Object> response = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        operationSellItem(String.valueOf(data.get("nameWeapon")),user, String.valueOf(data.get("priceWeapon")));
        response.put("status","success");
        response.put("userdata",formInventoryDTO(user));
        return response;
    }


    /// ///методы помощники
    private void sellAllItemOperation(User user){
        List<Integer> idItems = user.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        List<Integer> idTransactions = user.getTransactional();
        Integer totalBalance = 0;
        for (Integer idItem : idItems) {
            weapons.add(weaponRepository.getWeaponById(idItem));
        }
        for (Weapon weapon : weapons) {
            totalBalance+=weapon.getPrice();
            UserTransaction userTransaction = new UserTransaction(
                    null,
                    1,
                    weapon.getPrice(),
                    String.valueOf(new Date()),
                    user.getUsername()
            );
            UserTransaction NewuserTransaction = transactionRepository.save(userTransaction);
            idTransactions.add(NewuserTransaction.getId());
        }
        idItems.clear();
        user.setInventory(idItems);
        user.setTransactional(idTransactions);
        user.setBalance(user.getBalance()+totalBalance);
        userRepository.save(user);
    }

    private Integer formAllEarned(User user){
        List<Integer> idItems = user.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        Integer totalEarned = 0;
        for (Integer idItem : idItems) {
            weapons.add(weaponRepository.getWeaponById(idItem));
        }
        for (Weapon weapon : weapons) {
            totalEarned+=weapon.getPrice();
        }
        return totalEarned;
    }

    private InventoryDTO formInventoryDTO(User user){
        InventoryDTO inventoryDTO = new InventoryDTO();
        Map<String,Object> map = operateDataCount(user);
        inventoryDTO.setBalance(user.getBalance());
        inventoryDTO.setAllPrice((Integer) map.get("allPrice"));
        inventoryDTO.setBestWeapon(String.valueOf(map.get("bestWeapon")));
        inventoryDTO.setCountCommon((Integer) map.get("common"));
        inventoryDTO.setCountLegendary((Integer) map.get("lrg"));
        return inventoryDTO;
    }

    private Map<String,Object> operateDataCount(User user){
        List<Integer> idItems = user.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        Weapon bestWeapon = new Weapon();
        bestWeapon.setPrice(0);
        Integer allPrice = 0;
        Integer legCount = 0;
        Integer commonCount = 0;
        for (Integer i : idItems) {
            weapons.add(weaponRepository.getWeaponById(i));
        }
        for (Weapon weapon : weapons) {
            allPrice+=weapon.getPrice();
            if(weapon.getPrice()>bestWeapon.getPrice()){
                bestWeapon = weapon;
            }
            if(weapon.getRarity().equals("Legendary")){
                legCount+=1;
            }
            if(weapon.getRarity().equals("Common")){
                commonCount+=1;
            }
        }
        map.put("allPrice",allPrice);
        map.put("leg",legCount);
        map.put("bestWeapon",bestWeapon);
        map.put("common",commonCount);
        return map;
    }

    private UserDTO formUserDTO(User user){
        return new UserDTO(user.getEmail(),
                user.getUsername(),
                user.getBalance(),
                user.getInventory(),
                user.getCountCases(),
                user.getCountInventory(),
                user.getTransactional());
    }

    private void operationSellItem(String nameWeapon, User user, String weaponPrice){
        Weapon weapon = weaponRepository.findByNameWeapon(nameWeapon,Integer.parseInt(weaponPrice));
        List<Integer> idItems = user.getInventory();
        if(idItems.contains(weapon.getId())){
            idItems.remove(weapon.getId());
        }
        UserTransaction transaction = new UserTransaction(
                null,1,weapon.getPrice(),String.valueOf(new Date()),user.getUsername());
        transactionRepository.save(transaction);
        UserTransaction userTransaction = transactionRepository.findAll().get(transactionRepository.findAll().size()-1);
        List<Integer> idTr = user.getTransactional();
        idTr.add(userTransaction.getId());
        user.setTransactional(idTr);
        user.setBalance(user.getBalance()+weapon.getPrice());
        user.setInventory(idItems);
        User user1 = userRepository.save(user);
    }


    private List<TransactionDTO> transListToJson(User user, Map<String,Object> response){
        List<Integer> idTransactions = user.getTransactional();
        List<UserTransaction> userTransactions = new ArrayList<>();
        List<TransactionDTO> transactionDTOS = List.of();
        for (Integer idTransaction : idTransactions) {
             userTransactions.add(transactionRepository.getTransactionById(idTransaction));
        }
        if(userTransactions.isEmpty()){
            response.put("status","nottransactions");
        }else {
             transactionDTOS = formTransToDTO(userTransactions);
        }
        response.put("status","success");
        Collections.reverse(transactionDTOS);
        return transactionDTOS;
    }

    private List<TransactionDTO> formTransToDTO(List<UserTransaction> userTransactions){
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (UserTransaction transaction : userTransactions) {
            TransactionDTO transactionDTO =
                    new TransactionDTO(transaction.getTypeTransaction(),transaction.getAmount(),transaction.getDate(),transaction.getOwner());
            transactionDTOS.add(transactionDTO);
        }
        return transactionDTOS;
    }



}
