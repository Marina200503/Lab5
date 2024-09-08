package lib.models;

import client.utility.Validatable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * класс автора
 */
public class Person implements Validatable, Serializable {
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

    public Location getLocation() {
        return location;
    }

    /**
     * Проверка на валидность
     * @return успешность сравнения.
     */
    @Override
        public boolean validate(){
            if (name == null || name.isEmpty()) { return false; }
            if (birthday == null) { return false; }
            if (location != null && !location.validate()) { return false; }
            if(height==null){return false;}
            if(weight==null){return false;}



        return true;
        }
    /**
     * Возвращает имя автора
     * @return имя автора.
     */
        public String getName() {
            return name;
        }
        public ZonedDateTime getBirthday(){return  birthday;}
        public Long getHeight(){return height;
        }
        public Float getWeight(){return weight;}

    /**
     * Сравнивает объект автор
     * @param o - объекты сравнения
     * @return Успешность сравнения.
     */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return name.equals(person.name) && birthday.equals(person.birthday) && location.equals(person.location) && height.equals(person.height) && weight.equals(person.weight);
        }
    /**
     * Возвращает хеш код
     * @return хеш код составляющих автора.
     */
        @Override
        public int hashCode() {
            return Objects.hash(name, birthday, location, height, weight);
        }
    /**
     * Возвращает строковое представление автора лабораторной работы
     * @return строковое представление автора лабораторной работы.
     */
        @Override
        public String toString() {
            return name+" ; "+ birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)+" ; "+((location == null) ? "null" : location.toString()) +" ; height: " + height +" ; weight: "+ weight;
        }
}
