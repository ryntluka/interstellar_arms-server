package cz.cvut.fit.ryntluka.repository;

import cz.cvut.fit.ryntluka.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer> {
    List<Planet> findAllByName(String lastName);
}
