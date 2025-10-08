<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>🏆 Resultados - Dog Quiz</title>
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
        .results-container {
            max-width: 900px;
            margin: 0 auto;
        }
        .results-header {
            text-align: center;
            color: white;
            margin-bottom: 40px;
        }
        .results-title {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 10px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
            animation: bounceIn 1s ease-out;
        }
        .results-subtitle {
            font-size: 1.2rem;
            opacity: 0.9;
            animation: fadeInUp 1s ease-out 0.2s both;
        }
        .score-card {
            background: linear-gradient(145deg, #ffffff, #f8f9fa);
            border-radius: 25px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            margin-bottom: 30px;
            border: 3px solid transparent;
            background-clip: padding-box;
            position: relative;
        }
        .score-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(45deg, #ff6b6b, #feca57, #48cae4, #80ed99);
            border-radius: 25px;
            padding: 3px;
            mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
            mask-composite: exclude;
            z-index: -1;
        }
        .score-content {
            padding: 40px;
            text-align: center;
            position: relative;
            z-index: 1;
        }
        .score-icon {
            font-size: 4rem;
            color: #feca57;
            margin-bottom: 20px;
            animation: pulse 2s infinite;
        }
        .score-number {
            font-size: 3.5rem;
            font-weight: 700;
            color: #2d3436;
            margin: 20px 0;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
        }
        .score-label {
            font-size: 1.2rem;
            color: #636e72;
            font-weight: 600;
        }
        .play-again-btn {
            background: linear-gradient(45deg, #00b894, #00cec9);
            border: none;
            padding: 15px 30px;
            font-size: 1.1rem;
            font-weight: 600;
            border-radius: 25px;
            color: white;
            margin-top: 20px;
            transition: all 0.3s ease;
            box-shadow: 0 8px 25px rgba(0, 184, 148, 0.3);
        }
        .play-again-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(0, 184, 148, 0.4);
            background: linear-gradient(45deg, #00a085, #00b7b7);
        }
        .leaderboard-card {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 25px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            backdrop-filter: blur(10px);
        }
        .leaderboard-header {
            background: linear-gradient(45deg, #fd79a8, #fdcb6e);
            color: white;
            padding: 25px;
            text-align: center;
            position: relative;
        }
        .leaderboard-title {
            font-size: 1.8rem;
            font-weight: 700;
            margin: 0;
        }
        .leaderboard-content {
            padding: 0;
        }
        .custom-table {
            margin: 0;
            font-size: 1rem;
        }
        .custom-table thead th {
            background: linear-gradient(45deg, #2d3436, #636e72);
            color: white;
            border: none;
            padding: 20px 15px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-size: 0.9rem;
        }
        .custom-table tbody tr {
            transition: all 0.3s ease;
            border-bottom: 1px solid #e9ecef;
        }
        .custom-table tbody tr:hover {
            background: linear-gradient(45deg, #f8f9fa, #e9ecef);
            transform: scale(1.02);
        }
        .custom-table tbody td {
            padding: 20px 15px;
            vertical-align: middle;
            border: none;
        }
        .rank-badge {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            font-weight: 700;
            color: white;
            font-size: 1.1rem;
        }
        .rank-1 { background: linear-gradient(45deg, #ffd700, #ffed4e); color: #2d3436; }
        .rank-2 { background: linear-gradient(45deg, #c0c0c0, #e8e8e8); color: #2d3436; }
        .rank-3 { background: linear-gradient(45deg, #cd7f32, #deb887); color: white; }
        .rank-other { background: linear-gradient(45deg, #74b9ff, #0984e3); }
        .username {
            font-weight: 600;
            color: #2d3436;
            font-size: 1.1rem;
        }
        .score-badge {
            background: linear-gradient(45deg, #00b894, #00cec9);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
        }
        .tries-badge {
            background: linear-gradient(45deg, #fd79a8, #fdcb6e);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 600;
        }
        .confetti {
            position: absolute;
            width: 10px;
            height: 10px;
            background: #feca57;
            animation: confetti-fall 3s linear infinite;
        }
        .confetti:nth-child(odd) {
            background: #ff6b6b;
            animation-delay: -1s;
        }
        .confetti:nth-child(even) {
            background: #48cae4;
            animation-delay: -2s;
        }
        @keyframes confetti-fall {
            0% {
                transform: translateY(-100px) rotate(0deg);
                opacity: 1;
            }
            100% {
                transform: translateY(100vh) rotate(360deg);
                opacity: 0;
            }
        }
        @keyframes bounceIn {
            0% {
                transform: scale(0.3);
                opacity: 0;
            }
            50% {
                transform: scale(1.1);
            }
            100% {
                transform: scale(1);
                opacity: 1;
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
        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
        }
    </style>
</head>
<body>
    <!-- Confetti Animation -->
    <div class="confetti" style="left: 10%; animation-delay: 0s;"></div>
    <div class="confetti" style="left: 20%; animation-delay: -0.5s;"></div>
    <div class="confetti" style="left: 30%; animation-delay: -1s;"></div>
    <div class="confetti" style="left: 40%; animation-delay: -1.5s;"></div>
    <div class="confetti" style="left: 50%; animation-delay: -2s;"></div>
    <div class="confetti" style="left: 60%; animation-delay: -2.5s;"></div>
    <div class="confetti" style="left: 70%; animation-delay: -3s;"></div>
    <div class="confetti" style="left: 80%; animation-delay: -3.5s;"></div>
    <div class="confetti" style="left: 90%; animation-delay: -4s;"></div>

    <div class="container results-container">
        <div class="results-header">
            <h1 class="results-title">
                <i class="fas fa-trophy"></i> ¡Resultados!
            </h1>
            <p class="results-subtitle">¡Excelente trabajo identificando razas caninas!</p>
        </div>

        <div class="score-card">
            <div class="score-content">
                <i class="fas fa-medal score-icon"></i>
                <div class="score-number">${userScore.score}</div>
                <div class="score-label">Puntos Obtenidos</div>
                <p class="mt-3 text-muted">
                    <i class="fas fa-target me-2"></i>
                    ${userScore.tries} intentos realizados
                </p>
                <a href="JuegoServlet" class="btn play-again-btn">
                    <i class="fas fa-redo me-2"></i>¡Jugar de Nuevo!
                </a>
            </div>
        </div>

        <div class="leaderboard-card">
            <div class="leaderboard-header">
                <h3 class="leaderboard-title">
                    <i class="fas fa-crown me-2"></i>Ranking de Expertos Caninos
                </h3>
            </div>
            <div class="leaderboard-content">
                <table class="table custom-table">
                    <thead>
                        <tr>
                            <th><i class="fas fa-hashtag me-2"></i>Ranking</th>
                            <th><i class="fas fa-user me-2"></i>Usuario</th>
                            <th class="text-center"><i class="fas fa-star me-2"></i>Score</th>
                            <th class="text-center"><i class="fas fa-target me-2"></i>Tries</th>
                            <th class="text-center"><i class="fas fa-calendar me-2"></i>Fecha</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="score" items="${highScores}" varStatus="status">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${status.index == 0}">
                                            <span class="rank-badge rank-1">1</span>
                                        </c:when>
                                        <c:when test="${status.index == 1}">
                                            <span class="rank-badge rank-2">2</span>
                                        </c:when>
                                        <c:when test="${status.index == 2}">
                                            <span class="rank-badge rank-3">3</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="rank-badge rank-other">${status.index + 1}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="username">
                                        <i class="fas fa-dog me-2 text-warning"></i>
                                        ${score.username}
                                    </span>
                                </td>
                                <td class="text-center">
                                    <span class="score-badge">${score.score}</span>
                                </td>
                                <td class="text-center">
                                    <span class="tries-badge">${score.tries}</span>
                                </td>
                                <td class="text-center text-muted">
                                    <small>
                                        <i class="fas fa-clock me-1"></i>
                                        <fmt:formatDate value="${score.timestampAsDate}" pattern="dd/MM/yyyy"/>
                                    </small>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>