package ru.web.TurboLoot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.web.TurboLoot.backend.models.Weapon;

import java.math.BigInteger;
import java.util.List;

public interface WeaponRepository extends JpaRepository<Weapon,Integer> {

    @Query(value = "select * from weapons where price BETWEEN :price AND :final_price  ",nativeQuery = true)
    List<Weapon> getWeaponsMore(@Param("price") BigInteger price,@Param("final_price") BigInteger finalPrice);

    @Query(value = "select * from weapons where weapon_id = :id_weapon", nativeQuery = true)
    Weapon getWeaponById(@Param("id_weapon") Integer id_weapon);

    @Query(value = "select * from weapons where name_weapon = :nameWeapon and price = :price", nativeQuery = true)
    Weapon findByNameWeapon(@Param(value = "nameWeapon") String nameWeapon, @Param("price") Integer price);

}
