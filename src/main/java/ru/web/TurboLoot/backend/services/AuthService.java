package ru.web.TurboLoot.backend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;


    public Map<String,Object> authTry(Map<String,Object> data, HttpServletRequest request){
        Map<String,Object> response = new HashMap<>();
        User user = userRepository.getUserByEmail(String.valueOf(data.get("email")));
        String password = String.valueOf(data.get("password"));
        if(user!=null) {
            if (user.getPassword().equals(password)) {
                response.put("message", "success");
                response.put("user", (User) authService(data, request).get("user"));
            } else {
                response.put("message", "notsuccess");
            }
        }else {
            response.put("message","nulluser");
        }
        return response;
        }

    public Map<String,Object> authService(Map<String, Object> data, HttpServletRequest request){
        String email = (String) data.get("email");
        String password = (String) data.get("password");
        User user = userRepository.getUserByEmail(email);
        Map<String,Object> dataToFrontend = new HashMap<>();
        if(user.getPassword().equals(password)){
            dataToFrontend.put("isLogin",true);
            request.getSession().setAttribute("user",user);
        }else {
            dataToFrontend.put("isLogin",false);
        }
        dataToFrontend.put("user",user);
        return dataToFrontend;
    }

    public boolean getRegInfo(Map<String,Object> data){
        User user = new User(null,(String)data.get("username"),(String) data.get("email"),(String) data.get("password"),0,new ArrayList<Integer>(),0,0,new ArrayList<>());
        for (User user1 : userRepository.findAll()) {
            if(user1.getEmail().equals(user.getEmail())){
                return false;
            }
        }
        userRepository.save(user);
        return true;

    }


}
