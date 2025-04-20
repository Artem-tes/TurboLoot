package ru.web.TurboLoot.backend.controllers.accountpackage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.services.AccountService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account/get-data")
public class GetDataAccountController {
    @Autowired
    AccountService accountService;

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
