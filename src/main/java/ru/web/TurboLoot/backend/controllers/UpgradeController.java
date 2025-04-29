package ru.web.TurboLoot.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.web.TurboLoot.backend.models.dto.UpgradeItemDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;
import ru.web.TurboLoot.backend.services.interfaceservices.UpgradeService;

import java.util.List;
import java.util.Map;

@RestController
public class UpgradeController {

    @Autowired
    UpgradeService upgradeService;

    @GetMapping("/operation/up-inventory")
    public ResponseEntity<List<WeaponDTO>> getUpInventory(@RequestBody UpgradeItemDTO upgradeItemDTO, HttpServletRequest request){
        List<WeaponDTO> weaponDTOS = upgradeService.upInventory(upgradeItemDTO,request);
        return ResponseEntity.ok(weaponDTOS);
    }

}
