<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="net.ausiasmarch.juegoSalinas.model.ScoreDto" %>
<%
    List<ScoreDto> scores = (List<ScoreDto>) request.getAttribute("scores");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Puntuaciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg border-0">
                    <div class="card-body text-center">
                        <h2 class="mb-4 text-primary">Top 10 Puntuaciones</h2>
                        <table class="table table-striped table-bordered">
                            <thead class="table-primary">
                                <tr>
                                    <th>Usuario</th>
                                    <th>Puntuación</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (scores != null && !scores.isEmpty()) {
                                        for (ScoreDto score : scores) {
                                %>
                                <tr>
                                    <td><%= score.getUsername() %></td>
                                    <td><%= score.getScore() %></td>
                                </tr>
                                <%
                                        }
                                    } else {
                                %>
                                <tr>
                                    <td colspan="2" class="text-center text-muted">No hay puntuaciones aún.</td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <a href="landing.jsp" class="btn btn-link mt-3">Volver al juego</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>