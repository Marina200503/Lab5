package adapters;

import com.google.gson.*;
import models.Location;

import java.lang.reflect.Type;

public class LocationDeserializer implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        float x = jsonObject.get("x").getAsFloat();
        float y = jsonObject.get("y").getAsFloat();
        Long z = jsonObject.get("z").getAsLong();
        String name = jsonObject.get("name").getAsString();

        try {
            Location location = new Location(x, y, z, name);
            if(!location.validate()) {
                System.out.println("Некорректные данные в сохранении.");
                System.exit(1);
                return null;
            }
            return location;
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);

        }
        return null;
    }
}
