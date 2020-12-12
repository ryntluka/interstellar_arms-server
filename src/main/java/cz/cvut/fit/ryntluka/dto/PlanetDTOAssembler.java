package cz.cvut.fit.ryntluka.dto;

import cz.cvut.fit.ryntluka.controller.PlanetController;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlanetDTOAssembler extends RepresentationModelAssemblerSupport<Planet, PlanetDTO> {

    public PlanetDTOAssembler() {
        super(PlanetController.class, PlanetDTO.class);
    }

    @Override
    public PlanetDTO toModel(Planet entity) {
        PlanetDTO res = new PlanetDTO(entity.getId(),
                entity.getName(),
                entity.getCoordinate(),
                entity.getTerritory(),
                entity.getNativeRace());
        res.add(
                linkTo(
                        methodOn(PlanetController.class).findById(res.getId())
                ).withSelfRel()
        );
        res.add(
                linkTo(
                        methodOn(PlanetController.class).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
        return res;
    }
}
