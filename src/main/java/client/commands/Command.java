package client.commands;

import client.utility.Describable;
import client.utility.Executable;
import lib.models.User;
import server.managers.SQLManager;

import java.util.Objects;

public abstract class Command implements Executable, Describable {
    /**
     * абстрактный класс для всех команд
     */
    private final String name;
    private final String description;
    public static User user;

    public Command(String name, String description){
        this.name=name;
        this.description=description;
    }
    /**
     * @return название команды
     */
    public String getName(){
        return name;
    }
    /**
     * @return предназначение команды
     */
    public String getDescription(){
        return description;
    }

    /**
     * @return имя и описание команды
     */
    @Override
    public String toString(){
        return "Command{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
    /**
     * @return хеш код для поля имени и описания
     */
    @Override
    public int hashCode(){
        return Objects.hash(name,description);
    }
    /**
     * @param o - объект сравнения
     * @return сравнение объектов
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(this.name, command.name) && Objects.equals(this.description, command.description);
    }



}