package ru.web.TurboLoot.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpgradeItemDTO {

    private Integer chance;
    private String nameWeapon;
    private String priceWeapon;
}
