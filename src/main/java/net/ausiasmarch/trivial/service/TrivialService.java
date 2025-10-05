package net.ausiasmarch.trivial.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.ausiasmarch.trivial.model.TrivialBean;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrivialService {
    
        private static final String API_URL = "https://opentdb.com/api.php?amount=1";

        public TrivialBean fetchTriviaQuestions() {
            // Aquí iría la lógica para hacer la llamada a la API y obtener las preguntas
            // Por simplicidad, este método solo devuelve una cadena fija
            StringBuilder response= new StringBuilder();
            try{
            URL url=new URL(API_URL);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            JSONObject obj = new JSONObject(response.toString());
            JSONArray results = obj.getJSONArray("results");
            JSONObject questionData = results.getJSONObject(0);

            String question = questionData.getString("question");
            String correctAnswer = questionData.getString("correct_answer");
            JSONArray incorrect = questionData.getJSONArray("incorrect_answers");

            List<String> options = new ArrayList<>();
            options.add(correctAnswer);
            for (int i = 0; i < incorrect.length(); i++) {
                options.add(incorrect.getString(i));
            }
            Collections.shuffle(options);

            return new TrivialBean(question, options, correctAnswer);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
}
