package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
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
    public PlanetDTO create(PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
        List<Customer> inhabitants = customerService.findByIds(planetCreateDTO.getInhabitantsIds());
        if (inhabitants.size() != planetCreateDTO.getInhabitantsIds().size())
            throw new EntityMissingException();

        return toDTO(
                planetRepository.save(
                        new Planet(
                                planetCreateDTO.getName(),
                                planetCreateDTO.getCoordinate(),
                                planetCreateDTO.getTerritory(),
                                planetCreateDTO.getNativeRace(),
                                inhabitants)
                )
        );
    }

    /*================================================================================================================*/

    public List<PlanetDTO> findAll() {
        return planetRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Planet> findByIds(List<Integer> ids) {
        return planetRepository.findAllById(ids);
    }

    public Optional<Planet> findById(int id) {
        return planetRepository.findById(id);
    }

    public Optional<PlanetDTO> findByIdAsDTO(int id) {
        return toDTO(planetRepository.findById(id));
    }

    public Optional<PlanetDTO> findByName (String name) {
        return toDTO(planetRepository.findByName(name));
    }

    /*================================================================================================================*/

    @Transactional
    public PlanetDTO update(int id, PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
        Optional<Planet> optionalPlanet = planetRepository.findById(id);
        if (optionalPlanet.isEmpty())
            throw new EntityMissingException(id);


        List<Customer> inhabitants = customerService.findByIds(planetCreateDTO.getInhabitantsIds());
        if (inhabitants.size() != planetCreateDTO.getInhabitantsIds().size())
            throw new EntityMissingException();

        Planet planet = optionalPlanet.get();
        planet.setName(planetCreateDTO.getName());
        planet.setCoordinate(planetCreateDTO.getCoordinate());
        planet.setTerritory(planetCreateDTO.getTerritory());
        planet.setNativeRace(planetCreateDTO.getNativeRace());
        planet.setInhabitants(inhabitants);
        return toDTO(planet);
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) {
        planetRepository.deleteById(id);
    }

    /*================================================================================================================*/

    private PlanetDTO toDTO(Planet planet) {
        return new PlanetDTO(
                planet.getId(),
                planet.getName(),
                planet.getCoordinate(),
                planet.getTerritory(),
                planet.getNativeRace(),
                planet.getInhabitants().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()));
    }

    private Optional<PlanetDTO> toDTO(Optional<Planet> planet) {
        if (planet.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(planet.get()));
    }
}
