package lib.models;

import client.utility.Validatable;

import java.io.Serializable;
import java.util.Objects;
/**
 * класс координат
 */

public class Coordinates implements Validatable, Serializable {
    private Integer x; //Поле не может быть null
    private Long y; //Поле не может быть null
    public Coordinates(Integer x, Long y){
        this.x=x;
        this.y=y;
    }
    /**
     * Проверяет поле на валидность
     * @return Успешность выполнения команды.
     */
    public boolean validate(){
        if (x==null){
            return false;
        }
        return y!=null;
    }
    /**
     * Возвращает строковое представление объектов координат
     * @return строковое представление объектов координат.
     */
    @Override
    public String toString(){
        return x+";"+y;
    }
    /**
     * Возвращает хеш код
     * @return хеш код координат.
     */
    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
    /**
     * Сравнивает объект координат
     * @param o - объекты сравнения
     * @return Успешность сравнения.
     */
    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if ((o==null)|(this.getClass()!=o.getClass())){
            return false;
        }
        Coordinates that=(Coordinates) o;
        return Objects.equals(x, that.x)&&Objects.equals(y,that.y);
    }
}