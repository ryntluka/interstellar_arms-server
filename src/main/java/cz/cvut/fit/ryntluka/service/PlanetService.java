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
    public Planet create(PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
        List<Customer> inhabitants = customerService.findByIds(planetCreateDTO.getInhabitantsIds());
        if (inhabitants.size() != planetCreateDTO.getInhabitantsIds().size())
            throw new EntityMissingException();

        return planetRepository.save(
                new Planet(planetCreateDTO.getName(),
                        planetCreateDTO.getCoordinate(),
                        planetCreateDTO.getTerritory(),
                        planetCreateDTO.getNativeRace(),
                        inhabitants)
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

    public Optional<Planet> findByName (String name) {
        return planetRepository.findByName(name);
    }

    /*================================================================================================================*/

    @Transactional
    public Planet update(int id, PlanetCreateDTO planetCreateDTO) throws EntityMissingException {
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
        return planet;
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) {
        planetRepository.deleteById(id);
    }

    /*================================================================================================================*/

    @Transactional
    public Planet customerAddResidence(int customerId, int planetId) throws EntityMissingException {
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if(optionalCustomer.isEmpty())
            throw new EntityMissingException(customerId);

        Optional<Planet> optionalPlanet = planetRepository.findById(planetId);
        if(optionalPlanet.isEmpty())
            throw new EntityMissingException(planetId);

        Planet planet = optionalPlanet.get();
        planet.getInhabitants().add(optionalCustomer.get());
        return planet;
    }
}
