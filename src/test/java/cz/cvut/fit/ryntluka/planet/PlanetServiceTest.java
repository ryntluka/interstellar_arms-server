package cz.cvut.fit.ryntluka.planet;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import cz.cvut.fit.ryntluka.repository.PlanetRepository;
import cz.cvut.fit.ryntluka.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.planet.PlanetObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PlanetServiceTest {
    private final PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;

    @Autowired
    public PlanetServiceTest(PlanetService planetService) {
        this.planetService = planetService;
    }

    /*================================================================================================================*/

    @Test
    void create() throws EntityMissingException {
        given(planetRepository.save(planet1)).willReturn(planet1);
        assertEquals(dto(planet1), planetService.create(createDTO(planet1)));
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));
        assertEquals(Optional.of(planet1), planetService.findById(planet1.getId()));
        verify(planetRepository, Mockito.atLeastOnce()).findById(planet1.getId());
    }

    @Test
    void findByName() throws EntityMissingException {
        given(planetRepository.findByName(planet1.getName())).willReturn(Optional.of(planet1));
        PlanetDTO planetDTO = dto(planet1);
        PlanetDTO res = planetService.findByName(planet1.getName()).orElseThrow(EntityMissingException::new);
        assertEquals(planetDTO, res);
        verify(planetRepository, Mockito.atLeastOnce()).findByName(planet1.getName());
    }

    @Test
    void findByIdAsDTO() throws EntityMissingException {
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));
        PlanetDTO res = planetService.findByIdAsDTO(planet1.getId()).orElseThrow(EntityMissingException::new);
        assertEquals(res, dto(planet1));
        verify(planetRepository, Mockito.atLeastOnce()).findById(planet1.getId());
    }

    @Test
    void findAll() throws EntityMissingException {
        List<Planet> expectedList = new ArrayList<>() {{
            add(planet1);
            add(planet2);
            add(planet3);
            add(planet4);
        }};
        given(planetRepository.findAll()).willReturn(expectedList);
        List<PlanetDTO> answer = planetService.findAll();

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(dto(expectedList.get(i)), answer.get(i));

        verify(planetRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Planet> expectedList = new ArrayList<>() {{
            add(planet1);
            add(planet2);
        }};
        List<Integer> ids = new ArrayList<>() {{
            add(planet1.getId());
            add(planet2.getId());
        }};
        given(planetRepository.findAllById(ids)).willReturn(expectedList);
        List<Planet> answer = planetService.findByIds(ids);

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(expectedList.get(i), answer.get(i));

        verify(planetRepository, Mockito.atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        PlanetCreateDTO newData = createDTO(planet2);
        given(planetRepository.findById(planet1.getId())).willReturn(Optional.of(planet1));
        PlanetDTO updated = planetService.update(planet1.getId(), newData);

        assertEquals(updated.getName(), newData.getName());
        assertEquals(updated.getCoordinate(), newData.getCoordinate());
        assertEquals(updated.getTerritory(), newData.getTerritory());
        assertEquals(updated.getNativeRace(), newData.getNativeRace());
        assertEquals(updated.getInhabitantsIds(), newData.getInhabitantsIds());
        assertEquals(updated.getId(), planet1.getId());

        verify(planetRepository, Mockito.atLeastOnce()).findById(planet1.getId());
    }

    /*================================================================================================================*/

    @Test
    void delete() {
        planetRepository.deleteById(planet1.getId());
        planetService.delete(planet1.getId());
        assertEquals(Optional.empty(), planetService.findById(planet1.getId()));
        verify(planetRepository, Mockito.atLeastOnce()).deleteById(planet1.getId());

    }

    /*================================================================================================================*/

    private PlanetCreateDTO createDTO(Planet planet) {
        return new PlanetCreateDTO(
                planet.getName(),
                planet.getCoordinate(),
                planet.getTerritory(),
                planet.getNativeRace(),
                planet.getInhabitants().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()));
    }

    private PlanetDTO dto(Planet planet) {
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
}
