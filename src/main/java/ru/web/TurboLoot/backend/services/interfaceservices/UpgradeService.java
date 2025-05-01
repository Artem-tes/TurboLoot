package ru.web.TurboLoot.backend.services.interfaceservices;

import jakarta.servlet.http.HttpServletRequest;
import ru.web.TurboLoot.backend.models.dto.UpgradeItemDTO;
import ru.web.TurboLoot.backend.models.dto.WeaponDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface UpgradeService {
    Map<String, Object> rollWheel(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request);
    List<WeaponDTO> upInventory(UpgradeItemDTO upgradeItemDTO, HttpServletRequest request);
}
