<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="net.ausiasmarch.shared.model.UserBean" %>
        <%@ page import="net.ausiasmarch.codequest.model.ScoreDto" %>
            <%@ page import="java.util.List" %>
                <% UserBean sessionUser=(UserBean) session.getAttribute("sessionUser"); if (sessionUser==null) {
                    response.sendRedirect("../shared/login.jsp"); return; } String technology=(String)
                    request.getAttribute("technology"); String correctDescription=(String)
                    request.getAttribute("correctDescription"); String descriptionGuess=(String)
                    request.getAttribute("descriptionGuess"); String message=(String) request.getAttribute("message");
                    Boolean isCorrect=(Boolean) request.getAttribute("isCorrect"); ScoreDto userScore=(ScoreDto)
                    request.getAttribute("userScore"); List<ScoreDto> highScores = (List<ScoreDto>)
                        request.getAttribute("highScores");
                        Integer gameErrors = (Integer) request.getAttribute("gameErrors");
                        Integer remainingChances = (Integer) request.getAttribute("remainingChances");

                        if (gameErrors == null) gameErrors = 0;
                        if (remainingChances == null) remainingChances = 2;

                        boolean gameOver = gameErrors >= 2;
                        %>
                        <!DOCTYPE html>
                        <html lang="es">

                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Resultado - CodeQuest</title>
                            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                                rel="stylesheet">
                            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                                rel="stylesheet">
                            <style>
                                body {
                                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                    min-height: 100vh;
                                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                }

                                .result-container {
                                    max-width: 1000px;
                                    margin: 0 auto;
                                    padding: 2rem;
                                }

                                .result-card {
                                    background: white;
                                    border-radius: 20px;
                                    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                                    padding: 3rem;
                                    margin-bottom: 2rem;
                                }

                                .result-header {
                                    text-align: center;
                                    margin-bottom: 2rem;
                                }

                                .result-correct {
                                    color: #27ae60;
                                    background: linear-gradient(45deg, #2ecc71, #27ae60);
                                    -webkit-background-clip: text;
                                    -webkit-text-fill-color: transparent;
                                    background-clip: text;
                                }

                                .result-incorrect {
                                    color: #e74c3c;
                                    background: linear-gradient(45deg, #e74c3c, #c0392b);
                                    -webkit-background-clip: text;
                                    -webkit-text-fill-color: transparent;
                                    background-clip: text;
                                }

                                .technology-display {
                                    font-size: 2.5rem;
                                    font-weight: bold;
                                    color: #2c3e50;
                                    margin-bottom: 1rem;
                                }

                                .description-box {
                                    background: #f8f9fa;
                                    border-left: 4px solid #667eea;
                                    padding: 1.5rem;
                                    border-radius: 10px;
                                    margin: 1rem 0;
                                }

                                .description-correct {
                                    border-left-color: #27ae60;
                                    background: #d5f4e6;
                                }

                                .description-incorrect {
                                    border-left-color: #e74c3c;
                                    background: #fdf2f2;
                                }

                                .score-card {
                                    background: linear-gradient(45deg, #4facfe, #00f2fe);
                                    color: white;
                                    border-radius: 15px;
                                    padding: 2rem;
                                    text-align: center;
                                    margin-bottom: 2rem;
                                }

                                .score-number {
                                    font-size: 3rem;
                                    font-weight: bold;
                                }

                                .leaderboard-card {
                                    background: white;
                                    border-radius: 15px;
                                    padding: 2rem;
                                    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                                }

                                .leaderboard-item {
                                    display: flex;
                                    align-items: center;
                                    padding: 1rem;
                                    border-radius: 10px;
                                    margin-bottom: 0.5rem;
                                    transition: all 0.3s ease;
                                }

                                .leaderboard-item:hover {
                                    background: #f8f9fa;
                                    transform: translateX(5px);
                                }

                                .rank-badge {
                                    width: 40px;
                                    height: 40px;
                                    border-radius: 50%;
                                    display: flex;
                                    align-items: center;
                                    justify-content: center;
                                    font-weight: bold;
                                    margin-right: 1rem;
                                }

                                .rank-1 {
                                    background: linear-gradient(45deg, #ffd700, #ffed4e);
                                    color: #333;
                                }

                                .rank-2 {
                                    background: linear-gradient(45deg, #c0c0c0, #e8e8e8);
                                    color: #333;
                                }

                                .rank-3 {
                                    background: linear-gradient(45deg, #cd7f32, #daa520);
                                    color: white;
                                }

                                .rank-other {
                                    background: linear-gradient(45deg, #667eea, #764ba2);
                                    color: white;
                                }

                                .btn-action {
                                    padding: 1rem 2rem;
                                    border-radius: 25px;
                                    font-weight: bold;
                                    margin: 0.5rem;
                                    transition: all 0.3s ease;
                                }

                                .btn-action:hover {
                                    transform: translateY(-2px);
                                    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
                                }

                                .user-info {
                                    background: rgba(255, 255, 255, 0.1);
                                    color: white;
                                    padding: 1rem;
                                    border-radius: 10px;
                                    margin-bottom: 2rem;
                                    backdrop-filter: blur(10px);
                                }

                                .celebration {
                                    animation: celebration 0.6s ease-in-out;
                                }

                                @keyframes celebration {
                                    0% {
                                        transform: scale(1);
                                    }

                                    50% {
                                        transform: scale(1.05);
                                    }

                                    100% {
                                        transform: scale(1);
                                    }
                                }

                                .accuracy-bar {
                                    height: 10px;
                                    border-radius: 5px;
                                    background: #e9ecef;
                                    overflow: hidden;
                                    margin-top: 0.5rem;
                                }

                                .accuracy-fill {
                                    height: 100%;
                                    background: linear-gradient(45deg, #4facfe, #00f2fe);
                                    transition: width 0.8s ease;
                                }
                            </style>
                        </head>

                        <body>
                            <div class="container-fluid">
                                <!-- Información del Usuario -->
                                <div class="user-info text-center">
                                    <h5 class="mb-0">
                                        <i class="fas fa-user"></i> Jugador: <%= sessionUser.getUsername() %>
                                            <span class="ms-4">
                                                <i class="fas fa-gamepad"></i> Resultado del Juego
                                            </span>
                                    </h5>
                                </div>

                                <div class="result-container">
                                    <!-- Resultado Principal -->
                                    <div class="result-card <%= isCorrect != null && isCorrect ? " celebration" : "" %>
                                        ">
                                        <div class="result-header">
                                            <div class="technology-display">
                                                <i class="fas fa-code"></i>
                                                <%= technology %>
                                            </div>
                                            <h2 class="<%= isCorrect != null && isCorrect ? "result-correct" : "result-incorrect" %>">
                                                <i class="fas fa-<%= isCorrect != null && isCorrect ? "check-circle" : "times-circle" %>"></i>
                                                <%= message %>
                                            </h2>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6">
                                                <h5><i class="fas fa-lightbulb"></i> Descripción Correcta:</h5>
                                                <div class="description-box description-correct">
                                                    <strong>
                                                        <%= correctDescription %>
                                                    </strong>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <h5><i class="fas fa-user-edit"></i> Tu Respuesta:</h5>
                                                <div class="description-box <%= isCorrect != null && isCorrect ? "description-correct" : "description-incorrect" %>">
                                                    <%= descriptionGuess %>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Botones de Acción -->
                                        <div class="text-center mt-4">
                                            <% if (gameOver) { %>
                                                <!-- Juego terminado - ofrecer reiniciar -->
                                                                                            <div class="d-flex gap-2 justify-content-center">
                                                <a href="GameServlet" class="btn btn-warning btn-action">
                                                    <i class="fas fa-redo me-1"></i>Jugar de Nuevo
                                                </a>
                                                <a href="landing.jsp" class="btn btn-outline-secondary btn-action">
                                                    <i class="fas fa-home"></i> Inicio
                                                </a>
                                                <a href="../index.jsp" class="btn btn-outline-dark btn-action">
                                                    <i class="fas fa-arrow-left"></i> Menú Principal
                                                </a>
                                                <% } else { %>
                                                    <!-- Puede seguir jugando -->
                                                    <a href="GameServlet" class="btn btn-success btn-action">
                                                        <i class="fas fa-forward"></i> Continuar Jugando
                                                    </a>
                                                    <a href="GameServlet"
                                                        class="btn btn-outline-warning btn-action">
                                                        <i class="fas fa-redo"></i> Reiniciar Juego
                                                    </a>
                                                    <a href="landing.jsp" class="btn btn-outline-secondary btn-action">
                                                        <i class="fas fa-home"></i> Inicio
                                                    </a>
                                                    <a href="../index.jsp" class="btn btn-outline-dark btn-action">
                                                        <i class="fas fa-arrow-left"></i> Menú Principal
                                                    </a>
                                                    <% } %>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <!-- Puntuación del Usuario -->
                                        <div class="col-md-4">
                                            <div class="score-card">
                                                <h4><i class="fas fa-user"></i> Tu Puntuación</h4>
                                                <% if (userScore !=null) { %>
                                                    <div class="score-number">
                                                        <%= userScore.getScore() %>
                                                    </div>
                                                    <p class="mb-2">de <%= userScore.getTries() %> intentos</p>
                                                    <div class="accuracy-bar">
                                                        <div class="accuracy-fill"
                                                            style="width: <%= userScore.getTries() > 0 ? (userScore.getScore() * 100 / userScore.getTries()) : 0 %>%">
                                                        </div>
                                                    </div>
                                                    <small>Precisión: <%= userScore.getTries()> 0 ?
                                                            String.format("%.1f", (userScore.getScore() * 100.0 /
                                                            userScore.getTries())) : "0" %>%</small>
                                                    <% } else { %>
                                                        <div class="score-number">0</div>
                                                        <p class="mb-0">¡Primera partida!</p>
                                                        <% } %>
                                            </div>
                                        </div>

                                        <!-- Top 10 Puntuaciones -->
                                        <div class="col-md-8">
                                            <div class="leaderboard-card">
                                                <h4 class="mb-4">
                                                    <i class="fas fa-trophy text-warning"></i> Top 10 Jugadores
                                                </h4>
                                                <% if (highScores !=null && !highScores.isEmpty()) { %>
                                                    <% for (int i=0; i < highScores.size(); i++) { ScoreDto
                                                        score=highScores.get(i); String rankClass=i==0 ? "rank-1" : i==1
                                                        ? "rank-2" : i==2 ? "rank-3" : "rank-other" ; boolean
                                                        isCurrentUser=score.getUsername().equals(sessionUser.getUsername());
                                                        %>
                                                        <div class="leaderboard-item <%= isCurrentUser ? "bg-light" : "" %>">
                                                            <div class="rank-badge <%= rankClass %>">
                                                                <%= i + 1 %>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div
                                                                    class="d-flex justify-content-between align-items-center">
                                                                    <span class="fw-bold <%= isCurrentUser ? "text-primary" : "" %>">
                                                                        <%= isCurrentUser ? "🎯 " : "" %>
                                                                            <%= score.getUsername() %>
                                                                    </span>
                                                                    <div class="text-end">
                                                                        <span class="badge bg-success fs-6">
                                                                            <%= score.getScore() %> puntos
                                                                        </span>
                                                                        <small class="text-muted d-block">
                                                                            <%= score.getTries() %> intentos
                                                                                (<%= String.format("%.1f",
                                                                                    score.getTries()> 0 ?
                                                                                    (score.getScore() * 100.0 /
                                                                                    score.getTries()) : 0) %>%)
                                                                        </small>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <% } %>
                                                            <% } else { %>
                                                                <div class="text-center text-muted">
                                                                    <i class="fas fa-trophy fa-3x mb-3"></i>
                                                                    <p>¡Sé el primero en aparecer en el ranking!</p>
                                                                </div>
                                                                <% } %>

                                                                    <div class="text-center mt-3">
                                                                        <a href="ScoreServlet"
                                                                            class="btn btn-outline-primary">
                                                                            <i class="fas fa-list"></i> Ver Ranking
                                                                            Completo
                                                                        </a>
                                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <script
                                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                            <script>
                                // Animación de la barra de precisión
                                document.addEventListener('DOMContentLoaded', function () {
                                    const accuracyFill = document.querySelector('.accuracy-fill');
                                    if (accuracyFill) {
                                        const width = accuracyFill.style.width;
                                        accuracyFill.style.width = '0%';
                                        setTimeout(() => {
                                            accuracyFill.style.width = width;
                                        }, 500);
                                    }
                                });

        // Efecto de confeti para respuestas correctas
        <% if (isCorrect != null && isCorrect) { %>
                                    function createConfetti() {
                                        const colors = ['#ff6b6b', '#4ecdc4', '#45b7d1', '#f9ca24', '#6c5ce7'];
                                        for (let i = 0; i < 50; i++) {
                                            const confetti = document.createElement('div');
                                            confetti.style.position = 'fixed';
                                            confetti.style.left = Math.random() * 100 + 'vw';
                                            confetti.style.top = '-10px';
                                            confetti.style.width = '10px';
                                            confetti.style.height = '10px';
                                            confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
                                            confetti.style.borderRadius = '50%';
                                            confetti.style.pointerEvents = 'none';
                                            confetti.style.zIndex = '9999';
                                            confetti.style.animation = `fall ${Math.random() * 3 + 2}s linear forwards`;
                                            document.body.appendChild(confetti);

                                            setTimeout(() => {
                                                confetti.remove();
                                            }, 5000);
                                        }
                                    }

                                    // Añadir CSS para la animación de caída
                                    const style = document.createElement('style');
                                    style.textContent = `
            @keyframes fall {
                to {
                    transform: translateY(100vh) rotate(360deg);
                }
            }
        `;
                                    document.head.appendChild(style);

                                    // Ejecutar confeti después de un pequeño retraso
                                    setTimeout(createConfetti, 500);
        <% } %>

                                    // Atajos de teclado
                                    document.addEventListener('keydown', function (e) {
                                        if (e.key === ' ' || e.key === 'Enter') {
                                            e.preventDefault();
                                            window.location.href = 'GameServlet';
                                        } else if (e.key === 'Escape') {
                                            window.location.href = 'landing.jsp';
                                        }
                                    });

                                console.log('⌨️ Atajos de teclado:');
                                console.log('   Espacio/Enter - Jugar de nuevo');
                                console.log('   Escape - Volver al inicio');
                            </script>
                        </body>

                        </html>