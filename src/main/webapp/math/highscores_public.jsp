<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, net.ausiasmarch.math.model.MathScoreBean" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>High Scores Públicos - Math Challenge</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(to right, #1e3c72, #2a5298); color: #fff; font-family: 'Arial', sans-serif; }
        .card { border-radius: 2rem; background: rgba(255,255,255,0.1); backdrop-filter: blur(10px); box-shadow: 0 8px 32px rgba(0,0,0,0.25); color: #fff; }
        .btn-secondary-custom { background: #6c757d; color: #fff; font-weight: bold; transition: transform 0.2s; }
        .btn-secondary-custom:hover { transform: scale(1.05); background: #5a6268; }
        .table thead { background-color: rgba(23,162,184,0.3); }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="card p-4">
        <h2 class="text-center mb-4 text-warning">🌟 Ranking Público - Top 10</h2>

        <%
            List<MathScoreBean> highscores = (List<MathScoreBean>) request.getAttribute("highscores");
            if (highscores == null || highscores.isEmpty()) {
        %>
            <div class="alert alert-warning text-center">No hay puntuaciones registradas todavía.</div>
        <%
            } else {
        %>
            <table class="table table-striped table-bordered shadow-sm text-white">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Jugador</th>
                        <th>Intentos</th>
                        <th>Puntuación</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    int rank = 1;
                    for (MathScoreBean scoreBean : highscores) {
                %>
                    <tr>
                        <td><%= rank++ %></td>
                        <td><%= scoreBean.getUsername() %></td>
                        <td><%= scoreBean.getTries() %></td>
                        <td><%= scoreBean.getScore() %></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
            }
        %>

        <div class="text-center mt-4">
            <a href="../shared/welcome.jsp" class="btn btn-secondary-custom">Volver al menú</a>
        </div>
    </div>
</div>
</body>
</html>
