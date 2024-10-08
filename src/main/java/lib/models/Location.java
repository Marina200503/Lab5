package lib.models;

import java.io.Serializable;

/**
 * класс локаций
 */
public class Location implements Serializable {
    private float x;
    private float y;
    private Long z; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 919, Поле может быть null

    public Location(float x, float y, Long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    /**
     * Проверка на валидность
     * @return успешность сравнения.
     */
    public boolean validate() {
        return z != null && name != null && (name.length() <= 919);
    }
    /**
     * Возвращает строковое представление объектов локации
     * @return строковое представление объектов локации.
     */
    @Override
    public String toString() {
        String locationInfo = "";
        locationInfo += " X: " + x + ", Y: " + y + ", Z: " + z + ", Name: " + name;
        return locationInfo;
    }
    public float getLocationX(){return x;}
    public float getLocationY(){return y;}
    public Long getLocationZ(){return z;}
    public String getLocationName(){return name;}
}