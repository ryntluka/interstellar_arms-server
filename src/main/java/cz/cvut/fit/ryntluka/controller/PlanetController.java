package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping
    public List<PlanetDTO> readAll() {
        return planetService.findAll();
    }

    @GetMapping("/{id}")
    public PlanetDTO readById(@PathVariable int id) {
        return planetService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = {"name"})
    public PlanetDTO readByName(@RequestParam String name) {
        return planetService.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public PlanetDTO create(@RequestBody PlanetCreateDTO planet) throws Exception {
        return planetService.create(planet);
    }

    @PutMapping("/{id}")
    public PlanetDTO update(@PathVariable int id, @RequestBody PlanetCreateDTO planet) throws Exception {
        return planetService.update(id, planet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        planetService.delete(id);
    }
}

