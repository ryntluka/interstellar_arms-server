package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.exceptions.EntityContainsElementsException;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final CustomerService customerService;

    @Autowired
    public PlanetService(PlanetRepository planetRepository, CustomerService customerService) {
        this.planetRepository = planetRepository;
        this.customerService = customerService;
    }

    /*================================================================================================================*/

    @Transactional
    public Planet create(PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
        return planetRepository.save(
                new Planet(planetCreateDTO.getName(),
                        planetCreateDTO.getCoordinate(),
                        planetCreateDTO.getTerritory(),
                        planetCreateDTO.getNativeRace())
        );
    }

    /*================================================================================================================*/

    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    public List<Planet> findByIds(List<Integer> ids) {
        return planetRepository.findAllById(ids);
    }

    public Optional<Planet> findById(int id) {
        return planetRepository.findById(id);
    }

    public List<Planet> findAllByName (String name) {
        return planetRepository.findAllByName(name);
    }

    /*================================================================================================================*/

    @Transactional
    public Planet update(int id, PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
        Optional<Planet> optionalPlanet = planetRepository.findById(id);
        if (optionalPlanet.isEmpty())
            throw new EntityMissingException(id);

        Planet planet = optionalPlanet.get();
        planet.setName(planetCreateDTO.getName());
        planet.setCoordinate(planetCreateDTO.getCoordinate());
        planet.setTerritory(planetCreateDTO.getTerritory());
        planet.setNativeRace(planetCreateDTO.getNativeRace());
        return planet;
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) throws EntityContainsElementsException, EntityMissingException {
        List<Integer> planetIds = customerService.findAll()
                .stream()
                .map(c -> c.getPlanet().getId())
                .distinct()
                .collect(Collectors.toList());
        if (planetIds.contains(id))
            throw new EntityContainsElementsException();
        if (planetRepository.findById(id).isEmpty())
            throw new EntityMissingException();
        planetRepository.deleteById(id);
    }
}
