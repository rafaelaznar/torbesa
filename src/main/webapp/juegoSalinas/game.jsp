<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Elige tu jugada</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <jsp:include page="../shared/menu.jsp" />
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg border-0">
                    <div class="card-body text-center">
                        <h2 class="mb-4 text-primary">Elige tu jugada</h2>
                        <form action="GameServlet" method="post" class="d-flex justify-content-around">
                            <button type="submit" name="choice" value="Piedra" class="btn btn-light border shadow-sm mx-2">
                                <img src="img/piedra.png" alt="Piedra" width="80"><br>Piedra
                            </button>
                            <button type="submit" name="choice" value="Papel" class="btn btn-light border shadow-sm mx-2">
                                <img src="img/papel.png" alt="Papel" width="80"><br>Papel
                            </button>
                            <button type="submit" name="choice" value="Tijera" class="btn btn-light border shadow-sm mx-2">
                                <img src="img/tijera.png" alt="Tijera" width="80"><br>Tijera
                            </button>
                        </form>
                        <a href="landing.jsp" class="btn btn-link mt-4">Volver</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>