package ru.web.TurboLoot.backend.models;

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

    @Column(name = "rarity", nullable = false)
    private String rarity;

    @Column(name = "shance",nullable = false)
    private Integer chance;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "image")
    private byte[] image;
}
