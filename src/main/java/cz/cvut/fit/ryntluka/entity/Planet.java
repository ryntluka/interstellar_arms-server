package cz.cvut.fit.ryntluka.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name="inhabitants")
    private List<Customer> inhabitants;

    public Planet() {
    }

    public Planet(String name, Point coordinate, String territory, String nativeRace, List<Customer> inhabitants) {
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
        this.inhabitants = inhabitants;
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

    public List<Customer> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Customer> inhabitants) {
        this.inhabitants = inhabitants;
    }
}
