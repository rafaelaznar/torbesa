package emojiQuiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import net.ausiasmarch.emojiQuiz.dao.EmojiScoreDao;
import net.ausiasmarch.emojiQuiz.model.EmojiScoreBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmojiScoreDaoTest {

    private static final String COUNT_SQL =
        "SELECT COUNT(*) FROM emoji_quiz_score WHERE user_id = ?";
    private static final String SANITIZE_SQL =
        "DELETE FROM emoji_quiz_score " +
        "WHERE user_id = ? " +
        "  AND id NOT IN ( " +
        "    SELECT id FROM ( " +
        "      SELECT id FROM emoji_quiz_score " +
        "      WHERE user_id = ? " +
        "      ORDER BY `timestamp` DESC " +
        "      LIMIT 1 " +
        "    ) t " +
        "  )";
    private static final String SELECT_SQL =
        "SELECT e.id, e.user_id, u.username, e.score, e.tries, e.`timestamp` " +
        "FROM emoji_quiz_score e " +
        "JOIN users u ON e.user_id = u.id " +
        "WHERE e.user_id = ? " +
        "ORDER BY e.`timestamp` DESC " +
        "LIMIT 1";
    private static final String INSERT_SQL =
        "INSERT INTO emoji_quiz_score (user_id, score, tries, `timestamp`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_SQL =
        "DELETE FROM emoji_quiz_score WHERE id = ?";

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Test
    public void countByUserReturnsDatabaseCount() throws Exception {
        when(connection.prepareStatement(COUNT_SQL)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(3);

        EmojiScoreDao dao = new EmojiScoreDao(connection);
        int result = dao.countByUser(17);

        assertEquals(3, result);
        verify(preparedStatement).setInt(1, 17);
    }

    @Test
    public void sanitizeByUserDeletesAllButLatestRow() throws Exception {
        when(connection.prepareStatement(SANITIZE_SQL)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(2);

        EmojiScoreDao dao = new EmojiScoreDao(connection);
        int deleted = dao.sanitizeByUser(5);

        assertEquals(2, deleted);
        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).setInt(2, 5);
    }

    @Test
    public void getReturnsLatestScoreAndSanitizesWhenMultipleRowsFound() throws Exception {
        PreparedStatement selectStatement = org.mockito.Mockito.mock(PreparedStatement.class);
        ResultSet selectResult = org.mockito.Mockito.mock(ResultSet.class);

        EmojiScoreDao dao = org.mockito.Mockito.spy(new EmojiScoreDao(connection));
        doReturn(2).when(dao).countByUser(9);
        doReturn(1).when(dao).sanitizeByUser(9);

        when(connection.prepareStatement(SELECT_SQL)).thenReturn(selectStatement);
        when(selectStatement.executeQuery()).thenReturn(selectResult);
        when(selectResult.next()).thenReturn(true);
        when(selectResult.getInt("id")).thenReturn(21);
        when(selectResult.getInt("user_id")).thenReturn(9);
        when(selectResult.getString("username")).thenReturn("player");
        when(selectResult.getInt("score")).thenReturn(7);
        when(selectResult.getInt("tries")).thenReturn(3);

        LocalDateTime timestamp = LocalDateTime.of(2024, 2, 1, 10, 15, 30);
        when(selectResult.getTimestamp("timestamp")).thenReturn(Timestamp.valueOf(timestamp));

        EmojiScoreBean bean = dao.get(9);

        assertNotNull(bean);
        assertEquals(21, bean.getId());
        assertEquals(9, bean.getUserId());
        assertEquals("player", bean.getUsername());
        assertEquals(7, bean.getScore());
        assertEquals(3, bean.getTries());
        assertEquals(timestamp, bean.getTimestamp());
        verify(dao).sanitizeByUser(9);
    }

    @Test
    public void insertPersistsScoreAndReturnsGeneratedId() throws Exception {
        PreparedStatement insertStatement = org.mockito.Mockito.mock(PreparedStatement.class);
        ResultSet generatedKeys = org.mockito.Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS))
            .thenReturn(insertStatement);
        when(insertStatement.executeUpdate()).thenReturn(1);
        when(insertStatement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(44);

        LocalDateTime timestamp = LocalDateTime.of(2023, 12, 25, 8, 45);
        EmojiScoreBean bean = new EmojiScoreBean(0, 4, "", 12, 2, timestamp);

        EmojiScoreDao dao = new EmojiScoreDao(connection);
        int generatedId = dao.insert(bean);

        assertEquals(44, generatedId);
        assertEquals(44, bean.getId());

        verify(insertStatement).setInt(1, 4);
        verify(insertStatement).setInt(2, 12);
        verify(insertStatement).setInt(3, 2);
        verify(insertStatement).setTimestamp(4, Timestamp.valueOf(timestamp));
    }

    @Test
    public void deleteRemovesScoreById() throws Exception {
        when(connection.prepareStatement(DELETE_SQL)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        EmojiScoreDao dao = new EmojiScoreDao(connection);
        int affected = dao.delete(33);

        assertEquals(1, affected);
        verify(preparedStatement).setInt(1, 33);
    }
}