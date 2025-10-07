<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if(session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Math Challenge - Juego</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <style>
        body {
            background: linear-gradient(to right, #1e3c72, #2a5298);
            color: #fff;
            font-family: 'Arial', sans-serif;
        }
        .card {
            border-radius: 2rem;
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.25);
            color: #fff;
        }
        .btn-math {
            background: #ffdd57;
            color: #1e3c72;
            font-weight: bold;
            transition: transform 0.2s;
        }
        .btn-math:hover {
            transform: scale(1.05);
            background: #ffc107;
        }
        .badge-success, .badge-danger {
            font-size: 1.2rem;
            padding: 0.5rem 1rem;
        }
    </style>
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="d-flex justify-content-center align-items-center w-100">
        <div class="card p-5" style="max-width: 600px; width: 100%;">
            <h2 class="mb-4">🧮 Resuelve el Problema Matemático</h2>

            <h3>Pregunta: <span class="text-warning">${question}</span></h3>

            <form method="post" action="GameServlet" class="mt-3">
                <input type="number" step="0.01" name="answer" class="form-control mb-3" placeholder="Introduce tu respuesta" required/>
                <button type="submit" class="btn btn-math btn-lg w-100">Enviar</button>
            </form>

            <c:if test="${correct != null}">
                <div class="mt-3">
                    <span class="badge ${correct ? 'bg-success' : 'bg-danger'}">
                        ${correct ? '¡Correcto!' : 'Incorrecto'}
                    </span>
                </div>
            </c:if>

            <div class="mt-4">
                Puntuación: <span class="badge bg-primary">${score.score}</span> | Intentos: <span class="badge bg-info">${score.tries}</span>
            </div>
        </div>
    </div>
</body>
</html>
