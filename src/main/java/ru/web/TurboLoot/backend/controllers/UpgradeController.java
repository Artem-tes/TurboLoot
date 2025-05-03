package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.web.TurboLoot.backend.models.dto.UpTransactionDTO;
import ru.web.TurboLoot.backend.models.dto.UpgradeItemDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;
import ru.web.TurboLoot.backend.services.interfaceservices.UpgradeService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UpgradeController {


    private final UpgradeService upgradeService;

    @GetMapping("/api/v1/up-transactions")
    public ResponseEntity<List<UpTransactionDTO>> upTransactions(HttpServletRequest request){
        List<UpTransactionDTO> response = upgradeService.upTransactions(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("upgrade/operation/roll-upgrade")
    public ResponseEntity<Map<String, Object>> rollItem(@Valid @RequestBody UpgradeItemDTO upgradeItemDTO, HttpServletRequest request){
        Map<String,Object> response = upgradeService.rollWheel(upgradeItemDTO,request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("upgrade/operation/up-inventory")
    public ResponseEntity<List<WeaponDTO>> getUpInventory(@Valid @RequestBody UpgradeItemDTO upgradeItemDTO, HttpServletRequest request){
        List<WeaponDTO> weaponDTOS = upgradeService.upInventory(upgradeItemDTO,request);
        return ResponseEntity.ok(weaponDTOS);
    }

}
