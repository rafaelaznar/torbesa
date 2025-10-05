<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.ausiasmarch.shared.model.UserBean" %>
<%@ page import="net.ausiasmarch.codequest.model.ScoreDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    UserBean sessionUser = (UserBean) session.getAttribute("sessionUser");
    if (sessionUser == null) {
        response.sendRedirect("../shared/login.jsp");
        return;
    }
    
    ScoreDto userScore = (ScoreDto) request.getAttribute("userScore");
    List<ScoreDto> highScores = (List<ScoreDto>) request.getAttribute("highScores");
    List<ScoreDto> allScores = (List<ScoreDto>) request.getAttribute("allScores");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ranking - Juego de Programación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .ranking-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }
        .ranking-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            padding: 2rem;
            margin-bottom: 2rem;
        }
        .user-info {
            background: rgba(255,255,255,0.1);
            color: white;
            padding: 1rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            backdrop-filter: blur(10px);
        }
        .podium {
            display: flex;
            justify-content: center;
            align-items: end;
            margin-bottom: 3rem;
            gap: 1rem;
        }
        .podium-place {
            text-align: center;
            padding: 2rem 1rem;
            border-radius: 15px;
            color: white;
            min-width: 200px;
            position: relative;
        }
        .podium-1 {
            background: linear-gradient(45deg, #ffd700, #ffed4e);
            color: #333;
            height: 200px;
            order: 2;
        }
        .podium-2 {
            background: linear-gradient(45deg, #c0c0c0, #e8e8e8);
            color: #333;
            height: 160px;
            order: 1;
        }
        .podium-3 {
            background: linear-gradient(45deg, #cd7f32, #daa520);
            height: 120px;
            order: 3;
        }
        .podium-crown {
            position: absolute;
            top: -20px;
            left: 50%;
            transform: translateX(-50%);
            font-size: 2rem;
        }
        .leaderboard-table {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        .table-header {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
            padding: 1.5rem;
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
        .rank-1 { background: linear-gradient(45deg, #ffd700, #ffed4e); color: #333; }
        .rank-2 { background: linear-gradient(45deg, #c0c0c0, #e8e8e8); color: #333; }
        .rank-3 { background: linear-gradient(45deg, #cd7f32, #daa520); color: white; }
        .rank-other { background: linear-gradient(45deg, #667eea, #764ba2); color: white; }
        .user-row {
            background: linear-gradient(45deg, #e3f2fd, #f3e5f5);
            border-left: 4px solid #667eea;
        }
        .stats-card {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            color: white;
            border-radius: 15px;
            padding: 2rem;
            text-align: center;
            margin-bottom: 2rem;
        }
        .stats-number {
            font-size: 2.5rem;
            font-weight: bold;
        }
        .accuracy-bar {
            height: 8px;
            border-radius: 4px;
            background: rgba(255,255,255,0.3);
            overflow: hidden;
            margin-top: 0.5rem;
        }
        .accuracy-fill {
            height: 100%;
            background: white;
            border-radius: 4px;
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
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        .tab-content {
            margin-top: 2rem;
        }
        .nav-tabs .nav-link {
            border-radius: 15px 15px 0 0;
            font-weight: bold;
        }
        .nav-tabs .nav-link.active {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
            border-color: transparent;
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
                    <i class="fas fa-trophy"></i> Ranking de Programación
                </span>
            </h5>
        </div>

        <div class="ranking-container">
            <!-- Estadísticas del Usuario -->
            <div class="row">
                <div class="col-md-12">
                    <div class="stats-card">
                        <h4><i class="fas fa-user-circle"></i> Tus Estadísticas</h4>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="stats-number"><%= userScore != null ? userScore.getScore() : 0 %></div>
                                <p class="mb-0">Puntos</p>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-number"><%= userScore != null ? userScore.getTries() : 0 %></div>
                                <p class="mb-0">Intentos</p>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-number">
                                    <%= userScore != null && userScore.getTries() > 0 ? String.format("%.1f", (userScore.getScore() * 100.0 / userScore.getTries())) : "0" %>%
                                </div>
                                <p class="mb-0">Precisión</p>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-number">
                                    <% 
                                    int userRank = 0;
                                    if (allScores != null && userScore != null) {
                                        for (int i = 0; i < allScores.size(); i++) {
                                            if (allScores.get(i).getUsername().equals(sessionUser.getUsername())) {
                                                userRank = i + 1;
                                                break;
                                            }
                                        }
                                    }
                                    %>
                                    #<%= userRank > 0 ? userRank : "N/A" %>
                                </div>
                                <p class="mb-0">Posición</p>
                            </div>
                        </div>
                        <% if (userScore != null && userScore.getTries() > 0) { %>
                        <div class="accuracy-bar mt-3">
                            <div class="accuracy-fill" style="width: <%= (userScore.getScore() * 100 / userScore.getTries()) %>%"></div>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>

            <!-- Podio Top 3 -->
            <% if (highScores != null && highScores.size() >= 3) { %>
            <div class="ranking-card">
                <h3 class="text-center mb-4">
                    <i class="fas fa-crown text-warning"></i> Podio de Campeones
                </h3>
                <div class="podium">
                    <!-- Segundo Lugar -->
                    <div class="podium-place podium-2">
                        <div class="podium-crown">🥈</div>
                        <h5 class="fw-bold"><%= highScores.get(1).getUsername() %></h5>
                        <div class="fs-3 fw-bold"><%= highScores.get(1).getScore() %></div>
                        <small>puntos</small>
                        <div class="mt-2">
                            <small><%= highScores.get(1).getTries() %> intentos</small>
                        </div>
                    </div>
                    
                    <!-- Primer Lugar -->
                    <div class="podium-place podium-1">
                        <div class="podium-crown">👑</div>
                        <h4 class="fw-bold"><%= highScores.get(0).getUsername() %></h4>
                        <div class="fs-1 fw-bold"><%= highScores.get(0).getScore() %></div>
                        <small>puntos</small>
                        <div class="mt-2">
                            <small><%= highScores.get(0).getTries() %> intentos</small>
                        </div>
                    </div>
                    
                    <!-- Tercer Lugar -->
                    <div class="podium-place podium-3">
                        <div class="podium-crown">🥉</div>
                        <h5 class="fw-bold"><%= highScores.get(2).getUsername() %></h5>
                        <div class="fs-3 fw-bold"><%= highScores.get(2).getScore() %></div>
                        <small>puntos</small>
                        <div class="mt-2">
                            <small><%= highScores.get(2).getTries() %> intentos</small>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>

            <!-- Tabs para diferentes vistas -->
            <div class="ranking-card">
                <ul class="nav nav-tabs" id="rankingTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="top10-tab" data-bs-toggle="tab" data-bs-target="#top10" type="button" role="tab">
                            <i class="fas fa-trophy"></i> Top 10
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="all-tab" data-bs-toggle="tab" data-bs-target="#all" type="button" role="tab">
                            <i class="fas fa-list"></i> Todos los Jugadores
                        </button>
                    </li>
                </ul>

                <div class="tab-content" id="rankingTabsContent">
                    <!-- Top 10 -->
                    <div class="tab-pane fade show active" id="top10" role="tabpanel">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-header">
                                    <tr>
                                        <th>Posición</th>
                                        <th>Jugador</th>
                                        <th>Puntos</th>
                                        <th>Intentos</th>
                                        <th>Precisión</th>
                                        <th>Última Partida</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (highScores != null) {
                                        for (int i = 0; i < highScores.size(); i++) {
                                            ScoreDto score = highScores.get(i);
                                            String rankClass = i == 0 ? "rank-1" : i == 1 ? "rank-2" : i == 2 ? "rank-3" : "rank-other";
                                            boolean isCurrentUser = score.getUsername().equals(sessionUser.getUsername());
                                    %>
                                    <tr class="<%= isCurrentUser ? "user-row" : "" %>">
                                        <td>
                                            <div class="rank-badge <%= rankClass %>">
                                                <%= i + 1 %>
                                            </div>
                                        </td>
                                        <td>
                                            <strong class="<%= isCurrentUser ? "text-primary" : "" %>">
                                                <%= isCurrentUser ? "🎯 " : "" %><%= score.getUsername() %>
                                            </strong>
                                        </td>
                                        <td>
                                            <span class="badge bg-success fs-6"><%= score.getScore() %></span>
                                        </td>
                                        <td><%= score.getTries() %></td>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <span class="me-2"><%= String.format("%.1f", score.getTries() > 0 ? (score.getScore() * 100.0 / score.getTries()) : 0) %>%</span>
                                                <div class="accuracy-bar flex-grow-1" style="width: 60px; height: 6px;">
                                                    <div class="accuracy-fill" style="width: <%= score.getTries() > 0 ? (score.getScore() * 100 / score.getTries()) : 0 %>%; background: #28a745;"></div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <small class="text-muted">
                                                <%= score.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %>
                                            </small>
                                        </td>
                                    </tr>
                                    <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Todos los Jugadores -->
                    <div class="tab-pane fade" id="all" role="tabpanel">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-header">
                                    <tr>
                                        <th>Posición</th>
                                        <th>Jugador</th>
                                        <th>Puntos</th>
                                        <th>Intentos</th>
                                        <th>Precisión</th>
                                        <th>Última Partida</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (allScores != null) {
                                        for (int i = 0; i < allScores.size(); i++) {
                                            ScoreDto score = allScores.get(i);
                                            String rankClass = i == 0 ? "rank-1" : i == 1 ? "rank-2" : i == 2 ? "rank-3" : "rank-other";
                                            boolean isCurrentUser = score.getUsername().equals(sessionUser.getUsername());
                                    %>
                                    <tr class="<%= isCurrentUser ? "user-row" : "" %>">
                                        <td>
                                            <div class="rank-badge <%= rankClass %>">
                                                <%= i + 1 %>
                                            </div>
                                        </td>
                                        <td>
                                            <strong class="<%= isCurrentUser ? "text-primary" : "" %>">
                                                <%= isCurrentUser ? "🎯 " : "" %><%= score.getUsername() %>
                                            </strong>
                                        </td>
                                        <td>
                                            <span class="badge bg-success fs-6"><%= score.getScore() %></span>
                                        </td>
                                        <td><%= score.getTries() %></td>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <span class="me-2"><%= String.format("%.1f", score.getTries() > 0 ? (score.getScore() * 100.0 / score.getTries()) : 0) %>%</span>
                                                <div class="accuracy-bar flex-grow-1" style="width: 60px; height: 6px;">
                                                    <div class="accuracy-fill" style="width: <%= score.getTries() > 0 ? (score.getScore() * 100 / score.getTries()) : 0 %>%; background: #28a745;"></div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <small class="text-muted">
                                                <%= score.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %>
                                            </small>
                                        </td>
                                    </tr>
                                    <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Botones de Acción -->
            <div class="text-center">
                <a href="GameServlet" class="btn btn-primary btn-action">
                    <i class="fas fa-play"></i> Jugar Ahora
                </a>
                <a href="landing.jsp" class="btn btn-outline-secondary btn-action">
                    <i class="fas fa-home"></i> Inicio
                </a>
                <a href="../index.jsp" class="btn btn-outline-dark btn-action">
                    <i class="fas fa-arrow-left"></i> Menú Principal
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Auto-scroll al usuario actual en la tabla
        document.addEventListener('DOMContentLoaded', function() {
            const userRow = document.querySelector('.user-row');
            if (userRow) {
                userRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        });

        // Atajos de teclado
        document.addEventListener('keydown', function(e) {
            if (e.key === ' ' || e.key === 'Enter') {
                e.preventDefault();
                window.location.href = 'GameServlet';
            } else if (e.key === 'Escape') {
                window.location.href = 'landing.jsp';
            } else if (e.key === '1') {
                document.getElementById('top10-tab').click();
            } else if (e.key === '2') {
                document.getElementById('all-tab').click();
            }
        });

        console.log('⌨️ Atajos de teclado:');
        console.log('   Espacio/Enter - Jugar');
        console.log('   Escape - Volver al inicio');
        console.log('   1 - Ver Top 10');
        console.log('   2 - Ver todos los jugadores');
    </script>
</body>
</html>

