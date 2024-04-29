package models;

public class Location {
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
    public boolean validate(){
        return z != null && name != null && (name.length() <= 919);
    }
}