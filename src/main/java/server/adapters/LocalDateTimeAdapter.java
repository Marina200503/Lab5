package server.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.time.LocalDateTime;
/**
 * сериализация и десериализация объекта LocalDateTime
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    /**
     * сериализатор
     * @param jsonWriter - элемент json
     * @param localDateTime- объект времени тип LocalDateTime
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDateTime.toString());
        }
    }
    /**
     * десееализаторр
     * @param jsonReader - элемент json.
     * @return объект типа LocalDateTime.
     */
    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }
}