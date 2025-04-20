package ru.web.TurboLoot.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WeaponDTO {
    private String nameWeapon;
    private String rarity;
    private Integer price;
}
