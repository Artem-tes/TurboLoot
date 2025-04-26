package ru.web.TurboLoot.backend.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    @JsonProperty("balance")
    private Integer balance;
    @JsonProperty("allPrice")
    private Integer allPrice;
    @JsonProperty("bestWeapon")
    private String bestWeapon;
    @JsonProperty("countLegendary")
    private Integer countLegendary;
    @JsonProperty("commonCount")
    private Integer countCommon;
}
