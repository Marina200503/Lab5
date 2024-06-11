package server.adapters;

import com.google.gson.*;
import lib.models.Coordinates;
import lib.models.Difficulty;
import lib.models.LabWork;
import lib.models.Person;

import java.lang.reflect.Type;

/**
 * преобразует объект json в лабораторную работу
 */
public class LabWorkDeserializer implements JsonDeserializer<LabWork> {

    /**
     * десереализатор
     * @param jsonElement - элемент типа json
     * @param type - тип параметров лабораторной работы
     * @param jsonDeserializationContext - контекст десереализации
     * @return объект лабораторная работа.
     */
    @Override
    public LabWork deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();
        Coordinates coordinates = jsonDeserializationContext.deserialize(jsonObject.get("coordinates"), Coordinates.class);
        //LocalDateTime creationDate = LocalDateTime.parse(jsonObject.get("creationDate").toString());
        Long minimalPoint = jsonObject.get("minimalPoint").getAsLong();
        Difficulty difficulty = Difficulty.valueOf(jsonObject.get("difficulty").getAsString());
        Person author = jsonDeserializationContext.deserialize(jsonObject.get("author"), Person.class);

        try {
            LabWork labWork = new LabWork(id, name, coordinates, minimalPoint, difficulty, author);
            if (!labWork.validate()) {
                System.out.println("Некорректные данные в сохранении.");
                System.exit(1);
                return null;
            }
            return labWork;
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);

        }
        return null;
    }
}
