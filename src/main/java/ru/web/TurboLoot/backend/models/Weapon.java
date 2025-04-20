package ru.web.TurboLoot.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "weapons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weapon_id")
    private Integer id;

    @Column(name = "name_weapon", nullable = false)
    private String nameWeapon;

    //@JsonProperty("Rarity")
    @Column(name = "rarity", nullable = false)
    private String rarity;

    //@JsonProperty("chance")
    @Column(name = "chance",nullable = false)
    private float chance;

    @Column(name = "price", nullable = false)
    private Integer price;

}
