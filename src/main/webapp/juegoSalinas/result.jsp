<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.ausiasmarch.juegoSalinas.model.JuegoSalinasBean" %>
<%
    JuegoSalinasBean game = (JuegoSalinasBean) request.getAttribute("game");
    String result = game.getResult();
    String alertClass = "info";
    if ("¡Ganaste!".equals(result)) {
        alertClass = "success";
    } else if ("Perdiste".equals(result)) {
        alertClass = "danger";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg border-0">
                    <div class="card-body text-center">
                        <h2 class="mb-4 text-primary">Resultado</h2>
                        <div class="row mb-4">
                            <div class="col">
                                <h5>Tu elección</h5>
                                <img src="img/<%= game.getUserChoice().toLowerCase() %>.png" width="80">
                                <div><%= game.getUserChoice() %></div>
                            </div>
                            <div class="col">
                                <h5>Ordenador</h5>
                                <img src="img/<%= game.getComputerChoice().toLowerCase() %>.png" width="80">
                                <div><%= game.getComputerChoice() %></div>
                            </div>
                        </div>
                        <div class="alert alert-<%= alertClass %> fs-4" role="alert">
                            <%= result %>
                        </div>
                        <a href="game.jsp" class="btn btn-success w-100 mb-2">Jugar de nuevo</a>
                        <a href="ScoreServlet" class="btn btn-outline-primary w-100">Ver puntuaciones</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>