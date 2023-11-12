package Funcao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Cliente implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JsonObject responseJson = new JsonObject();
        JsonObject responseBody = new JsonObject();

        JsonObject inputObject = JsonParser.parseReader(reader).getAsJsonObject();

        if(inputObject.has("name")) {
            String nomeCliente = inputObject.get("name").getAsString();

            String message = "Processado com sucesso sua solicitação sr(a) " + nomeCliente;

            responseBody.addProperty("message", message);

            responseJson.addProperty("statusCode", 200);
            responseJson.addProperty("body", responseBody.toString());
        } else {
            responseBody.addProperty("message", "mensagem não foi tratada.");

            responseJson.addProperty("statusCode", 422);
            responseJson.addProperty("body", responseBody.toString());
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }
}
