package com.devora.weather.repository;

import com.devora.weather.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByName(String name);

    @Query("SELECT  l FROM Location l JOIN FETCH l.user WHERE l.user.login = :login")
    List<Location> findAllByLogin(String login);

    @Transactional
    @Modifying
    @Query("DELETE FROM Location l WHERE l.name = :name AND l.latitude = :lat AND l.longitude = :lon")
    void deleteByName(@Param("name") String name, @Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon);
}
