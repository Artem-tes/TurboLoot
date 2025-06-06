package ru.web.TurboLoot.backend.controllers.accountpackage;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.web.TurboLoot.backend.services.AccountService;
import ru.web.TurboLoot.backend.services.interfaceservices.SettingsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account/get-data")
public class GetDataAccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ru.web.TurboLoot.backend.services.interfaceservices.AccountService accountServiceI;

    @Autowired
    SettingsService settingsService;

    @PutMapping("/update-password")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> updatePassword(@RequestBody Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = settingsService.updateUserPassword(data,request);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/save-profile")
    public ResponseEntity<Map<String,Object>> updateUserData(@RequestBody Map<String,Object> data,HttpServletRequest request){
        Map<String, Object> response = settingsService.updateDataUserSettings(data,request);
        return ResponseEntity.status(200).body(response);

    }

    @GetMapping("/user-settings")
    public ResponseEntity<Map<String,Object>> userSettingsProfile(HttpServletRequest request){
        Map<String,Object> response = settingsService.sendUserDataToSettingsPage(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/sold-all-items")
    public ResponseEntity<Map<String,Object>> soldAllItems(HttpServletRequest request){
        Map<String,Object> response = accountServiceI.soldAllItems(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get-transactions")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getTransactions(HttpServletRequest request){
        Map<String,Object> response = accountServiceI.getTransactionsToPage(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/inventory-sell-item")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> sellItem(@RequestBody Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = accountServiceI.sellItemOnInventory(data,request);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/inventory-data")
    public ResponseEntity<Map<String,Object>> getDataToInventory(HttpServletRequest request){
        Map<String,Object> response = accountService.getDataToInventoryController(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-inventory")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getInventoryToProfilePage(HttpServletRequest request){
        Map<String,Object> response = accountService.getInventoryToGetDataController(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-profile-data")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getDataToProfilePage(HttpServletRequest request){
        Map<String,Object> response = accountService.getProfileData(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("try-put-money")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> tryPutMoney(@RequestBody Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = accountService.putMoneyToBalance(data,request);
        return ResponseEntity.ok(response);
    }

}
