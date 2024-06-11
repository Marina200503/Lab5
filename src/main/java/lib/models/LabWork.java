package lib.models;
import client.utility.Validatable;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * класс лабораторных работ
 */

public class LabWork implements Validatable, Comparable<LabWork>, Serializable {
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
    /**
     * Проверяет поле на валидность
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean validate() {
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
    /**
     * Возвращает id лабораторно работы
     * @return id.
     */
    public long getId() {
        return id;
    }
    /**
     * Возвращает название лабораторно работы
     * @return название лабораторно работы.
     */
    public String getName() {
        return name;
    }
    /**
     * Возвращает миномальный балл лабораторно работы
     * @return минимальный балл лабораторно работы.
     */

    public Long getMinimalPoint() {
        return minimalPoint;
    }
    /**
     * Возвращает сложность лабораторно работы
     * @return сложность лабораторно работы.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
    /**
     * Сравнивает объект лабораторной работы
     * @param o - объекты сравнения
     * @return Успешность сравнения.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return id == labWork.id && name.equals(labWork.name) && coordinates.equals(labWork.coordinates) && creationDate.equals(labWork.creationDate) && (minimalPoint == labWork.minimalPoint) && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }//Метод равенства (проверяет объекты на существование)
    /**
     * Возвращает хеш код
     * @return хеш код координат.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint, difficulty, author);
    }
    /**
     * Возвращает строковое представление объектов лабораторной работы
     * @return строковое представление объектов лабораторной работы.
     */
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
    /**
     * Сравнение объектов типа LabWork по полю сложности
     * @param o - объект сравнения
     * @return разница между текущим объектом и переданным объектом o.
     */
    @Override
    public int compareTo(LabWork o) {
        return this.difficulty.getValue() - o.difficulty.getValue();
    }
    /**
     * устанавливает индентификатор
     * @param id - индефикатор для установки.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
