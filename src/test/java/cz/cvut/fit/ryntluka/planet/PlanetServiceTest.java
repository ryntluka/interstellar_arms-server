package cz.cvut.fit.ryntluka.planet;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.exceptions.EntityContainsElementsException;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import cz.cvut.fit.ryntluka.repository.PlanetRepository;
import cz.cvut.fit.ryntluka.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.planet.PlanetObjects.all;
import static cz.cvut.fit.ryntluka.customer.CustomerObjects.customer1;
import static cz.cvut.fit.ryntluka.planet.PlanetObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PlanetServiceTest {
    private final PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    public PlanetServiceTest(PlanetService planetService) {
        this.planetService = planetService;
    }

    /*================================================================================================================*/

    @Test
    void create() throws EntityMissingException {
        given(planetRepository.save(planet1)).willReturn(planet1);
        assertEquals(planet1, planetService.create(createDTO(planet1)));
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));
        assertEquals(Optional.of(planet1), planetService.findById(planet1.getId()));
        verify(planetRepository, atLeastOnce()).findById(planet1.getId());
    }

    @Test
    void findByName() {
        given(planetRepository.findAllByName(planet1.getName())).willReturn(List.of(planet1));
        List<Planet> res = planetService.findAllByName(planet1.getName());
        assertEquals(planet1, res.get(0));
        verify(planetRepository, atLeastOnce()).findAllByName(planet1.getName());
    }

    @Test
    void findAll() throws EntityMissingException {
        given(planetRepository.findAll()).willReturn(all);
        List<Planet> answer = planetService.findAll();

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(planetRepository, atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Integer> ids = all.stream().map(Planet::getId).collect(Collectors.toList());
        given(planetRepository.findAllById(ids)).willReturn(all);
        List<Planet> answer = planetService.findByIds(ids);

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(planetRepository, atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        PlanetCreateDTO newData = createDTO(planet2);
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));
        Planet updated = planetService.update(planet1.getId(), newData);

        assertEquals(updated.getName(), newData.getName());
        assertEquals(updated.getCoordinate(), newData.getCoordinate());
        assertEquals(updated.getTerritory(), newData.getTerritory());
        assertEquals(updated.getNativeRace(), newData.getNativeRace());
        assertEquals(updated.getId(), planet1.getId());

        verify(planetRepository, atLeastOnce()).findById(planet1.getId());
    }

    /*================================================================================================================*/

    @Test
    void delete() throws EntityMissingException, EntityContainsElementsException {
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));

        planetService.delete(planet1.getId());

        verify(planetRepository, atLeastOnce()).findById(planet1.getId());
        verify(planetRepository, atLeastOnce()).deleteById(planet1.getId());
    }
}
