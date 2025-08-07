package dursahn.dndstats.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class DmDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String surname;
    private String color;
    private Integer damageDone;
    private Integer damageReceived;
    private Integer personalHeal;
    private Integer critical;
    private Integer failure;
    private Integer crowdControlled;
    private Integer minionLost;
    private Integer bossLost;
    private Integer playerControlled;
    private Integer playerKOs;
    private Boolean isVisible;

    public DmDTO(String name, String surname, String color) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.color = color;
        this.damageDone = 0;
        this.damageReceived = 0;
        this.personalHeal = 0;
        this.critical = 0;
        this.failure = 0;
        this.crowdControlled = 0;
        this.minionLost = 0;
        this.bossLost = 0;
        this.playerControlled = 0;
        this.playerKOs = 0;
        this.isVisible = false;
    }

    @Override
    public String toString() {
        return "Dm {Name='" + name + "'}";
    }

    //region Getters and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getDamageDone() {
        return damageDone;
    }

    public void setDamageDone(Integer damageDone) {
        this.damageDone = damageDone;
    }

    public Integer getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(Integer damageReceived) {
        this.damageReceived = damageReceived;
    }

    public Integer getPersonalHeal() {
        return personalHeal;
    }

    public void setPersonalHeal(Integer personalHeal) {
        this.personalHeal = personalHeal;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCrowdControlled() {
        return crowdControlled;
    }

    public void setCrowdControlled(Integer crowdControlled) {
        this.crowdControlled = crowdControlled;
    }

    public Integer getMinionLost() {
        return minionLost;
    }

    public void setMinionLost(Integer minionLost) {
        this.minionLost = minionLost;
    }

    public Integer getBossLost() {
        return bossLost;
    }

    public void setBossLost(Integer bossLost) {
        this.bossLost = bossLost;
    }

    public Integer getPlayerControlled() {
        return playerControlled;
    }

    public void setPlayerControlled(Integer playerControlled) {
        this.playerControlled = playerControlled;
    }

    public Integer getPlayerKOs() {
        return playerKOs;
    }

    public void setPlayerKOs(Integer playerKOs) {
        this.playerKOs = playerKOs;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
    //endregions
}
