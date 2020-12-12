package cz.cvut.fit.ryntluka.planet;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.customer1;

public class PlanetObjects {
    public static Planet planet1 = new Planet("Coruscant",
            new Point(2341, 563), "Core Worlds", "Human"
    );
    public static Planet planet2 = new Planet("Mon Cala",
            new Point(1234, 53), "Outer Rim", "Mon Calamari"
    );
    public static Planet planet3 = new Planet("Dathomir",
            new Point(1234, 53), "Outer Rim", "Zabrak"
    );
    public static Planet planet4 = new Planet("Mandalore",
            new Point(1234, 53), "Outer Rim", "Human"
    );

    public static java.util.List<Planet> all = List.of(
            planet1,
            planet2,
            planet3,
            planet4
    );

    /*================================================================================================================*/

    public static PlanetCreateDTO createDTO(Planet planet) {
        return new PlanetCreateDTO(
                planet.getName(),
                planet.getCoordinate(),
                planet.getTerritory(),
                planet.getNativeRace());
    }

    public static PlanetDTO dto(Planet planet) {
        return new PlanetDTO(
                planet.getId(),
                planet.getName(),
                planet.getCoordinate(),
                planet.getTerritory(),
                planet.getNativeRace());
    }

    public static String toCreateJSON(Planet planet) {
        return "{ \"name\": \"" + planet.getName() + "\", "
                + "\"coordinate\": {\"x\": " + planet.getCoordinate().getX() + ", \"y\": " + planet.getCoordinate().getY() + "}, "
                + "\"territory\": \"" + planet.getTerritory() + "\", "
                + "\"nativeRace\": \"" + planet.getNativeRace() + "\" }";
    }
}
