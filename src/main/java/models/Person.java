package models;

import utility.Validatable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Person implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private Float weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Location location; //Поле может быть null
    public Person(String name,java.time.ZonedDateTime birthday,Long height, Float weight, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }


    @Override
        public boolean validate(){
            if (name == null || name.isEmpty()) { return false; }
            if (birthday == null) { return false; }
            if (location != null && !location.validate()) { return false; }
            return true;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return name.equals(person.name) && birthday.equals(person.birthday) && location.equals(person.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, birthday, location);
        }

        @Override
        public String toString() {
            return name+" ; "+ birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)+" ; "+((location == null) ? "null" : location.toString());
        }
}
