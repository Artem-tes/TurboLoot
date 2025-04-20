package ru.web.TurboLoot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.web.TurboLoot.backend.models.Case;
import ru.web.TurboLoot.backend.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select * from user_data where email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);


}
