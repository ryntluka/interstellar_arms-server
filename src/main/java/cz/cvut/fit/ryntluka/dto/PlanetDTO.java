package cz.cvut.fit.ryntluka.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

public class PlanetDTO {

    private final int id;
    private final String name;
    private final Point coordinate;
    private final String territory;
    private final String nativeRace;
    private final List<Integer> inhabitantsIds;

    public PlanetDTO(int id, String name, Point coordinate, String territory, String nativeRace, List<Integer> inhabitantsIds) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
        this.inhabitantsIds = inhabitantsIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public String getTerritory() {
        return territory;
    }

    public String getNativeRace() {
        return nativeRace;
    }

    public List<Integer> getInhabitantsIds() {
        return inhabitantsIds;
    }
}
