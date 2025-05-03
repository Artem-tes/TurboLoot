package ru.web.TurboLoot.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.web.TurboLoot.backend.models.Upgrade;

public interface UpgradeRepository  extends JpaRepository<Upgrade,Integer> {}
