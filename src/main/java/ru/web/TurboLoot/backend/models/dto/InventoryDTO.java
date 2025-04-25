package ru.web.TurboLoot.backend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryDTO {
    private Integer balance;
    private Integer allPrice;
    private String bestWeapon;
    private Integer countLegendary;
    private Integer countCommon;
}
