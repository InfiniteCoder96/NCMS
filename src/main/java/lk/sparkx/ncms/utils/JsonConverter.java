package lk.sparkx.ncms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.var;

import java.util.List;

public class JsonConverter {

    private final Gson gson;

    public JsonConverter() {

        gson = new GsonBuilder().create();
    }

    public String ListConvertToJson(List<?> data) {

        JsonArray jarray = gson.toJsonTree(data).getAsJsonArray();
        var jsonObject = new JsonObject();
        jsonObject.add("data", jarray);

        return jsonObject.toString();
    }
}
