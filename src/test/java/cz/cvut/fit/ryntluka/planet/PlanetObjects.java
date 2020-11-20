package cz.cvut.fit.ryntluka.planet;

import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;

import java.awt.*;
import java.util.ArrayList;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.*;

public class PlanetObjects {
    public static Planet planet1 = new Planet("Coruscant",
            new Point(2341, 563), "Core Worlds", "Human",
            new ArrayList<>()
    );
    public static Planet planet2 = new Planet("Mon Cala",
            new Point(1234, 53), "Outer Rim", "Mon Calamari",
            new ArrayList<>()
    );
    public static Planet planet3 = new Planet("Dathomir",
            new Point(1234, 53), "Outer Rim", "Zabrak",
            new ArrayList<>()
    );
    public static Planet planet4 = new Planet("Mandalore",
            new Point(1234, 53), "Outer Rim", "Human",
            new ArrayList<>()
    );
}
