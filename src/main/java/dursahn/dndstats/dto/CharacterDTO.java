package dursahn.dndstats.dto;

public class CharacterDTO {
    private String name;
    private String _class;
    private String subclass;
    private Integer classLevel;
    private String class2;
    private String subclass2;
    private Integer classLevel2;
    private String color;

    public CharacterDTO(String name, String _class, String subclass, Integer classLevel,
                        String class2, String subclass2, Integer classLevel2, String color) {
        this.name = name;
        this._class = _class;
        this.subclass = subclass;
        this.classLevel = classLevel;
        this.class2 = class2;
        this.subclass2 = subclass2;
        this.classLevel2 = classLevel2;
        this.color = color;
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

    public Integer getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(Integer classLevel) {
        this.classLevel = classLevel;
    }

    public String getClass2() {
        return class2;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }

    public String getSubclass2() {
        return subclass2;
    }

    public void setSubclass2(String subclass2) {
        this.subclass2 = subclass2;
    }

    public Integer getClassLevel2() {
        return classLevel2;
    }

    public void setClassLevel2(Integer classLevel2) {
        this.classLevel2 = classLevel2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
