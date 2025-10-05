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
    
    List<ScoreDto> allScores = (List<ScoreDto>) request.getAttribute("allScores");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabla de Clasificación - Juego de Programación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .leaderboard-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }
        .leaderboard-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .user-info {
            background: rgba(255,255,255,0.1);
            color: white;
            padding: 1rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            backdrop-filter: blur(10px);
        }
        .leaderboard-header {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
            padding: 2rem;
            text-align: center;
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
        .table-row {
            transition: all 0.3s ease;
        }
        .table-row:hover {
            background: #f8f9fa;
            transform: translateX(5px);
        }
        .accuracy-bar {
            height: 8px;
            border-radius: 4px;
            background: #e9ecef;
            overflow: hidden;
        }
        .accuracy-fill {
            height: 100%;
            background: linear-gradient(45deg, #28a745, #20c997);
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
        .search-box {
            max-width: 400px;
            margin: 0 auto 2rem;
        }
        .filter-buttons {
            text-align: center;
            margin-bottom: 2rem;
        }
        .filter-btn {
            margin: 0.25rem;
            border-radius: 20px;
        }
        .stats-summary {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            color: white;
            padding: 1.5rem;
            border-radius: 15px;
            margin-bottom: 2rem;
        }
        .pagination-container {
            display: flex;
            justify-content: center;
            margin-top: 2rem;
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
                    <i class="fas fa-list"></i> Tabla de Clasificación Completa
                </span>
            </h5>
        </div>

        <div class="leaderboard-container">
            <div class="leaderboard-card">
                <!-- Header -->
                <div class="leaderboard-header">
                    <h2 class="mb-3">
                        <i class="fas fa-trophy"></i> Tabla de Clasificación General
                    </h2>
                    <p class="mb-0">Todos los jugadores del Juego de Programación</p>
                </div>

                <div class="p-4">
                    <!-- Resumen de Estadísticas -->
                    <div class="stats-summary">
                        <div class="row text-center">
                            <div class="col-md-3">
                                <h4><%= allScores != null ? allScores.size() : 0 %></h4>
                                <p class="mb-0">Jugadores Totales</p>
                            </div>
                            <div class="col-md-3">
                                <h4>
                                    <%= allScores != null && !allScores.isEmpty() ? allScores.get(0).getScore() : 0 %>
                                </h4>
                                <p class="mb-0">Puntuación Máxima</p>
                            </div>
                            <div class="col-md-3">
                                <h4>
                                    <% 
                                    double avgScore = 0;
                                    if (allScores != null && !allScores.isEmpty()) {
                                        int totalScore = 0;
                                        for (ScoreDto score : allScores) {
                                            totalScore += score.getScore();
                                        }
                                        avgScore = (double) totalScore / allScores.size();
                                    }
                                    %>
                                    <%= String.format("%.1f", avgScore) %>
                                </h4>
                                <p class="mb-0">Puntuación Media</p>
                            </div>
                            <div class="col-md-3">
                                <h4>
                                    <% 
                                    double avgAccuracy = 0;
                                    if (allScores != null && !allScores.isEmpty()) {
                                        double totalAccuracy = 0;
                                        for (ScoreDto score : allScores) {
                                            if (score.getTries() > 0) {
                                                totalAccuracy += (score.getScore() * 100.0 / score.getTries());
                                            }
                                        }
                                        avgAccuracy = totalAccuracy / allScores.size();
                                    }
                                    %>
                                    <%= String.format("%.1f", avgAccuracy) %>%
                                </h4>
                                <p class="mb-0">Precisión Media</p>
                            </div>
                        </div>
                    </div>

                    <!-- Búsqueda -->
                    <div class="search-box">
                        <div class="input-group">
                            <span class="input-group-text">
                                <i class="fas fa-search"></i>
                            </span>
                            <input type="text" class="form-control" id="searchInput" placeholder="Buscar jugador...">
                        </div>
                    </div>

                    <!-- Filtros -->
                    <div class="filter-buttons">
                        <button class="btn btn-outline-primary filter-btn active" data-filter="all">
                            <i class="fas fa-users"></i> Todos
                        </button>
                        <button class="btn btn-outline-success filter-btn" data-filter="top10">
                            <i class="fas fa-trophy"></i> Top 10
                        </button>
                        <button class="btn btn-outline-warning filter-btn" data-filter="high-accuracy">
                            <i class="fas fa-bullseye"></i> Alta Precisión (>80%)
                        </button>
                        <button class="btn btn-outline-info filter-btn" data-filter="active">
                            <i class="fas fa-clock"></i> Activos (última semana)
                        </button>
                    </div>

                    <!-- Tabla de Clasificación -->
                    <div class="table-responsive">
                        <table class="table table-hover" id="leaderboardTable">
                            <thead class="table-dark">
                                <tr>
                                    <th>
                                        <i class="fas fa-sort"></i> Posición
                                    </th>
                                    <th>
                                        <i class="fas fa-user"></i> Jugador
                                    </th>
                                    <th>
                                        <i class="fas fa-star"></i> Puntos
                                    </th>
                                    <th>
                                        <i class="fas fa-play"></i> Intentos
                                    </th>
                                    <th>
                                        <i class="fas fa-bullseye"></i> Precisión
                                    </th>
                                    <th>
                                        <i class="fas fa-calendar"></i> Última Partida
                                    </th>
                                    <th>
                                        <i class="fas fa-chart-line"></i> Progreso
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="leaderboardBody">
                                <% if (allScores != null) {
                                    for (int i = 0; i < allScores.size(); i++) {
                                        ScoreDto score = allScores.get(i);
                                        String rankClass = i == 0 ? "rank-1" : i == 1 ? "rank-2" : i == 2 ? "rank-3" : "rank-other";
                                        boolean isCurrentUser = score.getUsername().equals(sessionUser.getUsername());
                                        double accuracy = score.getTries() > 0 ? (score.getScore() * 100.0 / score.getTries()) : 0;
                                %>
                                <tr class="table-row <%= isCurrentUser ? "user-row" : "" %>" 
                                    data-username="<%= score.getUsername().toLowerCase() %>"
                                    data-rank="<%= i + 1 %>"
                                    data-accuracy="<%= accuracy %>"
                                    data-timestamp="<%= score.getTimestamp().toString() %>">
                                    <td>
                                        <div class="rank-badge <%= rankClass %>">
                                            <%= i + 1 %>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <% if (i == 0) { %>
                                                <i class="fas fa-crown text-warning me-2"></i>
                                            <% } else if (i == 1) { %>
                                                <i class="fas fa-medal text-secondary me-2"></i>
                                            <% } else if (i == 2) { %>
                                                <i class="fas fa-award text-warning me-2"></i>
                                            <% } %>
                                            <strong class="<%= isCurrentUser ? "text-primary" : "" %>">
                                                <%= isCurrentUser ? "🎯 " : "" %><%= score.getUsername() %>
                                            </strong>
                                        </div>
                                    </td>
                                    <td>
                                        <span class="badge bg-success fs-6"><%= score.getScore() %></span>
                                    </td>
                                    <td>
                                        <span class="badge bg-info"><%= score.getTries() %></span>
                                    </td>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <span class="me-2 fw-bold">
                                                <%= String.format("%.1f", accuracy) %>%
                                            </span>
                                            <div class="accuracy-bar flex-grow-1" style="width: 60px;">
                                                <div class="accuracy-fill" style="width: <%= accuracy %>%"></div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <small class="text-muted">
                                            <%= score.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
                                            <br>
                                            <span class="text-primary">
                                                <%= score.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")) %>
                                            </span>
                                        </small>
                                    </td>
                                    <td>
                                        <div class="progress" style="height: 20px;">
                                            <div class="progress-bar bg-gradient" 
                                                 style="width: <%= Math.min(100, (score.getScore() * 100.0 / Math.max(1, allScores.get(0).getScore()))) %>%"
                                                 role="progressbar">
                                                <%= String.format("%.0f", Math.min(100, (score.getScore() * 100.0 / Math.max(1, allScores.get(0).getScore())))) %>%
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <% } } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Paginación -->
                    <div class="pagination-container">
                        <nav>
                            <ul class="pagination" id="pagination">
                                <!-- Se genera dinámicamente con JavaScript -->
                            </ul>
                        </nav>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="text-center mt-4">
                        <a href="GameServlet" class="btn btn-primary btn-action">
                            <i class="fas fa-play"></i> Jugar Ahora
                        </a>
                        <a href="ScoreServlet" class="btn btn-outline-info btn-action">
                            <i class="fas fa-trophy"></i> Top 10
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
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Variables globales
        let currentFilter = 'all';
        let currentPage = 1;
        const itemsPerPage = 20;
        let filteredRows = [];

        // Inicialización
        document.addEventListener('DOMContentLoaded', function() {
            initializeTable();
            setupEventListeners();
            
            // Auto-scroll al usuario actual
            const userRow = document.querySelector('.user-row');
            if (userRow) {
                setTimeout(() => {
                    userRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }, 500);
            }
        });

        function initializeTable() {
            const rows = Array.from(document.querySelectorAll('#leaderboardBody tr'));
            filteredRows = rows;
            updatePagination();
            showPage(1);
        }

        function setupEventListeners() {
            // Búsqueda
            document.getElementById('searchInput').addEventListener('input', function() {
                filterTable();
            });

            // Filtros
            document.querySelectorAll('.filter-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                    this.classList.add('active');
                    currentFilter = this.dataset.filter;
                    filterTable();
                });
            });

            // Atajos de teclado
            document.addEventListener('keydown', function(e) {
                if (e.key === ' ' || e.key === 'Enter') {
                    e.preventDefault();
                    window.location.href = 'GameServlet';
                } else if (e.key === 'Escape') {
                    window.location.href = 'landing.jsp';
                } else if (e.key === '/') {
                    e.preventDefault();
                    document.getElementById('searchInput').focus();
                }
            });
        }

        function filterTable() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const allRows = Array.from(document.querySelectorAll('#leaderboardBody tr'));
            
            filteredRows = allRows.filter(row => {
                const username = row.dataset.username;
                const rank = parseInt(row.dataset.rank);
                const accuracy = parseFloat(row.dataset.accuracy);
                const timestamp = new Date(row.dataset.timestamp);
                const oneWeekAgo = new Date();
                oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

                // Filtro de búsqueda
                if (searchTerm && !username.includes(searchTerm)) {
                    return false;
                }

                // Filtros por categoría
                switch (currentFilter) {
                    case 'top10':
                        return rank <= 10;
                    case 'high-accuracy':
                        return accuracy > 80;
                    case 'active':
                        return timestamp > oneWeekAgo;
                    default:
                        return true;
                }
            });

            currentPage = 1;
            updatePagination();
            showPage(1);
        }

        function showPage(page) {
            const startIndex = (page - 1) * itemsPerPage;
            const endIndex = startIndex + itemsPerPage;
            
            // Ocultar todas las filas
            document.querySelectorAll('#leaderboardBody tr').forEach(row => {
                row.style.display = 'none';
            });

            // Mostrar filas de la página actual
            filteredRows.slice(startIndex, endIndex).forEach(row => {
                row.style.display = '';
            });

            currentPage = page;
            updatePaginationButtons();
        }

        function updatePagination() {
            const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
            const pagination = document.getElementById('pagination');
            pagination.innerHTML = '';

            if (totalPages <= 1) return;

            // Botón anterior
            const prevBtn = createPaginationButton('‹', currentPage > 1, () => showPage(currentPage - 1));
            pagination.appendChild(prevBtn);

            // Botones de página
            for (let i = 1; i <= totalPages; i++) {
                if (i === 1 || i === totalPages || (i >= currentPage - 2 && i <= currentPage + 2)) {
                    const pageBtn = createPaginationButton(i, true, () => showPage(i));
                    if (i === currentPage) {
                        pageBtn.classList.add('active');
                    }
                    pagination.appendChild(pageBtn);
                } else if (i === currentPage - 3 || i === currentPage + 3) {
                    const ellipsis = document.createElement('li');
                    ellipsis.className = 'page-item disabled';
                    ellipsis.innerHTML = '<span class="page-link">...</span>';
                    pagination.appendChild(ellipsis);
                }
            }

            // Botón siguiente
            const nextBtn = createPaginationButton('›', currentPage < totalPages, () => showPage(currentPage + 1));
            pagination.appendChild(nextBtn);
        }

        function createPaginationButton(text, enabled, onClick) {
            const li = document.createElement('li');
            li.className = `page-item ${enabled ? '' : 'disabled'}`;
            
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = text;
            
            if (enabled) {
                a.addEventListener('click', function(e) {
                    e.preventDefault();
                    onClick();
                });
            }
            
            li.appendChild(a);
            return li;
        }

        function updatePaginationButtons() {
            document.querySelectorAll('#pagination .page-item').forEach(item => {
                item.classList.remove('active');
            });
            
            const pageButtons = document.querySelectorAll('#pagination .page-link');
            pageButtons.forEach(btn => {
                if (btn.textContent == currentPage) {
                    btn.parentElement.classList.add('active');
                }
            });
        }

        // Mostrar ayuda de atajos de teclado
        console.log('⌨️ Atajos de teclado disponibles:');
        console.log('   Espacio/Enter - Jugar');
        console.log('   Escape - Volver al inicio');
        console.log('   / - Enfocar búsqueda');
    </script>
</body>
</html>

