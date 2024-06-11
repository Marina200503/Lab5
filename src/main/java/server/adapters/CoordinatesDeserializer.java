package server.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lib.models.Coordinates;

import java.lang.reflect.Type;
/**
 * преобразует объект json в координаты
 */
public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {
    /**
     * десереализатор
     * @param jsonElement - элемент типа json
     * @param type - тип координат
     * @param jsonDeserializationContext - контекст десереализации
     * @return объект координат.
     */
    @Override
    public Coordinates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)  {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Integer x = jsonObject.get("x").getAsInt();
        Long y = jsonObject.get("y").getAsLong();
        try {
            Coordinates coord = new Coordinates(x, y);
            if (!coord.validate()) {
                System.out.println("Некорректные данные в сохранении.");
                System.exit(1);
                return null;
            }
            return coord;
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);

        }
        return null;

    }
}
