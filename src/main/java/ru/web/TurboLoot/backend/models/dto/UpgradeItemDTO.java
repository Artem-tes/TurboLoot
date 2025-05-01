package ru.web.TurboLoot.backend.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpgradeItemDTO {

    @Min(0)
    @Max(100)
    @NotNull
    @Positive
    @JsonProperty("chance")
    private Integer chance;

    @NotNull
    @JsonProperty("price")
    private Integer priceWeapon;

    @JsonProperty("upgradePrice")
    private String upgradePrice;

    @JsonProperty("upgradeWeaponName")
    private String upgradeWeapon;

    @NotNull
    @JsonProperty("weaponName")
    private String nameWeapon;



}
