<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>🏆 Leaderboard - Dog Quiz</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 30px 0;
        }
        .leaderboard-container {
            max-width: 1000px;
            margin: 0 auto;
        }
        .header-section {
            text-align: center;
            color: white;
            margin-bottom: 40px;
        }
        .main-title {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 15px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
            animation: slideInDown 1s ease-out;
        }
        .subtitle {
            font-size: 1.3rem;
            opacity: 0.9;
            margin-bottom: 30px;
            animation: fadeInUp 1s ease-out 0.2s both;
        }
        .trophy-icon {
            font-size: 2.5rem;
            color: #feca57;
            margin-bottom: 20px;
            animation: bounce 2s infinite;
        }
        .leaderboard-card {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 25px;
            box-shadow: 0 25px 60px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            backdrop-filter: blur(15px);
            border: 2px solid rgba(255, 255, 255, 0.3);
            margin-bottom: 30px;
        }
        .card-header-custom {
            background: linear-gradient(45deg, #fd79a8, #fdcb6e);
            color: white;
            padding: 30px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }
        .card-header-custom::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: repeating-linear-gradient(
                45deg,
                transparent,
                transparent 15px,
                rgba(255,255,255,0.1) 15px,
                rgba(255,255,255,0.1) 30px
            );
            animation: shimmer 20s linear infinite;
        }
        @keyframes shimmer {
            0% { transform: translate(0, 0); }
            100% { transform: translate(60px, 60px); }
        }
        .header-title {
            font-size: 2rem;
            font-weight: 700;
            margin: 0;
            position: relative;
            z-index: 1;
        }
        .stats-row {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
            position: relative;
            z-index: 1;
        }
        .stat-item {
            text-align: center;
        }
        .stat-number {
            font-size: 1.8rem;
            font-weight: 700;
            display: block;
        }
        .stat-label {
            font-size: 0.9rem;
            opacity: 0.9;
        }
        .table-container {
            padding: 0;
            max-height: 600px;
            overflow-y: auto;
        }
        .action-section {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 30px;
            text-align: center;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
        .action-title {
            color: white;
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 25px;
        }
        .action-buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }
        .action-btn {
            padding: 15px 30px;
            border-radius: 25px;
            font-weight: 600;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            border: none;
            min-width: 180px;
        }
        .btn-play {
            background: linear-gradient(45deg, #00b894, #00cec9);
            color: white;
            box-shadow: 0 8px 25px rgba(0, 184, 148, 0.3);
        }
        .btn-play:hover {
            background: linear-gradient(45deg, #00a085, #00b7b7);
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(0, 184, 148, 0.4);
        }
        .btn-home {
            background: linear-gradient(45deg, #74b9ff, #0984e3);
            color: white;
            box-shadow: 0 8px 25px rgba(116, 185, 255, 0.3);
        }
        .btn-home:hover {
            background: linear-gradient(45deg, #6c5ce7, #a29bfe);
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(116, 185, 255, 0.4);
        }
        .btn-logout {
            background: linear-gradient(45deg, #fd79a8, #fdcb6e);
            color: white;
            box-shadow: 0 8px 25px rgba(253, 121, 168, 0.3);
        }
        .btn-logout:hover {
            background: linear-gradient(45deg, #e84393, #f39c12);
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(253, 121, 168, 0.4);
        }
        .floating-elements {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            pointer-events: none;
            z-index: -1;
        }
        .floating-element {
            position: absolute;
            font-size: 2rem;
            opacity: 0.1;
            animation: float 6s ease-in-out infinite;
        }
        @keyframes float {
            0%, 100% { transform: translateY(0px) rotate(0deg); }
            50% { transform: translateY(-30px) rotate(180deg); }
        }
        @keyframes slideInDown {
            from {
                opacity: 0;
                transform: translateY(-50px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% {
                transform: translateY(0);
            }
            40% {
                transform: translateY(-10px);
            }
            60% {
                transform: translateY(-5px);
            }
        }
    </style>
</head>
<body>
    <!-- Floating Background Elements -->
    <div class="floating-elements">
        <div class="floating-element" style="top: 10%; left: 10%; animation-delay: 0s;">🏆</div>
        <div class="floating-element" style="top: 20%; right: 15%; animation-delay: -2s;">🥇</div>
        <div class="floating-element" style="top: 60%; left: 5%; animation-delay: -4s;">🐕</div>
        <div class="floating-element" style="bottom: 30%; right: 10%; animation-delay: -1s;">⭐</div>
        <div class="floating-element" style="top: 70%; right: 25%; animation-delay: -3s;">🎯</div>
        <div class="floating-element" style="bottom: 20%; left: 20%; animation-delay: -5s;">🏅</div>
    </div>

    <div class="container leaderboard-container">
        <div class="header-section">
            <i class="fas fa-trophy trophy-icon"></i>
            <h1 class="main-title">Hall of Fame</h1>
            <p class="subtitle">Los mejores expertos en razas caninas del mundo</p>
        </div>

        <div class="leaderboard-card">
            <div class="card-header-custom">
                <h2 class="header-title">
                    <i class="fas fa-crown me-3"></i>
                    Ranking Global de Campeones
                </h2>
                <div class="stats-row">
                    <div class="stat-item">
                        <span class="stat-number">${highScores.size()}</span>
                        <span class="stat-label">Participantes</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">50+</span>
                        <span class="stat-label">Razas</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">∞</span>
                        <span class="stat-label">Diversión</span>
                    </div>
                </div>
            </div>
            <div class="table-container">
                <%@ include file="tablaLider.jsp" %>
            </div>
        </div>

        <div class="action-section">
            <h3 class="action-title">
                <i class="fas fa-gamepad me-2"></i>
                ¿Listo para el desafío?
            </h3>
            <div class="action-buttons">
                <form method="get" action="JuegoServlet" style="display: inline;">
                    <button type="submit" class="btn action-btn btn-play">
                        <i class="fas fa-play me-2"></i>¡Jugar Ahora!
                    </button>
                </form>
                <form method="get" action="landing.jsp" style="display: inline;">
                    <button type="submit" class="btn action-btn btn-home">
                        <i class="fas fa-home me-2"></i>Volver al Inicio
                    </button>
                </form>
                <form method="get" action="../shared/LogoutServlet" style="display: inline;">
                    <button type="submit" class="btn action-btn btn-logout">
                        <i class="fas fa-sign-out-alt me-2"></i>Cerrar Sesión
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>