package net.ausiasmarch.trivialReyna.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.trivialReyna.model.QuestionBean;

public class QuestionService {

    private static final String API_URL = "https://opentdb.com/api.php?amount=50&category=18&difficulty=medium&type=multiple";
    private ServletContext oContext;

    public QuestionService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<QuestionBean> fetchAllQuestions() {
        // revisar si ya están en el contexto
        if (oContext != null) {
            @SuppressWarnings("unchecked")
            List<QuestionBean> questions = (List<QuestionBean>) oContext.getAttribute("questions");
            if (questions != null) {
                return questions;
            }
        }

        System.out.println("Descargando preguntas de la API...");
        List<QuestionBean> questions = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(content.toString());
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                String questionText = obj.getString("question");
                String correctAnswer = obj.getString("correct_answer");

                JSONArray incorrectArray = obj.getJSONArray("incorrect_answers");
                ArrayList<String> options = new ArrayList<>();
                options.add(correctAnswer);
                for (int j = 0; j < incorrectArray.length(); j++) {
                    options.add(incorrectArray.getString(j));
                }

                Collections.shuffle(options); // mezclamos opciones

                questions.add(new QuestionBean(questionText, correctAnswer, options));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oContext != null) {
            oContext.setAttribute("questions", questions);
        }

        return questions;
    }

    public QuestionBean getOneRandomQuestion() {
        List<QuestionBean> oQuestions = fetchAllQuestions();
        int randomIndex = (int) (Math.random() * oQuestions.size());
        QuestionBean selectedQuestion = oQuestions.get(randomIndex);
        while (selectedQuestion.getQuestion().trim().isEmpty()) {
            randomIndex = (int) (Math.random() * oQuestions.size());
            selectedQuestion = oQuestions.get(randomIndex);
        }
        return selectedQuestion;
    }

    public ArrayList<String> getRandomOptionsForTest(QuestionBean question, int numberOfOptions) {
        ArrayList<String> options = new ArrayList<>(question.getOptions());
        Collections.shuffle(options);
        if (numberOfOptions >= options.size()) {
            return options; // si el número solicitado es mayor o igual, devolver todas
        }
        ArrayList<String> selectedOptions = new ArrayList<>(options.subList(0, numberOfOptions - 1));
        if (!selectedOptions.contains(question.getCorrectAnswer())) {
            selectedOptions.set(0, question.getCorrectAnswer()); // asegurar que la correcta esté incluida
        }
        Collections.shuffle(selectedOptions); // mezclar de nuevo para aleatorizar posición
        return selectedOptions;
    }

}
