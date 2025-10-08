<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="net.ausiasmarch.shared.model.UserBean" %>
<%@ page import="net.ausiasmarch.codequest.model.ScoreDto" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    // Recuperar usuario de sesión
    UserBean sessionUser = (UserBean) session.getAttribute("sessionUser");
    if (sessionUser == null) {
        response.sendRedirect("../shared/login.jsp");
        return;
    }

    // Recuperar datos pasados por el servlet
    ScoreDto userScore = (ScoreDto) request.getAttribute("userScore");
    List<ScoreDto> highScores = (List<ScoreDto>) request.getAttribute("highScores");
    List<ScoreDto> allScores = (List<ScoreDto>) request.getAttribute("allScores");

    // Verificación de depuración opcional
    if (userScore == null) out.println("<p>⚠️ userScore es nulo</p>");
    if (highScores == null) out.println("<p>⚠️ highScores es nulo</p>");
    if (allScores == null) out.println("<p>⚠️ allScores es nulo</p>");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ranking de Jugadores - CodeQuest</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #121212;
            color: #f1f1f1;
            margin: 0;
            padding: 20px;
        }
        h1, h2 {
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
            background-color: #1e1e1e;
            border: 1px solid #333;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #333;
            text-align: center;
        }
        th {
            background-color: #333;
            color: #fff;
        }
        tr:hover {
            background-color: #2a2a2a;
        }
        .user-row {
            background-color: #0066cc;
            color: #fff;
        }
        .back-button {
            display: block;
            margin: 20px auto;
            text-align: center;
        }
        a.button {
            background-color: #0066cc;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 8px;
        }
        a.button:hover {
            background-color: #004c99;
        }
    </style>
</head>
<body>

    <h1>🏆 Ranking de Jugadores - CodeQuest</h1>
    <h2>Bienvenido, <%= sessionUser.getLogin() %>!</h2>

    <h3>Tu puntuación</h3>
    <table>
        <tr>
            <th>Usuario</th>
            <th>Puntuación</th>
            <th>Posición</th>
        </tr>
        <tr class="user-row">
            <td><%= sessionUser.getLogin() %></td>
            <td><%= userScore != null ? userScore.getScore() : "N/A" %></td>
            <td>
                <%
                    int userRank = -1;
                    if (allScores != null && userScore != null) {
                        for (int i = 0; i < allScores.size(); i++) {
                            if (allScores.get(i).getUserId() == userScore.getUserId()) {
                                userRank = i + 1;
                                break;
                            }
                        }
                    }
                %>
                <%= userRank > 0 ? String.valueOf(userRank) : "N/A" %>
            </td>
        </tr>
    </table>

    <h3>Top 10 Jugadores</h3>
    <table>
        <tr>
            <th>Posición</th>
            <th>Usuario</th>
            <th>Puntuación</th>
            <th>Última Actualización</th>
        </tr>
        <%
            if (highScores != null && !highScores.isEmpty()) {
                int rank = 1;
                for (ScoreDto score : highScores) {
                    boolean isCurrentUser = (userScore != null && score.getUserId() == userScore.getUserId());
        %>
                    <tr class="<%= isCurrentUser ? "user-row" : "" %>">
                        <td><%= rank++ %></td>
                        <td><%= score.getLogin() %></td>
                        <td><%= score.getScore() %></td>
                        <td><%= score.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></td>
                    </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="4">No hay puntuaciones registradas</td></tr>
        <%
            }
        %>
    </table>

    <div class="back-button">
        <a class="button" href="menu.jsp">⬅ Volver al Menú Principal</a>
    </div>

</body>
</html>
