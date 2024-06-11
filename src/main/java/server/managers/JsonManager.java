package server.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lib.models.Coordinates;
import lib.models.LabWork;
import lib.models.Location;
import lib.models.Person;
import server.adapters.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * работает с файлом
 */
public class JsonManager{

    private String jsonPath = System.getenv("JSON_PATH");
    //private String jsonPath = "C:\\Users\\New\\Desktop\\TestJson.json";
    //переменная в операционной системе (переменная окружения), путь к файлу
    //запомнили значение переменной окружения

    //форматирует данные (20.01.2001 запишет через точки) объясняет как сохранять
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()) //даём тип
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Location.class, new LocationDeserializer())
            .registerTypeAdapter(LabWork.class, new LabWorkDeserializer())
            .registerTypeAdapter(Person.class, new PersonDeserializer())
            .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
            .setPrettyPrinting() //красивы джейсон
            .create(); //готовим

//взяли и скопировали в фаил (сохранили строку через байты)
    /**
     * сохраняет список объектов в файл типа json
     */
    public void save(LinkedList<LabWork> manager) {
        String json = gson.toJson(manager); //храним большй текст
        try (FileOutputStream out = new FileOutputStream(jsonPath); //поток для записи в файл
             BufferedOutputStream bos = new BufferedOutputStream(out)) { //вкидаваем сюда и по символам вносим в фаил
            byte[] buffer = json.getBytes();
            bos.write(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //если переменная окружения была не заполнена (пустая, null)
    /**
     * загружает данные из файла
     * @return возвращает коллекцию лабораторных работ
     */
    public LinkedList<LabWork> load() {

        Scanner scan = null;
        if (jsonPath == null || jsonPath.isEmpty())
            jsonPath = "test.json";

        try {
            File jsonFile = new File(jsonPath); //это путь к файлу, а не строка

            if (!jsonFile.exists()) //фаил сущ?
                jsonFile.createNewFile();

            if (!jsonFile.canRead()) { //есть ли доступ к файлу, можем ли мы его считать
                System.out.println("Нет доступа к чтение! Чтение недоступно!");
                System.exit(1);
            }
            if (!jsonFile.canWrite()) { //есть доступ к записи
                System.out.println("Нет доступа к записи! Сохранение недоступно!");
                System.exit(1);
            }
            scan = new Scanner(jsonFile); //парсер (начинаем читать) читаем по байтам, ставим разделитель и читаем по слову абзац \\Z последний символ файла
        } catch (IOException e) {
            System.out.println("Ошибка. Неправильно указан путь в переменной окружения!");
            return new LinkedList<LabWork>();
        }
        scan.useDelimiter("\\Z");

        if (!scan.hasNext()) //если пустой, то возвращаем пустую коллекцию
            return new LinkedList<LabWork>();

        String content = scan.next(); //Если вставлен пустой символ и строка оказалась пустой
        if (content.isEmpty())
            return new LinkedList<LabWork>();
        try {
            Type collectionType = new TypeToken<LinkedList<LabWork>>(){}.getType();
            LinkedList<LabWork> repository = gson.fromJson(content, collectionType); //из строки получаем объект и из файла получаем класс
            return repository == null ? new LinkedList<LabWork>(): repository;
        } catch (Exception e) {
            System.out.println("Не удалось загрузить сохранение:");
            System.out.println(e.getMessage());
            System.exit(1);
            return new LinkedList<LabWork>();
        }

    }
}
