<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="net.ausiasmarch.shared.model.UserBean" %>
        <%@ page import="java.util.ArrayList" %>
            <% UserBean sessionUser=(UserBean) session.getAttribute("sessionUser"); if (sessionUser==null) {
                response.sendRedirect("../shared/login.jsp"); return; } String technology=(String)
                request.getAttribute("technology"); String technologyType=(String)
                request.getAttribute("technologyType"); String technologyCategory=(String)
                request.getAttribute("technologyCategory"); String technologyDifficulty=(String)
                request.getAttribute("technologyDifficulty"); ArrayList<String> options = (ArrayList<String>)
                    request.getAttribute("options");
                    Integer gameErrors = (Integer) request.getAttribute("gameErrors");
                    Integer remainingChances = (Integer) request.getAttribute("remainingChances");

                    if (gameErrors == null) gameErrors = 0;
                    if (remainingChances == null) remainingChances = 2;
                    %>
                    <!DOCTYPE html>
                    <html lang="es">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>CodeQuest - <%= technology != null ? technology : "Cargando..." %>
                        </title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                            rel="stylesheet">
                        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                            rel="stylesheet">
                        <style>
                            body {
                                background: #f8f9fa;
                                min-height: 100vh;
                                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            }

                            .game-container {
                                max-width: 800px;
                                margin: 0 auto;
                                padding: 2rem;
                            }

                            .game-card {
                                background: white;
                                border-radius: 10px;
                                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                                padding: 2rem;
                                text-align: center;
                                border: 1px solid #dee2e6;
                            }

                            .technology-name {
                                font-size: 2.5rem;
                                font-weight: bold;
                                color: #2c3e50;
                                margin-bottom: 1rem;
                            }

                            .technology-info {
                                display: flex;
                                justify-content: center;
                                gap: 1rem;
                                margin-bottom: 2rem;
                                flex-wrap: wrap;
                            }

                            .info-badge {
                                padding: 0.5rem 1rem;
                                border-radius: 20px;
                                font-weight: bold;
                                font-size: 0.9rem;
                            }

                            .type-lenguaje {
                                background-color: #3498db;
                                color: white;
                            }

                            .type-framework {
                                background-color: #e74c3c;
                                color: white;
                            }

                            .type-libreria {
                                background-color: #2ecc71;
                                color: white;
                            }

                            .type-herramienta {
                                background-color: #f39c12;
                                color: white;
                            }

                            .type-runtime {
                                background-color: #1abc9c;
                                color: white;
                            }

                            .type-default {
                                background-color: #95a5a6;
                                color: white;
                            }

                            .category-frontend {
                                background-color: #ff6b6b;
                                color: white;
                            }

                            .category-backend {
                                background-color: #4ecdc4;
                                color: white;
                            }

                            .category-fullstack {
                                background-color: #45b7d1;
                                color: white;
                            }

                            .category-devops {
                                background-color: #f9ca24;
                                color: white;
                            }

                            .category-mobile {
                                background-color: #6c5ce7;
                                color: white;
                            }

                            .category-seguridad {
                                background-color: #fd79a8;
                                color: white;
                            }

                            .category-default {
                                background-color: #74b9ff;
                                color: white;
                            }

                            .difficulty-facil {
                                background-color: #00b894;
                                color: white;
                            }

                            .difficulty-medio {
                                background-color: #fdcb6e;
                                color: white;
                            }

                            .difficulty-dificil {
                                background-color: #e17055;
                                color: white;
                            }

                            .question-text {
                                font-size: 1.5rem;
                                color: #2c3e50;
                                margin-bottom: 2rem;
                                font-weight: 500;
                            }

                            .option-btn {
                                width: 100%;
                                padding: 1rem;
                                margin-bottom: 1rem;
                                border: 1px solid #dee2e6;
                                border-radius: 5px;
                                background: #f8f9fa;
                                color: #2c3e50;
                                font-size: 1rem;
                                cursor: pointer;
                                text-align: left;
                            }

                            .option-btn:hover {
                                border-color: #007bff;
                                background: #e7f1ff;
                            }

                            .progress-container {
                                margin-bottom: 2rem;
                            }

                            .user-info {
                                background: #343a40;
                                color: white;
                                padding: 1rem;
                                border-radius: 5px;
                                margin-bottom: 2rem;
                            }

                            .nav-buttons {
                                margin-top: 2rem;
                            }

                            .btn-nav {
                                padding: 0.5rem 1.5rem;
                                border-radius: 5px;
                                font-weight: normal;
                                margin: 0.25rem;
                            }

                            .chances-indicator {
                                background: #f8f9fa;
                                border-radius: 5px;
                                padding: 1rem;
                                margin-bottom: 1.5rem;
                                border: 1px solid #dee2e6;
                            }

                            .heart-icon {
                                color: #e74c3c;
                                font-size: 1.2rem;
                                margin: 0 0.2rem;
                            }

                            .heart-lost {
                                color: #bdc3c7;
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
                                            <i class="fas fa-brain"></i> CodeQuest
                                        </span>
                                </h5>
                            </div>

                            <!-- Contenedor Principal del Juego -->
                            <div class="game-container">
                                <div class="game-card">

                                    <!-- Indicador de Oportunidades -->
                                    <div class="chances-indicator text-center">
                                        <h6 class="mb-2 text-muted">Oportunidades restantes</h6>
                                        <div>
                                            <% for (int i=0; i < 2; i++) { %>
                                                <i class="fas fa-heart heart-icon <%= (i < remainingChances) ? "" : "heart-lost" %>"></i>
                                                <% } %>
                                        </div>
                                        <small class="text-muted">Puedes equivocarte máximo 2 veces</small>
                                    </div>

                                    <!-- Nombre de la Tecnología -->
                                    <div class="technology-name">
                                        <i class="fas fa-microchip"></i>
                                        <%= technology %>
                                    </div>

                                    <!-- Información de la Tecnología -->
                                    <div class="technology-info">
                                        <span class="info-badge type-<%= technologyType.toLowerCase() %>">
                                            <i class="fas fa-tag"></i>
                                            <%= technologyType.substring(0,1).toUpperCase() +
                                                technologyType.substring(1) %>
                                        </span>
                                        <span class="info-badge category-<%= technologyCategory.toLowerCase() %>">
                                            <i class="fas fa-layer-group"></i>
                                            <%= technologyCategory.substring(0,1).toUpperCase() +
                                                technologyCategory.substring(1) %>
                                        </span>
                                        <span class="info-badge difficulty-<%= technologyDifficulty.toLowerCase() %>">
                                            <i class="fas fa-chart-line"></i>
                                            <%= technologyDifficulty.substring(0,1).toUpperCase() +
                                                technologyDifficulty.substring(1) %>
                                        </span>
                                    </div>

                                    <!-- Pregunta -->
                                    <div class="question-text">
                                        <i class="fas fa-lightbulb text-warning"></i>
                                        ¿Cuál es la descripción correcta de <strong>
                                            <%= technology %>
                                        </strong>?
                                        <br><small class="text-muted mt-2">Elige la opción que mejor describa esta
                                            tecnología</small>
                                    </div>

                                    <!-- Formulario con Opciones -->
                                    <form method="post" action="GameServlet">
                                        <input type="hidden" name="technology" value="<%= technology %>">

                                        <div class="options-container">
                                            <% if (options !=null) { for (int i=0; i < options.size(); i++) { String
                                                option=options.get(i); %>
                                                <button type="submit" name="descriptionGuess" value="<%= option %>"
                                                    class="option-btn">
                                                    <div class="d-flex align-items-start">
                                                        <span class="badge bg-primary me-3 mt-1">
                                                            <%= (char)('A' + i) %>
                                                        </span>
                                                        <span>
                                                            <%= option %>
                                                        </span>
                                                    </div>
                                                </button>
                                                <% } } %>
                                        </div>
                                    </form>

                                    <!-- Botones de Navegación -->
                                    <div class="nav-buttons">
                                        <a href="landing.jsp" class="btn btn-outline-secondary btn-nav">
                                            <i class="fas fa-home"></i> Inicio
                                        </a>
                                        <a href="ScoreServlet" class="btn btn-outline-info btn-nav">
                                            <i class="fas fa-trophy"></i> Puntuaciones
                                        </a>
                                        <a href="../index.jsp" class="btn btn-outline-dark btn-nav">
                                            <i class="fas fa-arrow-left"></i> Menú Principal
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <script
                            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                    </body>

                    </html>