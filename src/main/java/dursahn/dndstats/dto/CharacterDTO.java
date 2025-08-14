package dursahn.dndstats.dto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CharacterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String _class;
    private String subclass;
    private Integer classLevel;
    private String class2;
    private String subclass2;
    private Integer classLevel2;
    private String color;
    private Integer damageDone;
    private Integer damageReceived;
    private Integer alliedDamage;
    private Integer healDone;
    private Integer healReceived;
    private Integer personalHeal;
    private Integer critical;
    private Integer failure;
    private Integer minionControlled;
    private Integer minionKilled;
    private Integer bossKilled;
    private Integer controlled;
    private Integer knockOut;
    private Boolean isVisible;
    private Integer buffs;

    public CharacterDTO(String firstName, String lastName, String _class, String subclass, Integer classLevel,
                        String class2, String subclass2, Integer classLevel2, String color) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this._class = _class;
        this.subclass = subclass;
        this.classLevel = classLevel;
        this.class2 = class2;
        this.subclass2 = subclass2;
        this.classLevel2 = classLevel2;
        this.color = color;
        this.damageDone = 0;
        this.damageReceived = 0;
        this.alliedDamage = 0;
        this.healDone = 0;
        this.healReceived = 0;
        this.personalHeal = 0;
        this.critical = 0;
        this.failure = 0;
        this.minionControlled = 0;
        this.minionKilled = 0;
        this.bossKilled = 0;
        this.controlled = 0;
        this.knockOut = 0;
        this.isVisible = true;
        this.buffs = 0;
    }

    @Override
    public String toString() {
        return "Character {firstName='" + firstName + "', lastName='" + lastName + "', class='" + _class + "'}";
    }

    //region Getters and Setters
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setId(String id) {
        this.id = id;
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

    public Integer getAlliedDamage() {
        return alliedDamage;
    }

    public void setAlliedDamage(Integer alliedDamage) {
        this.alliedDamage = alliedDamage;
    }

    public Integer getHealDone() {
        return healDone;
    }

    public void setHealDone(Integer healDone) {
        this.healDone = healDone;
    }

    public Integer getHealReceived() {
        return healReceived;
    }

    public void setHealReceived(Integer healReceived) {
        this.healReceived = healReceived;
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

    public Integer getMinionControlled() {
        return minionControlled;
    }

    public void setMinionControlled(Integer minionControlled) {
        this.minionControlled = minionControlled;
    }

    public Integer getMinionKilled() {
        return minionKilled;
    }

    public void setMinionKilled(Integer minionKilled) {
        this.minionKilled = minionKilled;
    }

    public Integer getBossKilled() {
        return bossKilled;
    }

    public void setBossKilled(Integer bossKilled) {
        this.bossKilled = bossKilled;
    }

    public Integer getControlled() {
        return controlled;
    }

    public void setControlled(Integer controlled) {
        this.controlled = controlled;
    }

    public Integer getKnockOut() {
        return knockOut;
    }

    public void setKnockOut(Integer knockOut) {
        this.knockOut = knockOut;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Integer getBuffs() {
        return buffs;
    }

    public void setBuffs(Integer buffs) {
        this.buffs = buffs;
    }
    //endregion
    public void resetCharacter(){
        this.damageDone = 0;
        this.damageReceived = 0;
        this.alliedDamage = 0;
        this.healDone = 0;
        this.healReceived = 0;
        this.personalHeal = 0;
        this.critical = 0;
        this.failure = 0;
        this.minionControlled = 0;
        this.minionKilled = 0;
        this.bossKilled = 0;
        this.controlled = 0;
        this.knockOut = 0;
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (buffs == null) {
            buffs = 0; // valeur par défaut pour anciens objets
            alliedDamage = 0;
        }
    }
}
