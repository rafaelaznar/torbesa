<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="net.ausiasmarch.shared.model.UserBean" %>
        <% UserBean sessionUser=(UserBean) session.getAttribute("sessionUser"); if (sessionUser==null) {
            response.sendRedirect("../shared/login.jsp"); return; } %>
            <!DOCTYPE html>
            <html lang="es">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>CodeQuest - Desafío de Programación</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css"
                    rel="stylesheet">
                <style>
                    /* Sección principal con gradiente bonito */
                    .hero-section {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        padding: 60px 0;
                        min-height: 50vh;
                        display: flex;
                        align-items: center;
                    }

                    .hero-title {
                        font-size: 3rem;
                        font-weight: 700;
                        margin-bottom: 1rem;
                        text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
                    }

                    .hero-subtitle {
                        font-size: 1.2rem;
                        opacity: 0.95;
                        margin-bottom: 2rem;
                    }

                    /* Tarjetas con efectos elegantes */
                    .game-card {
                        background: white;
                        border: none;
                        border-radius: 15px;
                        padding: 2rem;
                        height: 100%;
                        transition: all 0.3s ease;
                        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
                    }

                    .game-card:hover {
                        transform: translateY(-10px);
                        box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
                    }

                    .card-icon {
                        font-size: 3.5rem;
                        margin-bottom: 1rem;
                    }

                    /* Badges de tecnologías */
                    .tech-badge {
                        display: inline-block;
                        padding: 8px 16px;
                        margin: 4px;
                        border-radius: 20px;
                        font-size: 0.9rem;
                        font-weight: 500;
                        text-decoration: none;
                        transition: all 0.2s ease;
                    }

                    .tech-badge:hover {
                        transform: scale(1.05);
                    }

                    .tech-cliente {
                        background: linear-gradient(45deg, #007bff, #0056b3);
                        color: white;
                    }

                    .tech-servidor {
                        background: linear-gradient(45deg, #28a745, #1e7e34);
                        color: white;
                    }

                    .tech-fullstack {
                        background: linear-gradient(45deg, #dc3545, #c82333);
                        color: white;
                    }

                    /* Botones principales */
                    .btn-game {
                        background: linear-gradient(45deg, #ff6b6b, #ee5a24);
                        border: none;
                        color: white;
                        padding: 18px 40px;
                        font-size: 1.2rem;
                        font-weight: 600;
                        border-radius: 30px;
                        transition: all 0.3s ease;
                        box-shadow: 0 8px 20px rgba(255, 107, 107, 0.3);
                    }

                    .btn-game:hover {
                        background: linear-gradient(45deg, #ff5252, #e53935);
                        transform: translateY(-3px);
                        box-shadow: 0 12px 25px rgba(255, 107, 107, 0.4);
                        color: white;
                    }

                    /* Sección de estadísticas */
                    .stats-section {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        padding: 40px 0;
                    }

                    .stat-card {
                        background: rgba(255, 255, 255, 0.15);
                        border-radius: 15px;
                        padding: 2rem;
                        text-align: center;
                        backdrop-filter: blur(5px);
                        border: 1px solid rgba(255, 255, 255, 0.2);
                        transition: all 0.3s ease;
                    }

                    .stat-card:hover {
                        background: rgba(255, 255, 255, 0.25);
                        transform: translateY(-5px);
                    }

                    .stat-number {
                        font-size: 3rem;
                        font-weight: 700;
                        margin-bottom: 0.5rem;
                    }

                    /* Mejoras generales */
                    .section-title {
                        font-size: 2.2rem;
                        font-weight: 700;
                        margin-bottom: 1.5rem;
                        color: #333;
                    }

                    .cta-section {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                    }

                    /* Responsive */
                    @media (max-width: 768px) {
                        .hero-title {
                            font-size: 2.5rem;
                        }

                        .hero-subtitle {
                            font-size: 1.1rem;
                        }
                    }
                </style>
            </head>

            <body>
                <!-- Barra de navegación -->
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container">
                        <a class="navbar-brand fw-bold" href="../">
                            <i class="bi bi-house text-primary"></i> Torbesa
                        </a>
                        <div class="navbar-nav ms-auto">
                            <span class="navbar-text">
                                Bienvenido, <strong>
                                    <%= sessionUser.getUsername() %>
                                </strong>
                                <a href="../shared/LogoutServlet" class="btn btn-outline-secondary btn-sm ms-2">Cerrar
                                    Sesión</a>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- Sección Principal -->
                <section class="hero-section">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-8 mx-auto text-center">
                                <h1 class="hero-title">CodeQuest</h1>
                                <p class="hero-subtitle">
                                    Pon a prueba tus conocimientos de programación.<br>
                                    Identifica lenguajes, frameworks y librerías populares.
                                </p>
                                                            <div class="col text-center">
                                <a href="GameServlet" class="btn btn-game me-3">
                                    <i class="fas fa-play-circle me-2"></i>Empezar Juego
                                </a>
                                <a href="ScoreServlet" class="btn btn-outline-light btn-lg me-2">
                                    <i class="bi bi-trophy"></i> Ver Ranking
                                </a>
                                <a href="../" class="btn btn-outline-light btn-lg">
                                    <i class="bi bi-house"></i> Menú Principal
                                </a>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Sección Compacta: Categorías y Estadísticas -->
                <section class="py-3 bg-light">
                    <div class="container">
                        <!-- Categorías más compactas -->
                        <div class="row mb-4">
                            <div class="col-12 text-center mb-3">
                                <h2 class="h3 fw-bold text-dark">Categorías de Tecnologías</h2>
                                <p class="text-muted mb-0">Cliente, Servidor y FullStack</p>
                            </div>
                        </div>
                        <div class="row g-3 mb-4">
                            <div class="col-lg-4">
                                <div class="card border-0 shadow-sm h-100">
                                    <div class="card-body text-center p-3">
                                        <i class="bi bi-display text-primary" style="font-size: 2.5rem;"></i>
                                        <h5 class="text-primary fw-bold mt-2 mb-2">Cliente</h5>
                                        <p class="text-muted small mb-2">Frontend y UI</p>
                                        <div>
                                            <span class="badge bg-primary me-1 mb-1">HTML</span>
                                            <span class="badge bg-primary me-1 mb-1">CSS</span>
                                            <span class="badge bg-primary me-1 mb-1">JavaScript</span>
                                            <span class="badge bg-primary me-1 mb-1">React</span>
                                            <span class="badge bg-primary me-1 mb-1">Vue.js</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="card border-0 shadow-sm h-100">
                                    <div class="card-body text-center p-3">
                                        <i class="bi bi-server text-success" style="font-size: 2.5rem;"></i>
                                        <h5 class="text-success fw-bold mt-2 mb-2">Servidor</h5>
                                        <p class="text-muted small mb-2">Backend y APIs</p>
                                        <div>
                                            <span class="badge bg-success me-1 mb-1">Java</span>
                                            <span class="badge bg-success me-1 mb-1">Python</span>
                                            <span class="badge bg-success me-1 mb-1">PHP</span>
                                            <span class="badge bg-success me-1 mb-1">Spring</span>
                                            <span class="badge bg-success me-1 mb-1">Django</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="card border-0 shadow-sm h-100">
                                    <div class="card-body text-center p-3">
                                        <i class="bi bi-layers text-danger" style="font-size: 2.5rem;"></i>
                                        <h5 class="text-danger fw-bold mt-2 mb-2">FullStack</h5>
                                        <p class="text-muted small mb-2">Completo</p>
                                        <div>
                                            <span class="badge bg-danger me-1 mb-1">Node.js</span>
                                            <span class="badge bg-danger me-1 mb-1">Next.js</span>
                                            <span class="badge bg-danger me-1 mb-1">Nuxt.js</span>
                                            <span class="badge bg-danger me-1 mb-1">SvelteKit</span>
                                            <span class="badge bg-danger me-1 mb-1">Remix</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Estadísticas Compactas -->
                <section class="py-4" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                    <div class="container">
                        <div class="row">
                            <div class="col-12 text-center mb-3">
                                <h3 class="fw-bold text-white mb-1">Estadísticas del Juego</h3>
                                <p class="text-light small mb-0">Datos importantes de programación</p>
                            </div>
                        </div>
                        <div class="row g-4">
                            <div class="col-6 col-md-3">
                                <div class="text-center p-3"
                                    style="background: rgba(255,255,255,0.1); border-radius: 15px; backdrop-filter: blur(10px);">
                                    <i class="bi bi-code-slash text-white" style="font-size: 2.5rem;"></i>
                                    <div class="h1 fw-bold text-white mt-2 mb-1">80+</div>
                                    <h6 class="text-white fw-bold mb-1">Tecnologías</h6>
                                    <small class="text-light opacity-75">Lenguajes, Frameworks, Librerías</small>
                                </div>
                            </div>
                            <div class="col-6 col-md-3">
                                <div class="text-center p-3"
                                    style="background: rgba(255,255,255,0.1); border-radius: 15px; backdrop-filter: blur(10px);">
                                    <i class="bi bi-layers text-white" style="font-size: 2.5rem;"></i>
                                    <div class="h1 fw-bold text-white mt-2 mb-1">3</div>
                                    <h6 class="text-white fw-bold mb-1">Categorías</h6>
                                    <small class="text-light opacity-75">Cliente, Servidor, FullStack</small>
                                </div>
                            </div>
                            <div class="col-6 col-md-3">
                                <div class="text-center p-3"
                                    style="background: rgba(255,255,255,0.1); border-radius: 15px; backdrop-filter: blur(10px);">
                                    <i class="bi bi-speedometer2 text-white" style="font-size: 2.5rem;"></i>
                                    <div class="h1 fw-bold text-white mt-2 mb-1">3</div>
                                    <h6 class="text-white fw-bold mb-1">Niveles</h6>
                                    <small class="text-light opacity-75">Fácil, Medio, Difícil</small>
                                </div>
                            </div>
                            <div class="col-6 col-md-3">
                                <div class="text-center p-3"
                                    style="background: rgba(255,255,255,0.1); border-radius: 15px; backdrop-filter: blur(10px);">
                                    <i class="bi bi-arrow-repeat text-white" style="font-size: 2.5rem;"></i>
                                    <div class="h1 fw-bold text-white mt-2 mb-1">2</div>
                                    <h6 class="text-white fw-bold mb-1">Intentos</h6>
                                    <small class="text-light opacity-75">Múltiples oportunidades</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Footer -->
                <footer
                    style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 2rem 0;">
                    <div class="container text-center">
                        <div class="row">
                            <div class="col-12">
                                <p class="h5 mb-2">
                                    <i class="bi bi-code-slash"></i> CodeQuest
                                </p>
                                <p class="mb-2 opacity-75">
                                    El juego definitivo para repasar lenguajes y tecnologías de programación.
                                </p>
                                <p class="mb-0 small opacity-50">
                                    © 2025 Torbesa -  <i class="bi bi-heart-fill text-danger"></i> 
                                    Pollyanna.
                                </p>
                            </div>
                        </div>
                    </div>
                </footer>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>