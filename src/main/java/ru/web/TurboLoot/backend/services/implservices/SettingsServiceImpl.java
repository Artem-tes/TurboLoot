package ru.web.TurboLoot.backend.services.implservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.web.TurboLoot.backend.models.User;
import ru.web.TurboLoot.backend.models.dto.UserDTO;
import ru.web.TurboLoot.backend.repositories.UserRepository;
import ru.web.TurboLoot.backend.services.interfaceservices.SettingsService;

import java.util.HashMap;
import java.util.Map;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    UserRepository userRepository;

    /// /// реализация получения данных о пользователе на страничку настроек
    @Primary
    @Override
    public Map<String, Object> sendUserDataToSettingsPage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> response = new HashMap<>();
        response.put("user",formUserDTO(user));
        response.put("country","Россия");
        return response;
    }

    @Primary
    @Override
    public Map<String, Object> updateDataUserSettings(Map<String, Object> data, HttpServletRequest request) {
         User user = (User) request.getSession().getAttribute("user");
         Map<String,Object> response = new HashMap<>();
         Map<String,Object> map = updateUserOperation(data,user);
         response.put("user",formUserDTO((User) map.get("user")));
         response.put("status",map.get("status"));
         return response;
    }

    /// //////////////////// /////////////////////// //////////////////////////// ///// //

    private Map<String,Object> updateUserOperation(Map<String,Object> data,User user){
        String username = String.valueOf(data.get("username"));
        String email = String.valueOf(data.get("email"));
        String country = String.valueOf(data.get("country"));
        Map<String,Object> responseTOMethod = new HashMap<>();
        User newUser = null;
        if(!username.equals(user.getUsername()) || !email.equals(user.getEmail()) || !country.equals("Россия")){
            user.setUsername(username);
            user.setEmail(email);
            newUser = userRepository.save(user);
            responseTOMethod.put("user",newUser);
            responseTOMethod.put("status","success");
        } else {
            responseTOMethod.put("status","error");
        }
        return responseTOMethod;
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

}
