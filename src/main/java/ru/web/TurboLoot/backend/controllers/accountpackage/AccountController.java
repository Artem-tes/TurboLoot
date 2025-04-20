package ru.web.TurboLoot.backend.controllers.accountpackage;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.web.TurboLoot.backend.services.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    @Qualifier("accountService")
    AccountService accountService;

    @GetMapping("/profile")
    public String getProfilePage(){
        return "accountpages/profile";
    }

    @GetMapping("/put-money")
    public String getPutMoneyPage(){
        return "accountpages/putmoney";
    }


}
