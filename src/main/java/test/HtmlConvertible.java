package momentum.retail.cif.sti.claim.domain;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public interface HtmlConvertible {

    default String toHtml(JsonObject element) {
        StringBuilder table = new StringBuilder();
        if (element == null) {
            element = new Gson().toJsonTree(this).getAsJsonObject();
            table.append("<style> #table { font-family: Arial, Helvetica, sans-serif; border-collapse: collapse; width: 90%; margin-left: 10px; margin-top: 10px} #table td, #table th { border: 2px solid #ddd; padding: 8px; } #table th { padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #04AA6D; color: white; } </style>");
        }
        table.append("<table id=\"table\"><tr><th>Property</th><th>Value</th></tr>");
        Set<Map.Entry<String, JsonElement>> entries = element.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            table.append("<tr><td>").append(entry.getKey()).append("</td><td>");

            JsonElement value = entry.getValue();
            if (value != null) {
                if (value.isJsonObject()) {
                    table.append(toHtml(value.getAsJsonObject()));
                } else if (value.isJsonArray()) {
                    JsonArray asJsonArray = value.getAsJsonArray();
                    for (JsonElement el : asJsonArray) {
                        if (el.isJsonObject()) {
                            table.append(toHtml(el.getAsJsonObject()));
                        } else {
                            table.append(value);
                        }
                    }
                } else {
                    table.append(value);
                }
            }
            table.append("</td></tr>");
        }
        table.append("</table>");
        return table.toString();
    }

    default String getEmailContent(EmailFeed feed) {
        StringBuilder table = new StringBuilder();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanSerializer());
        JsonObject jsonObject = gsonBuilder.create().toJsonTree(feed).getAsJsonObject();

        table.append("<style> #table { font-family: Arial, Helvetica, sans-serif; border-collapse: collapse; ")
                .append("width: 90%; margin-left: 10px; margin-top: 10px} #table td, #table th { ")
                .append("border: 2px solid #ddd; padding: 8px; } #table th { padding-top: 12px; padding-bottom: 12px; ")
                .append("text-align: left; background-color: #808080; color: white; } </style>")
                .append("<table id=\"table\"><tr><th colspan=\"2\">")
                .append(getTableHeader())
                .append("</th></tr>");

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            table.append("<tr><td>")
                    .append(entry.getKey())
                    .append("</td><td>")
                    .append(entry.getValue().getAsString())
                    .append("</td></tr>");
        }
        table.append("</table>");
        return table.toString();
    }

    String getTableHeader();

    String getClaimName();

    String getPolicyNumber();

    EmailFeed getEmailFeed();

    String getVehicleId();

    class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
        @Override
        public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src ? "Yes" : "No");
        }

        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return "Yes".equalsIgnoreCase(json.getAsString());
        }
    }
}

