package adapters;


import com.google.gson.*;
import models.Location;
import models.Person;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class PersonDeserializer implements JsonDeserializer<Person> {
    @Override
    public Person deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ZonedDateTime birthday = ZonedDateTime.parse(jsonObject.get("birthday").getAsString());
        Long height = jsonObject.get("height").getAsLong();
        Float weight = jsonObject.get("weight").getAsFloat();
        Location location = jsonDeserializationContext.deserialize(jsonObject.get("location"), Location.class);
        String name = jsonObject.get("name").getAsString();


        try {
            Person pers = new Person(name, birthday, height, weight, location);
            if(!pers.validate()) {
                System.out.println("Некорректные данные в сохранении.");
                System.exit(1);
                return null;
            }
            return pers;
        }
        catch (Exception e) {
            System.out.println("Некорректные данные в сохранении. " + e.getMessage());
            System.exit(1);
        }
        return null;

    }
}
