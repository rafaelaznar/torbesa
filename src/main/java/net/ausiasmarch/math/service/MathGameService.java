package net.ausiasmarch.math.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.servlet.http.HttpSession;

import net.ausiasmarch.math.model.MathQuestionBean;
import net.ausiasmarch.math.model.MathScoreBean;

public class MathGameService {

    public MathQuestionBean generateQuestion(HttpSession session) {
        Random rand = new Random();
        int a = rand.nextInt(50) + 1;
        int b = rand.nextInt(50) + 1;
        String[] ops = { "+", "-", "*", "/" };
        String op = ops[rand.nextInt(ops.length)];
        String expr = a + op + b;

        double answer = 0;
        try {
            answer = getAnswerFromAPI(expr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MathQuestionBean question = new MathQuestionBean(expr, answer);
        session.setAttribute("currentQuestion", question);

        if (session.getAttribute("mathScore") == null) {
            session.setAttribute("mathScore", new MathScoreBean());
        }

        return question;
    }

    public boolean checkAnswer(HttpSession session, String userAnswerStr) {
        try {
            double userAnswer = Double.parseDouble(userAnswerStr);
            MathQuestionBean question = (MathQuestionBean) session.getAttribute("currentQuestion");
            return Math.abs(userAnswer - question.getAnswer()) < 0.01;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private double getAnswerFromAPI(String expression) throws Exception {
        String urlStr = "https://api.mathjs.org/v4/?expr=" + java.net.URLEncoder.encode(expression,"UTF-8");
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String result = in.readLine();
        in.close();
        return Double.parseDouble(result);
    }
}
