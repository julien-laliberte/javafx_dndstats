package dursahn.dndstats.dto;

import java.io.Serial;
import java.io.Serializable;

public class DmDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String color;

    public DmDTO(String name, String surname, String color) {
        this.name = name;
        this.surname = surname;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
