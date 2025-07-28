package dursahn.dndstats.dto;

public class DmDTO {
    private String name;
    private String surname;
    private String color;

    public DmDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.color = null;
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
