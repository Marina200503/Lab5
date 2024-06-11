package server.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
//адаптеры
import java.io.IOException;
import java.time.ZonedDateTime;
/**
 * сериализация и десериализация объекта типа ZonedDateTime
 */
public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {
    /**
     * сериализатор
     * @param jsonWriter - элемент json
     * @param localDateTime - объект времени тип ZonedDateTime
     */
    @Override
    public void write(JsonWriter jsonWriter, ZonedDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDateTime.toString());
        }
    }
    /**
     * десееализаторр
     * @param jsonReader - элемент json.
     * @return объект типа ZonedDateTime.
     */
    @Override
    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return ZonedDateTime.parse(jsonReader.nextString());
        }
    }
}