<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Piedra, Papel o Tijera</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg border-0">
                    <div class="card-body text-center">
                        <h1 class="mb-4 text-primary">Piedra, Papel o Tijera</h1>
                        <p class="lead mb-4">¿Listo para jugar contra el ordenador?</p>
                        <form action="game.jsp" method="get">
                            <button type="submit" class="btn btn-success btn-lg w-100 mb-3">
                                <i class="bi bi-play-fill"></i> ¡Jugar!
                            </button>
                        </form>
                        <a href="ScoreServlet" class="btn btn-outline-primary w-100">Ver puntuaciones</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>