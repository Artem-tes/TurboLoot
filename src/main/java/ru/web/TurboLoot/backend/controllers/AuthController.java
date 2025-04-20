package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.services.AuthService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @GetMapping("/login")
    public String getLoginPage(){
        return "authpages/loginPage";

    }

    @GetMapping("/registration")
    public String getRegistrationPage(){
        return "authpages/registrationPage";
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String,String>> postRegistration(@RequestBody Map<String,Object> data){
        Map<String,String> response = new HashMap<>();
        boolean responseService = authService.getRegInfo(data);
        if((boolean)responseService){
            response.put("message","success");
        }else {
            response.put("message","notsuccess");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> postLogin(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        Map<String, Object> response = authService.authTry(data,request);
        return ResponseEntity.ok(response);
    }
}
