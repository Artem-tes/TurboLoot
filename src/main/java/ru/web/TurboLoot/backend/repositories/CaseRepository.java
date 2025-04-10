package ru.web.TurboLoot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.web.TurboLoot.backend.models.Case;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case,Integer> {

    @Query(value = "select * from case_table",nativeQuery = true)
    List<Case> getAllCases();

    @Query(value = "select * from case_table where name_case = :name", nativeQuery = true)
    Case getCaseByName(@Param("name") String name);
}
