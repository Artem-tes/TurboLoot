package ru.web.TurboLoot.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.web.TurboLoot.backend.models.Weapon;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpTransactionDTO {
    private WeaponDTO weapon;
    private WeaponDTO upWeapon;
    private Integer move;
    private Integer user;
}
