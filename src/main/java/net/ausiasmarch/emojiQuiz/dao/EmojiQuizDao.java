package net.ausiasmarch.emojiQuiz.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;

public class EmojiQuizDao {

    Connection conn = null;

    public EmojiQuizDao(Connection conn) {
        this.conn = conn;
    }

    public List<EmojiQuizBean> getAllQuestions() throws SQLException {
        List<EmojiQuizBean> questionList = new ArrayList<>();
        String query = "SELECT * FROM emoji_quiz";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                EmojiQuizBean question = new EmojiQuizBean();
                question.setId(rs.getInt("id"));
                question.setQuestion(rs.getString("question"));
                question.setCorrectAnswer(rs.getString("correct_answer"));
                question.setOption1(rs.getString("option1"));
                question.setOption2(rs.getString("option2"));

                questionList.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return questionList;
    }

    public EmojiQuizBean getQuestionById(long id) throws SQLException {
        String query = "SELECT * FROM emoji_quiz WHERE id = " + id;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                EmojiQuizBean question = new EmojiQuizBean();
                question.setId(rs.getInt("id"));
                question.setQuestion(rs.getString("question"));
                question.setCorrectAnswer(rs.getString("correct_answer"));
                question.setOption1(rs.getString("option1"));
                question.setOption2(rs.getString("option2"));
                return question;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
}
