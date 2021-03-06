package cz.cvut.fit.ryntluka.planet;

import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.service.PlanetService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.customer1;
import static cz.cvut.fit.ryntluka.planet.PlanetObjects.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetControllerTest {

    private final static String ROOT_URL = "/api/planets";
    private final static String GET_ONE = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-planets+json";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService planetService;

    @Test
    void findById() throws Exception {
        given(planetService.findById(planet1.getId())).willReturn(Optional.of(planet1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + GET_ONE, planet1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(planet1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(planet1.getName()))).
                andExpect(jsonPath("$.coordinate.x", CoreMatchers.is(planet1.getCoordinate().getX()))).
                andExpect(jsonPath("$.coordinate.y", CoreMatchers.is(planet1.getCoordinate().getY()))).
                andExpect(jsonPath("$.territory", CoreMatchers.is(planet1.getTerritory()))).
                andExpect(jsonPath("$.nativeRace", CoreMatchers.is(planet1.getNativeRace()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + planet1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(planetService, atLeastOnce()).findById(planet1.getId());
    }

    @Test
    void findByName() throws Exception {
        given(planetService.findAllByName(planet1.getName())).willReturn(List.of(planet1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + "?name=" + planet1.getName()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", CoreMatchers.is(planet1.getId()))).
                andExpect(jsonPath("$.[0].name", CoreMatchers.is(planet1.getName()))).
                andExpect(jsonPath("$.[0].coordinate.x", CoreMatchers.is(planet1.getCoordinate().getX()))).
                andExpect(jsonPath("$.[0].coordinate.y", CoreMatchers.is(planet1.getCoordinate().getY()))).
                andExpect(jsonPath("$.[0].territory", CoreMatchers.is(planet1.getTerritory()))).
                andExpect(jsonPath("$.[0].nativeRace", CoreMatchers.is(planet1.getNativeRace()))).
                andExpect(jsonPath("$.[0].links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + planet1.getId()))).
                andExpect(jsonPath("$.[0].links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(planetService, atLeastOnce()).findAllByName(planet1.getName());
    }

    @Test
    void findAll() throws Exception {
        given(planetService.findAll()).willReturn(all);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());

        verify(planetService, atLeastOnce()).findAll();
    }

    @Test
    void create() throws Exception {
        given(planetService.create(createDTO(planet4))).willReturn(planet4);
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post(ROOT_URL).
                        contentType(CONTENT_TYPE).
                        accept(CONTENT_TYPE).
                        content(toCreateJSON(planet4))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id", CoreMatchers.is(planet4.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(planet4.getName()))).
                andExpect(jsonPath("$.coordinate.x", CoreMatchers.is(planet4.getCoordinate().getX()))).
                andExpect(jsonPath("$.coordinate.y", CoreMatchers.is(planet4.getCoordinate().getY()))).
                andExpect(jsonPath("$.territory", CoreMatchers.is(planet4.getTerritory()))).
                andExpect(jsonPath("$.nativeRace", CoreMatchers.is(planet4.getNativeRace()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + planet4.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));
        verify(planetService, atLeastOnce()).create(createDTO(planet4));
    }

    @Test
    void update() throws Exception {

        given(planetService.update(planet1.getId(), createDTO(planet2))).willReturn(planet2);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        put(ROOT_URL + GET_ONE, planet1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE).
                        content(toCreateJSON(planet2))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(planet1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(planet2.getName()))).
                andExpect(jsonPath("$.coordinate.x", CoreMatchers.is(planet2.getCoordinate().getX()))).
                andExpect(jsonPath("$.coordinate.y", CoreMatchers.is(planet2.getCoordinate().getY()))).
                andExpect(jsonPath("$.territory", CoreMatchers.is(planet2.getTerritory()))).
                andExpect(jsonPath("$.nativeRace", CoreMatchers.is(planet2.getNativeRace()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + planet2.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(planetService, atLeastOnce()).update(planet1.getId(), createDTO(planet2));
    }

    @Test
    void delete() throws Exception {
        planetService.delete(planet1.getId());
        mockMvc.perform(
                MockMvcRequestBuilders.
                        delete(ROOT_URL + GET_ONE, planet1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());
        verify(planetService, Mockito.atLeastOnce()).delete(planet1.getId());
    }
}
