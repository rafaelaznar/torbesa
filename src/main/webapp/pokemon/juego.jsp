<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Pokémon Ability Quiz</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
            <style>
                body {
                    background: linear-gradient(135deg, #ff6b6b 0%, #4ecdc4 50%, #45b7d1 100%);
                    min-height: 100vh;
                }
                .pokemon-card {
                    background: rgba(255, 255, 255, 0.95);
                    border-radius: 20px;
                    border: 3px solid #ffcc02;
                    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
                }
                .pokemon-title {
                    background: linear-gradient(45deg, #ffcc02, #ff6b6b);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                    font-weight: bold;
                    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
                }
                .pokemon-name {
                    color: #2c5aa0;
                    font-weight: bold;
                    text-transform: capitalize;
                }
                .form-check-input:checked {
                    background-color: #ff6b6b;
                    border-color: #ff6b6b;
                }
                .btn-pokemon {
                    background: linear-gradient(45deg, #ff6b6b, #ffcc02);
                    border: none;
                    color: white;
                    font-weight: bold;
                    border-radius: 25px;
                    transition: all 0.3s ease;
                }
                .btn-pokemon:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 5px 15px rgba(255, 107, 107, 0.4);
                    color: white;
                }
                .score-badge {
                    background: linear-gradient(45deg, #4ecdc4, #45b7d1) !important;
                    font-size: 1.1em;
                    padding: 8px 15px;
                }
            </style>
        </head>

        <body>

            <jsp:include page="../shared/menu.jsp" />

            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card pokemon-card shadow-lg">
                            <div class="card-body p-4">
                                <h2 class="card-title text-center mb-4 pokemon-title">
                                    🎯 Guess the Pokémon Ability! ⚡
                                </h2>
                                <form method="post" action="ServletJuego">
                                    <div class="mb-4 text-center p-3" style="background: rgba(255, 204, 2, 0.1); border-radius: 15px;">
                                        <h4>What is the ability of <span class="pokemon-name">${pokemonName}</span>? 🤔</h4>
                                    </div>
                                    <!-- send the pokemon name as a hidden field -->
                                    <input type="hidden" name="pokemonName" value="${pokemonName}">
                                    <div class="mb-3 d-flex justify-content-center">
                                        <div class="w-75">
                                            <c:forEach var="option" items="${options}">
                                                <div class="form-check mb-3 p-3" style="background: rgba(76, 205, 196, 0.1); border-radius: 10px; border-left: 4px solid #4ecdc4;">
                                                    <input class="form-check-input" type="radio" name="abilityGuess"
                                                        id="option${option}" value="${option}" required>
                                                    <label class="form-check-label fw-semibold"
                                                        for="option${option}" style="text-transform: capitalize;">${option}</label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-pokemon btn-lg w-100">🚀 Submit Answer</button>
                                </form>
                                <div class="mt-4 text-center p-3" style="background: rgba(69, 183, 209, 0.1); border-radius: 15px;">
                                    <span class="fw-bold">⭐ Score:</span> <span class="badge score-badge">${score}</span>
                                </div>
                                <form method="get" action="PuntajeServlet" class="mt-3">
                                    <button type="submit" class="btn btn-outline-info w-100" style="border-radius: 20px; border-width: 2px;">
                                        🏆 View High Scores
                                    </button>
                                </form>
                                <form method="get" action="../shared/LogoutServlet" class="mt-2">
                                    <button type="submit" class="btn btn-outline-danger w-100" style="border-radius: 20px; border-width: 2px;">
                                        🔒 Logout
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="../shared/footer.jsp" />
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>