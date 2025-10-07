<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session == null || session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Math Challenge - torbesa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
        .btn-secondary-custom {
            background: #6c757d;
            color: #fff;
            font-weight: bold;
            transition: transform 0.2s;
        }
        .btn-secondary-custom:hover {
            transform: scale(1.05);
            background: #5a6268;
        }
        .btn-highscores {
            background: #17a2b8;
            color: #fff;
            font-weight: bold;
            transition: transform 0.2s;
        }
        .btn-highscores:hover {
            transform: scale(1.05);
            background: #138496;
        }
        .math-icons {
            font-size: 3rem;
            margin: 0 0.5rem;
        }
        .footer-note {
            font-size: 0.9rem;
            color: #f0f0f0aa;
            margin-top: 1rem;
        }
    </style>
</head>

<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="text-center">
        <div class="card p-5" style="max-width: 600px;">
            <h1 class="mb-3">🧮 <span class="text-warning">Math Challenge</span> 🧮</h1>
            <p class="lead mb-4">Demuestra tus habilidades resolviendo <strong>10 operaciones matemáticas</strong> aleatorias. ¡Cada segundo cuenta!</p>
            
            <div class="mb-4">
                <i class="fas fa-square-root-alt math-icons"></i>
                <i class="fas fa-plus math-icons"></i>
                <i class="fas fa-minus math-icons"></i>
                <i class="fas fa-divide math-icons"></i>
                <i class="fas fa-times math-icons"></i>
            </div>
            
            <form action="../math/GameServlet" method="get" class="mb-3">
                <button type="submit" class="btn btn-math btn-lg w-100">Comenzar partida</button>
            </form>

            <form action="../math/HighscoresPublicServlet" method="get" class="mb-3">
                <button type="submit" class="btn btn-highscores btn-lg w-100">Ver Rankings Públicos</button>
            </form>

            <form action="../shared/welcome.jsp" method="get">
                <button type="submit" class="btn btn-secondary-custom btn-lg w-100">Volver al menú</button>
            </form>
            
            <div class="footer-note">
                ¡Buena suerte! Cada acierto suma puntos y cada intento cuenta.
            </div>
        </div>
    </div>
</body>
</html>
