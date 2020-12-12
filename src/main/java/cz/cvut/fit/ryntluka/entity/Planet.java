package cz.cvut.fit.ryntluka.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Planet {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private Point coordinate;

    private String territory;

    private String nativeRace;

    public Planet() {
    }

    public Planet(String name, Point coordinate, String territory, String nativeRace) {
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getNativeRace() {
        return nativeRace;
    }

    public void setNativeRace(String natives) {
        this.nativeRace = natives;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinate, territory, nativeRace);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Planet planet = (Planet) obj;
        return id == planet.id
                && Objects.equals(name, planet.name)
                && Objects.equals(coordinate, planet.coordinate)
                && Objects.equals(territory, planet.territory)
                && Objects.equals(nativeRace, planet.nativeRace);
    }
}
