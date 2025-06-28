package dursahn.dndstats.dto;

public class PlayerDTO {

    private String name;
    private String _class;
    private String subclass;
    private Integer level;

    public PlayerDTO(String name, String _class, String subclass, Integer level) {
        this.name = name;
        this._class = _class;
        this.subclass = subclass;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
