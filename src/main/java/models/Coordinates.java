package models;

import utility.Validatable;

import java.util.Objects;

public class Coordinates implements Validatable {
    /**
     * класс координат
     */
    private Integer x; //Поле не может быть null
    private Long y; //Поле не может быть null
    public Coordinates(Integer x, Long y){
        this.x=x;
        this.y=y;
    }
    public boolean validate(){
        if (x==null){
            return false;
        }
        return y!=null;
    }
    @Override
    public String toString(){
        return x+";"+y;
    }
    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
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