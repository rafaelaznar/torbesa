<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Game Results - Pokémon Quiz</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
                    rel="stylesheet">
                <style>
                    body {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                    }
                    .results-title {
                        background: linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1);
                        -webkit-background-clip: text;
                        background-clip: text;
                        -webkit-text-fill-color: transparent;
                        font-weight: bold;
                    }
                    .pokemon-card {
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        border: 3px solid #ffcc02;
                        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
                    }
                    .answer-header {
                        background: linear-gradient(45deg, #4ecdc4, #45b7d1);
                        color: white;
                        border-radius: 15px 15px 0 0;
                    }
                    .score-header {
                        background: linear-gradient(45deg, #ff6b6b, #ffcc02);
                        color: white;
                        border-radius: 15px 15px 0 0;
                    }
                    .btn-play-again {
                        background: linear-gradient(45deg, #ff6b6b, #ffcc02);
                        border: none;
                        color: white;
                        font-weight: bold;
                        border-radius: 25px;
                        transition: all 0.3s ease;
                    }
                    .btn-play-again:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(255, 107, 107, 0.4);
                        color: white;
                    }
                    .pokemon-name {
                        background: linear-gradient(45deg, #2c5aa0, #4ecdc4);
                        color: white;
                        text-transform: capitalize;
                    }
                    .ability-correct {
                        background: linear-gradient(45deg, #28a745, #20c997);
                        color: white;
                    }
                    .ability-guess {
                        background: linear-gradient(45deg, #ffc107, #fd7e14);
                        color: white;
                    }
                </style>
            </head>

            <body>

                <jsp:include page="../shared/menu.jsp" />

                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-10">

                            <!-- Header -->
                            <div class="text-center mb-4">
                                <h1 class="display-4 results-title">
                                    🎯 Game Results ⚡
                                </h1>
                            </div>

                            <!-- Message Alert -->
                            <c:if test="${not empty message}">
                                <div class="alert alert-info alert-dismissible fade show pokemon-card" role="alert">
                                    <i class="bi bi-info-circle-fill"></i> ${message}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            </c:if>

                            <div class="row g-4">
                                <!-- Your Answer Card -->
                                <div class="col-md-6">
                                    <div class="card pokemon-card shadow-sm border-0 h-100">
                                        <div class="card-header answer-header">
                                            <h5 class="card-title mb-0">
                                                <i class="bi bi-question-circle-fill"></i> 🎯 Your Answer
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <h5>
                                                    <strong class="text-muted">🎮 Pokémon:</strong>
                                                    <span class="badge pokemon-name ms-2">${pokemonName}</span>
                                                </h5>
                                            </div>
                                            <div class="mb-3">
                                                <h5>
                                                    <strong class="text-muted">💭 Your Guess:</strong>
                                                    <span class="badge ability-guess ms-2">${abilityGuess}</span>
                                                </h5>
                                            </div>
                                            <div class="mb-0">
                                                <h5>
                                                    <strong class="text-muted">✅ Correct Ability:</strong>
                                                    <span class="badge ability-correct ms-2">${correctAbility}</span>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Your Score Card -->
                                <div class="col-md-6">
                                    <div class="card pokemon-card shadow-sm border-0 h-100">
                                        <div class="card-header score-header">
                                            <h5 class="card-title mb-0">
                                                <i class="bi bi-star-fill"></i> ⭐ Your Performance
                                            </h5>
                                        </div>
                                        <div class="card-body">
                                            <div class="row text-center">
                                                <div class="col-4">
                                                    <div class="border-end">
                                                        <h3 class="text-success mb-1">${userScore.score}</h3>
                                                        <small class="text-muted">Score</small>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div class="border-end">
                                                        <h3 class="text-info mb-1">${userScore.tries}</h3>
                                                        <small class="text-muted">Tries</small>
                                                    </div>
                                                </div>
                                                <div class="col-4">
                                                    <div>
                                                        <h6 class="text-primary mb-1 h3">
                                                            <fmt:formatNumber
                                                                value="${userScore.score / userScore.tries * 100}"
                                                                maxFractionDigits="1" />%
                                                        </h6>
                                                        <small class="text-muted">Accuracy</small>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr>
                                            <div class="text-center">
                                                <small class="text-muted h5">
                                                    <i class="bi bi-calendar-event"></i>
                                                    <fmt:formatDate value="${userScore.timestampAsDate}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Action Buttons -->
                            <div class="text-center mt-4">
                                <form method="get" action="ServletJuego" class="d-inline me-3">
                                    <button type="submit" class="btn btn-play-again btn-lg">
                                        <i class="bi bi-play-fill"></i> 🎮 Play Again
                                    </button>
                                </form>

                                <form method="get" action="../shared/LogoutServlet" class="d-inline">
                                    <button type="submit" class="btn btn-outline-danger btn-lg" style="border-radius: 25px; border-width: 2px;">
                                        <i class="bi bi-box-arrow-right"></i> 🔒 Logout
                                    </button>
                                </form>
                            </div>

                            <!-- High Scores Table -->
                            <jsp:include page="ranking.jsp" />


                        </div>
                    </div>
                </div>
                <jsp:include page="../shared/footer.jsp" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>