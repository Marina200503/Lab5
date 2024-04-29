package models;
import utility.Validatable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;


public class LabWork implements Validatable, Comparable<LabWork> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long minimalPoint; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Person author; //Поле может быть null
    public LabWork(long id, String name, Coordinates coordinates, Long minimalPoint, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates=coordinates;
        this.creationDate = LocalDateTime.now();
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }
    @Override
    public boolean validate() {
        if (id <= 0) { return false; }
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (coordinates == null || !coordinates.validate()) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (minimalPoint <= 0) {
            return false;
        }
        if (author != null && !author.validate()) {
            return false;
        }
        return true;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getMinimalPoint() {
        return minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return id == labWork.id && name.equals(labWork.name) && coordinates.equals(labWork.coordinates) && creationDate.equals(labWork.creationDate) && (minimalPoint == labWork.minimalPoint) && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }//Метод равенства (проверяет объекты на существование)
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint, difficulty, author);
    }

    @Override
    public String toString(){
        String info = "";
        info += " ID: " + id;
        info += "\n Название работы: " + name;
        info += "\n Координаты: (" + coordinates +")";
        info += "\n Время создания: "+ creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        info += "\n Минимальный балл: "+ minimalPoint;
        info += "\n Сложность: "+((difficulty == null) ? "null" : difficulty);
        info += "\n Автор работы: "+ ((author == null) ? "null" : author);
        return info;
    }

    @Override
    public int compareTo(LabWork o) {
        return this.difficulty.getValue() - o.difficulty.getValue();
    }

    public void setId(Long id) {
        this.id = id;
    }
}
