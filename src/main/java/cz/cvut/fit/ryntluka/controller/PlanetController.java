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
public class PlanetController {
//
//    private final PlanetService planetService;
//
//    @Autowired
//    public PlanetController(PlanetService planetService) {
//        this.planetService = planetService;
//    }

//    @GetMapping("/planet/all")
//    List<PlanetDTO> all() {
//        return planetService.findAll();
//    }
//
//    @GetMapping("/planet/{id}")
//    PlanetDTO byId(@PathVariable int id) {
//        return planetService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping("/planet")
//    PlanetDTO save(@RequestBody PlanetCreateDTO planet) throws Exception {
//        return planetService.create(planet);
//    }
//
//    @PutMapping("/planet/{id}")
//    PlanetDTO save(@PathVariable int id, @RequestBody PlanetCreateDTO planet) throws Exception {
//        return planetService.update(id, planet);
//    }
}
