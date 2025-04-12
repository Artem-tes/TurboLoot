package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    private String getLoginPage(){
        return "authpages/loginPage";
    }

    @PostMapping("/login")
    private ResponseEntity<Map<String,Object>> postLogin(@RequestBody Map<String,Object> data){
        System.out.println(data.get("email"));
        System.out.println(data.get("password"));
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("message","complete");
        return ResponseEntity.ok(dataMap);
    }

}
