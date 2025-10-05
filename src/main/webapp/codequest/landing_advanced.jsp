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
                <link href="https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@400;500;600;700&display=swap"
                    rel="stylesheet">
                <style>
                    * {
                        font-family: 'Space Grotesk', sans-serif;
                    }

                    .hero-section {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
                        color: white;
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        position: relative;
                        overflow: hidden;
                    }

                    .hero-section::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grid" width="10" height="10" patternUnits="userSpaceOnUse"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="rgba(255,255,255,0.1)" stroke-width="0.5"/></pattern></defs><rect width="100" height="100" fill="url(%23grid)"/></svg>');
                        animation: float 20s ease-in-out infinite;
                    }

                    @keyframes float {

                        0%,
                        100% {
                            transform: translateY(0px);
                        }

                        50% {
                            transform: translateY(-20px);
                        }
                    }

                    .hero-content {
                        position: relative;
                        z-index: 2;
                    }

                    .hero-title {
                        font-size: 4.5rem;
                        font-weight: 700;
                        background: linear-gradient(45deg, #ffffff, #a8edea);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                        background-clip: text;
                        margin-bottom: 1.5rem;
                        text-shadow: 0 0 30px rgba(255, 255, 255, 0.3);
                    }

                    .hero-subtitle {
                        font-size: 1.4rem;
                        font-weight: 400;
                        opacity: 0.9;
                        margin-bottom: 3rem;
                        line-height: 1.6;
                    }

                    .btn-primary-custom {
                        background: linear-gradient(45deg, #ff6b6b, #ee5a24, #ff9f43);
                        border: none;
                        padding: 18px 50px;
                        font-size: 1.3rem;
                        font-weight: 600;
                        border-radius: 50px;
                        color: white;
                        text-decoration: none;
                        display: inline-block;
                        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
                        box-shadow: 0 20px 40px rgba(255, 107, 107, 0.3);
                        position: relative;
                        overflow: hidden;
                    }

                    .btn-primary-custom:hover {
                        transform: translateY(-5px) scale(1.05);
                        box-shadow: 0 30px 60px rgba(255, 107, 107, 0.4);
                        color: white;
                    }

                    .btn-primary-custom::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: -100%;
                        width: 100%;
                        height: 100%;
                        background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
                        transition: left 0.6s;
                    }

                    .btn-primary-custom:hover::before {
                        left: 100%;
                    }

                    .game-card {
                        background: white;
                        border: none;
                        border-radius: 20px;
                        padding: 2.5rem;
                        height: 100%;
                        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
                        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
                        position: relative;
                        overflow: hidden;
                    }

                    .game-card::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: 0;
                        right: 0;
                        height: 5px;
                        background: linear-gradient(90deg, #667eea, #764ba2, #f093fb);
                    }

                    .game-card:hover {
                        transform: translateY(-15px) rotate(2deg);
                        box-shadow: 0 25px 60px rgba(0, 0, 0, 0.15);
                    }

                    .card-icon {
                        font-size: 4rem;
                        margin-bottom: 1.5rem;
                        background: linear-gradient(45deg, #667eea, #764ba2);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                        background-clip: text;
                    }

                    .tech-badge {
                        display: inline-block;
                        padding: 8px 16px;
                        margin: 4px;
                        border-radius: 25px;
                        font-size: 0.9rem;
                        font-weight: 500;
                        text-decoration: none;
                        transition: all 0.3s ease;
                    }

                    .tech-frontend {
                        background: linear-gradient(45deg, #667eea, #764ba2);
                        color: white;
                    }

                    .tech-backend {
                        background: linear-gradient(45deg, #f093fb, #f5576c);
                        color: white;
                    }

                    .tech-database {
                        background: linear-gradient(45deg, #4facfe, #00f2fe);
                        color: white;
                    }

                    .tech-devops {
                        background: linear-gradient(45deg, #43e97b, #38f9d7);
                        color: white;
                    }

                    .tech-badge:hover {
                        transform: scale(1.1);
                        color: white;
                    }

                    .stats-section {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        padding: 6rem 0;
                    }

                    .stat-card {
                        text-align: center;
                        padding: 2rem;
                        background: rgba(255, 255, 255, 0.1);
                        border-radius: 20px;
                        backdrop-filter: blur(10px);
                        border: 1px solid rgba(255, 255, 255, 0.2);
                        transition: all 0.3s ease;
                    }

                    .stat-card:hover {
                        transform: translateY(-10px);
                        background: rgba(255, 255, 255, 0.2);
                    }

                    .stat-number {
                        font-size: 3.5rem;
                        font-weight: 700;
                        margin-bottom: 0.5rem;
                    }

                    .navbar-custom {
                        background: rgba(255, 255, 255, 0.95) !important;
                        backdrop-filter: blur(10px);
                        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
                    }

                    .floating-elements {
                        position: absolute;
                        width: 100%;
                        height: 100%;
                        overflow: hidden;
                        z-index: 1;
                    }

                    .floating-element {
                        position: absolute;
                        background: rgba(255, 255, 255, 0.1);
                        border-radius: 50%;
                        animation: float-random 15s infinite linear;
                    }

                    @keyframes float-random {
                        0% {
                            transform: translateY(100vh) rotate(0deg);
                        }

                        100% {
                            transform: translateY(-100px) rotate(360deg);
                        }
                    }

                    .section-title {
                        font-size: 3rem;
                        font-weight: 700;
                        text-align: center;
                        margin-bottom: 4rem;
                        background: linear-gradient(45deg, #667eea, #764ba2);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                        background-clip: text;
                    }
                </style>
            </head>

            <body>
                <!-- Navegación -->
                <nav class="navbar navbar-expand-lg navbar-light navbar-custom fixed-top">
                    <div class="container">
                        <a class="navbar-brand fw-bold" href="../index.jsp" style="color: #667eea;">
                            <i class="bi bi-code-slash"></i> Torbesa
                        </a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <div class="navbar-nav ms-auto">
                                <a class="nav-link" href="../shared/welcome.jsp" style="color: #667eea;">Inicio</a>
                                <a class="nav-link" href="../capitals/landing.jsp" style="color: #667eea;">Capitals</a>
                                <a class="nav-link active fw-bold" href="landing.jsp"
                                    style="color: #764ba2;">CodeQuest</a>
                                <a class="nav-link" href="../shared/logout.jsp" style="color: #667eea;">Cerrar
                                    Sesión</a>
                            </div>
                        </div>
                    </div>
                </nav>

                <!-- Sección Hero -->
                <section class="hero-section">
                    <div class="floating-elements">
                        <div class="floating-element"
                            style="width: 60px; height: 60px; left: 10%; animation-delay: 0s;"></div>
                        <div class="floating-element"
                            style="width: 80px; height: 80px; left: 80%; animation-delay: 2s;"></div>
                        <div class="floating-element"
                            style="width: 40px; height: 40px; left: 60%; animation-delay: 4s;"></div>
                        <div class="floating-element"
                            style="width: 100px; height: 100px; left: 30%; animation-delay: 6s;"></div>
                    </div>
                    <div class="container hero-content">
                        <div class="row align-items-center min-vh-100">
                            <div class="col-lg-10 mx-auto text-center">
                                <div class="mb-4">
                                    <i class="bi bi-code-square"
                                        style="font-size: 5rem; color: rgba(255,255,255,0.9);"></i>
                                </div>
                                <h1 class="hero-title">CodeQuest</h1>
                                <p class="hero-subtitle">
                                    ¡Hola <strong>
                                        <%= sessionUser.getUsername() %>
                                    </strong>! 🚀<br>
                                    Desafía tus conocimientos en programación con el juego más emocionante.<br>
                                    ¿Podrás identificar las tecnologías más populares del desarrollo?
                                </p>
                                <div class="text-center">
                                    <a href="RestartGameServlet" class="btn-primary-custom me-3">
                                        <i class="bi bi-play-fill"></i> Comenzar Aventura
                                    </a>
                                    <a href="ScoreServlet" class="btn btn-outline-light btn-lg"
                                        style="border-radius: 50px; padding: 18px 40px;">
                                        <i class="bi bi-trophy"></i> Ver Rankings
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Cómo Funciona -->
                <section class="py-5" style="margin-top: 6rem;">
                    <div class="container">
                        <h2 class="section-title">¿Cómo Funciona?</h2>
                        <div class="row g-4">
                            <div class="col-lg-3 col-md-6">
                                <div class="game-card text-center">
                                    <i class="bi bi-question-circle-fill card-icon"></i>
                                    <h4 class="fw-bold mb-3">1. Pregunta</h4>
                                    <p class="text-muted">
                                        Te mostramos el nombre de una tecnología popular del mundo del desarrollo
                                    </p>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="game-card text-center">
                                    <i class="bi bi-list-check card-icon"></i>
                                    <h4 class="fw-bold mb-3">2. Opciones</h4>
                                    <p class="text-muted">
                                        Elige la descripción correcta entre 4 opciones cuidadosamente seleccionadas
                                    </p>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="game-card text-center">
                                    <i class="bi bi-award-fill card-icon"></i>
                                    <h4 class="fw-bold mb-3">3. Puntuación</h4>
                                    <p class="text-muted">
                                        Gana puntos por cada respuesta correcta y mejora tu ranking personal
                                    </p>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="game-card text-center">
                                    <i class="bi bi-graph-up-arrow card-icon"></i>
                                    <h4 class="fw-bold mb-3">4. Progreso</h4>
                                    <p class="text-muted">
                                        Compite con otros desarrolladores y sube en el ranking global
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Tecnologías del Juego -->
                <section class="py-5" style="background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);">
                    <div class="container">
                        <h2 class="section-title">Tecnologías que Dominarás</h2>
                        <div class="row g-4">
                            <div class="col-lg-4">
                                <div class="game-card">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-display card-icon"></i>
                                        <h4 class="fw-bold">Cliente (Frontend)</h4>
                                        <p class="text-muted">Tecnologías del lado del cliente</p>
                                    </div>
                                    <div class="mb-3">
                                        <a href="#" class="tech-badge tech-frontend">JavaScript</a>
                                        <a href="#" class="tech-badge tech-frontend">TypeScript</a>
                                        <a href="#" class="tech-badge tech-frontend">React</a>
                                        <a href="#" class="tech-badge tech-frontend">Vue.js</a>
                                        <a href="#" class="tech-badge tech-frontend">Angular</a>
                                        <a href="#" class="tech-badge tech-frontend">HTML</a>
                                        <a href="#" class="tech-badge tech-frontend">CSS</a>
                                        <a href="#" class="tech-badge tech-frontend">Bootstrap</a>
                                        <a href="#" class="tech-badge tech-frontend">jQuery</a>
                                        <a href="#" class="tech-badge tech-frontend">Redux</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="game-card">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-server card-icon"></i>
                                        <h4 class="fw-bold">Servidor (Backend)</h4>
                                        <p class="text-muted">Tecnologías del lado del servidor</p>
                                    </div>
                                    <div class="mb-3">
                                        <a href="#" class="tech-badge tech-backend">Java</a>
                                        <a href="#" class="tech-badge tech-backend">Python</a>
                                        <a href="#" class="tech-badge tech-backend">Spring Boot</a>
                                        <a href="#" class="tech-badge tech-backend">Django</a>
                                        <a href="#" class="tech-badge tech-backend">PHP</a>
                                        <a href="#" class="tech-badge tech-backend">Laravel</a>
                                        <a href="#" class="tech-badge tech-backend">C#</a>
                                        <a href="#" class="tech-badge tech-backend">Express.js</a>
                                        <a href="#" class="tech-badge tech-backend">Flask</a>
                                        <a href="#" class="tech-badge tech-backend">FastAPI</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="game-card">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-layers card-icon"></i>
                                        <h4 class="fw-bold">FullStack</h4>
                                        <p class="text-muted">Tecnologías de pila completa</p>
                                    </div>
                                    <div class="mb-3">
                                        <a href="#" class="tech-badge tech-database">Node.js</a>
                                        <a href="#" class="tech-badge tech-database">Next.js</a>
                                        <a href="#" class="tech-badge tech-database">Nuxt.js</a>
                                        <a href="#" class="tech-badge tech-database">Meteor</a>
                                        <a href="#" class="tech-badge tech-database">SvelteKit</a>
                                        <a href="#" class="tech-badge tech-database">Remix</a>
                                        <a href="#" class="tech-badge tech-database">T3 Stack</a>
                                        <a href="#" class="tech-badge tech-database">Gatsby</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row g-4 mt-2">
                            <div class="col-lg-6">
                                <div class="game-card">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-gear card-icon"></i>
                                        <h4 class="fw-bold">Frameworks & Librerías</h4>
                                        <p class="text-muted">Herramientas que facilitan el desarrollo</p>
                                    </div>
                                    <div class="mb-3">
                                        <a href="#" class="tech-badge tech-devops">Webpack</a>
                                        <a href="#" class="tech-badge tech-devops">Vite</a>
                                        <a href="#" class="tech-badge tech-devops">Babel</a>
                                        <a href="#" class="tech-badge tech-devops">Sass</a>
                                        <a href="#" class="tech-badge tech-devops">Jest</a>
                                        <a href="#" class="tech-badge tech-devops">Cypress</a>
                                        <a href="#" class="tech-badge tech-devops">Axios</a>
                                        <a href="#" class="tech-badge tech-devops">Lodash</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="game-card">
                                    <div class="text-center mb-4">
                                        <i class="bi bi-puzzle card-icon"></i>
                                        <h4 class="fw-bold">Tipos de Tecnología</h4>
                                        <p class="text-muted">Clasificación por tipo</p>
                                    </div>
                                    <div class="mb-3">
                                        <span class="tech-badge tech-frontend">Lenguajes</span>
                                        <span class="tech-badge tech-backend">Frameworks</span>
                                        <span class="tech-badge tech-database">Librerías</span>
                                        <br><br>
                                        <small class="text-muted">
                                            <strong>Dificultad:</strong><br>
                                            🟢 Fácil • 🟡 Medio • 🔴 Difícil
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Estadísticas -->
                <section class="stats-section">
                    <div class="container">
                        <div class="row">
                            <div class="col-12 text-center mb-5">
                                <h2 class="display-5 fw-bold">Estadísticas del Juego</h2>
                                <p class="lead">Datos de las tecnologías más importantes en programación</p>
                            </div>
                        </div>
                        <div class="row g-4">
                            <div class="col-lg-3 col-md-6">
                                <div class="stat-card">
                                    <i class="bi bi-code-slash" style="font-size: 3rem; margin-bottom: 1rem;"></i>
                                    <div class="stat-number">80+</div>
                                    <p class="mb-0 h5">Tecnologías</p>
                                    <small class="opacity-75">Lenguajes, Frameworks, Librerías</small>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="stat-card">
                                    <i class="bi bi-layers" style="font-size: 3rem; margin-bottom: 1rem;"></i>
                                    <div class="stat-number">3</div>
                                    <p class="mb-0 h5">Categorías</p>
                                    <small class="opacity-75">Cliente, Servidor, FullStack</small>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="stat-card">
                                    <i class="bi bi-speedometer2" style="font-size: 3rem; margin-bottom: 1rem;"></i>
                                    <div class="stat-number">3</div>
                                    <p class="mb-0 h5">Niveles</p>
                                    <small class="opacity-75">Fácil, Medio, Difícil</small>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6">
                                <div class="stat-card">
                                    <i class="bi bi-arrow-repeat" style="font-size: 3rem; margin-bottom: 1rem;"></i>
                                    <div class="stat-number">∞</div>
                                    <p class="mb-0 h5">Múltiples Intentos</p>
                                    <small class="opacity-75">Aprende de tus errores</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Llamada Final -->
                <section class="py-5" style="background: white;">
                    <div class="container text-center">
                        <div class="row">
                            <div class="col-lg-8 mx-auto">
                                <h2 class="display-6 fw-bold mb-4" style="color: #667eea;">
                                    ¿Estás Listo para el Desafío?
                                </h2>
                                <p class="lead text-muted mb-5">
                                    Únete a miles de desarrolladores que ya están probando sus conocimientos.<br>
                                    Tu aventura en el mundo de la programación comienza aquí.
                                </p>
                                <div class="d-flex gap-3 justify-content-center flex-wrap">
                                    <a href="RestartGameServlet" class="btn-primary-custom">
                                        <i class="bi bi-rocket-takeoff"></i> ¡Empezar Ahora!
                                    </a>
                                    <a href="ScoreServlet" class="btn btn-outline-secondary btn-lg"
                                        style="border-radius: 50px; padding: 18px 40px;">
                                        <i class="bi bi-trophy"></i> Ver Mejores Puntuaciones
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Footer -->
                <footer
                    style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 3rem 0;">
                    <div class="container text-center">
                        <div class="row">
                            <div class="col-12">
                                <p class="h5 mb-3">
                                    <i class="bi bi-code-slash"></i> CodeQuest
                                </p>
                                <p class="mb-3 opacity-75">
                                    El juego definitivo para desarrolladores apasionados
                                </p>
                                <p class="mb-0 small opacity-50">
                                    © 2024 Torbesa - CodeQuest. Hecho con <i class="bi bi-heart-fill text-danger"></i>
                                    para la comunidad dev.
                                </p>
                            </div>
                        </div>
                    </div>
                </footer>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    // Efectos de animación adicionales
                    document.addEventListener('DOMContentLoaded', function () {
                        // Animación de aparición para las tarjetas
                        const cards = document.querySelectorAll('.game-card');
                        const observer = new IntersectionObserver((entries) => {
                            entries.forEach(entry => {
                                if (entry.isIntersecting) {
                                    entry.target.style.opacity = '1';
                                    entry.target.style.transform = 'translateY(0)';
                                }
                            });
                        });

                        cards.forEach(card => {
                            card.style.opacity = '0';
                            card.style.transform = 'translateY(30px)';
                            card.style.transition = 'all 0.6s ease';
                            observer.observe(card);
                        });
                    });
                </script>
            </body>

            </html>