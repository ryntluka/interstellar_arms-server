package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTOAssembler;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/planets", produces = "application/vnd-planets+json", consumes = "application/vnd-planets+json")
public class PlanetController {

    private final PlanetService planetService;
    private final PlanetDTOAssembler planetDTOAssembler;

    @Autowired
    public PlanetController(PlanetService planetService, PlanetDTOAssembler planetDTOAssembler) {
        this.planetService = planetService;
        this.planetDTOAssembler = planetDTOAssembler;
    }

    @GetMapping
    public List<PlanetDTO> findAll() {
        return planetService.findAll().stream().map(planetDTOAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlanetDTO findById(@PathVariable int id) {
        return planetDTOAssembler.toModel(
                planetService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }

    @GetMapping(params = {"name"})
    public PlanetDTO findByName(@RequestParam String name) {
        return planetDTOAssembler.toModel(
                planetService.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }

    @PostMapping
    public ResponseEntity<PlanetDTO> create(@RequestBody PlanetCreateDTO planet) {
        PlanetDTO inserted;
        Planet planet1 = new Planet("Coruscant",
                new Point(2341, 563), "Core Worlds", "Human",
                new ArrayList<>()
        );
        try{
            inserted = planetDTOAssembler.toModel(
                    planetService.create(planet)
            );
        }
        catch (EntityMissingException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .created(Link.of("http://localhost:8080/api/planets/" + inserted.getId()).toUri())
                .body(inserted);

    }

    @PutMapping("/{id}")
    public PlanetDTO update(@PathVariable int id, @RequestBody PlanetCreateDTO planet) {
        try {
            return planetDTOAssembler.toModel(
                    planetService.update(id, planet)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        planetService.delete(id);
    }

    @PutMapping(value = {"/{id}"}, params = {"customerId"})
    public PlanetDTO addResidence(@PathVariable int id, @RequestParam int customerId) {
        try {
            return planetDTOAssembler.toModel(
                    planetService.customerAddResidence(customerId, id)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

