package ru.web.TurboLoot.backend.services.interfaceservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AccountService {

    Map<String,Object> soldAllItems(HttpServletRequest request);

    Map<String,Object> getTransactionsToPage(HttpServletRequest request);

    Map<String,Object> sellItemOnInventory( Map<String,Object> data, HttpServletRequest request);


}
