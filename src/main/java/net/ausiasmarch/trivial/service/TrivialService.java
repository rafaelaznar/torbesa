package net.ausiasmarch.trivial.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.trivial.model.TrivialBean;

public class TrivialService {

    private static final String API_URL = "https://opentdb.com/api.php?amount=10"; // traer 10 preguntas de golpe

    /**
     * Obtiene la siguiente pregunta, usando la cache de la sesión.
     * @param session la sesión del usuario
     * @return TrivialBean con la pregunta
     */
    public TrivialBean getNextQuestion(HttpSession session) {
        List<TrivialBean> cachedQuestions = (List<TrivialBean>) session.getAttribute("triviaQuestions");

        // Si no hay preguntas en la sesión, traerlas del API
        if (cachedQuestions == null || cachedQuestions.isEmpty()) {
            cachedQuestions = fetchTriviaQuestionsFromAPI();
            if (cachedQuestions == null || cachedQuestions.isEmpty()) {
                return null; // fallo al obtener preguntas
            }
            session.setAttribute("triviaQuestions", cachedQuestions);
        }

        // Sacar la primera pregunta de la lista
        TrivialBean nextQuestion = cachedQuestions.remove(0);
        session.setAttribute("triviaQuestions", cachedQuestions); // actualizar la lista en sesión
        return nextQuestion;
    }

    /**
     * Llama a la API de OpenTDB y devuelve una lista de preguntas
     */
    private List<TrivialBean> fetchTriviaQuestionsFromAPI() {
        List<TrivialBean> questionsList = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            JSONArray results = obj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject questionData = results.getJSONObject(i);
                String question = questionData.getString("question");
                String correctAnswer = questionData.getString("correct_answer");
                JSONArray incorrect = questionData.getJSONArray("incorrect_answers");

                List<String> options = new ArrayList<>();
                options.add(correctAnswer);
                for (int j = 0; j < incorrect.length(); j++) {
                    options.add(incorrect.getString(j));
                }
                Collections.shuffle(options);

                questionsList.add(new TrivialBean(question, options, correctAnswer));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return questionsList;
    }
}

