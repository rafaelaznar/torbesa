package net.ausiasmarch.emojiQuiz.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.emojiQuiz.dao.EmojiQuizDao;
import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;

public class EmojiQuizService {

    public List<EmojiQuizBean> fetchAllQuestions() throws SQLException { 
        try (Connection conn = HikariPool.getConnection()) {
            EmojiQuizDao questionList = new EmojiQuizDao(conn);
            return questionList.getAllQuestions();
        }
    }

    public EmojiQuizBean getQuestionById(long id) throws SQLException {
        try (Connection conn = HikariPool.getConnection()) {
            EmojiQuizDao questionList = new EmojiQuizDao(conn);
            return questionList.getQuestionById(id);
        }
    }

    public EmojiQuizBean getRandomQuestion() throws SQLException {
        List<EmojiQuizBean> questionList = fetchAllQuestions();
        int randomIndex = (int) (Math.random() * questionList.size());
        return questionList.get(randomIndex);
    }

    public List<String> getShuffledOptions(EmojiQuizBean question) {
        List<String> options = new ArrayList<>();
        options.add(question.getCorrectAnswer());
        options.add(question.getOption1());
        options.add(question.getOption2());

        Collections.shuffle(options);
        return options;
    }

    public String getCorrectAnswer(EmojiQuizBean question) {
        return question.getCorrectAnswer();
    }
}