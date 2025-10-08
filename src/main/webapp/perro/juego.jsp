<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>🐕 Dog Quiz - ¡Identifica la Raza!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
        }
        .game-container {
            max-width: 700px;
            margin: 0 auto;
        }
        .game-card {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 25px;
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.3);
        }
        .game-header {
            background: linear-gradient(45deg, #ff6b6b, #feca57);
            color: white;
            padding: 25px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }
        .game-header::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: repeating-linear-gradient(
                45deg,
                transparent,
                transparent 10px,
                rgba(255,255,255,0.1) 10px,
                rgba(255,255,255,0.1) 20px
            );
            animation: move 20s linear infinite;
        }
        @keyframes move {
            0% { transform: translate(0, 0); }
            100% { transform: translate(50px, 50px); }
        }
        .game-title {
            font-size: 1.8rem;
            font-weight: 700;
            margin: 0;
            position: relative;
            z-index: 1;
        }
        .score-display {
            position: absolute;
            top: 15px;
            right: 20px;
            background: rgba(255, 255, 255, 0.2);
            padding: 10px 20px;
            border-radius: 25px;
            z-index: 2;
        }
        .dog-image-container {
            padding: 30px;
            text-align: center;
            background: linear-gradient(145deg, #f8f9fa, #e9ecef);
        }
        .dog-image {
            max-height: 350px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            border: 5px solid white;
        }
        .dog-image:hover {
            transform: scale(1.05);
        }
        .options-container {
            padding: 0 30px 30px;
        }
        .option-card {
            background: linear-gradient(145deg, #ffffff, #f8f9fa);
            border: 2px solid #e9ecef;
            border-radius: 15px;
            padding: 15px 20px;
            margin-bottom: 12px;
            transition: all 0.3s ease;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }
        .option-card:hover {
            border-color: #feca57;
            background: linear-gradient(145deg, #fff9e6, #fef7e0);
            transform: translateX(5px);
        }
        .option-card input[type="radio"]:checked + .option-content {
            color: #ff6b6b;
            font-weight: 600;
        }
        .option-card input[type="radio"]:checked {
            accent-color: #ff6b6b;
        }
        .option-content {
            display: flex;
            align-items: center;
            font-size: 1.1rem;
        }
        .option-icon {
            margin-right: 12px;
            font-size: 1.2rem;
            color: #666;
        }
        .submit-btn {
            background: linear-gradient(45deg, #28a745, #20c997);
            border: none;
            padding: 15px 30px;
            font-size: 1.2rem;
            font-weight: 600;
            border-radius: 25px;
            color: white;
            width: 100%;
            margin: 20px 0;
            transition: all 0.3s ease;
            box-shadow: 0 8px 25px rgba(40, 167, 69, 0.3);
        }
        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 35px rgba(40, 167, 69, 0.4);
            background: linear-gradient(45deg, #218838, #1dc5a3);
        }
        .result-alert {
            border-radius: 15px;
            padding: 20px;
            margin: 20px 0;
            text-align: center;
            font-weight: 600;
            font-size: 1.1rem;
        }
        .action-buttons {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }
        .action-btn {
            flex: 1;
            min-width: 150px;
            padding: 12px 20px;
            border-radius: 20px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-leaderboard {
            background: linear-gradient(45deg, #17a2b8, #20c997);
            border: none;
            color: white;
        }
        .btn-leaderboard:hover {
            background: linear-gradient(45deg, #138496, #1dc5a3);
            transform: translateY(-2px);
        }
        .btn-logout {
            background: linear-gradient(45deg, #dc3545, #fd7e14);
            border: none;
            color: white;
        }
        .btn-logout:hover {
            background: linear-gradient(45deg, #c82333, #e8650e);
            transform: translateY(-2px);
        }
        .paw-print {
            color: #feca57;
            margin: 0 5px;
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
        }
    </style>
</head>
<body>
    <div class="container game-container">
        <div class="game-card">
            <div class="game-header">
                <h2 class="game-title">
                    <i class="fas fa-question-circle"></i>
                    ¿Qué raza es este perro?
                    <span class="paw-print">🐾</span>
                </h2>
                <div class="score-display">
                    <i class="fas fa-star"></i> ${puntuacion} puntos
                </div>
            </div>

            <div class="dog-image-container">
                <img src="${imagenUrl}" alt="Imagen de perro" class="img-fluid dog-image">
            </div>

            <div class="options-container">
                <form method="post" action="JuegoServlet">
                    <input type="hidden" name="razaCorrecta" value="${razaCorrecta}">
                    
                    <c:forEach var="option" items="${opciones}" varStatus="status">
                        <div class="option-card">
                            <input type="radio" name="respuesta" id="option${status.index}" 
                                   value="${option}" required style="margin-right: 15px;">
                            <label for="option${status.index}" class="option-content">
                                <span class="option-icon">🐕</span>
                                ${option}
                            </label>
                        </div>
                    </c:forEach>

                    <button type="submit" class="submit-btn">
                        <i class="fas fa-paper-plane me-2"></i>Enviar Respuesta
                    </button>
                </form>

                <c:if test="${not empty resultado}">
                    <div class="alert result-alert ${resultado eq '¡Correcto!' ? 'alert-success' : 'alert-danger'}">
                        <i class="fas ${resultado eq '¡Correcto!' ? 'fa-check-circle' : 'fa-times-circle'} me-2"></i>
                        ${resultado}
                    </div>
                </c:if>

                <div class="action-buttons">
                    <form method="get" action="PuntuacionServlet" style="flex: 1;">
                        <button type="submit" class="btn btn-leaderboard action-btn">
                            <i class="fas fa-trophy me-2"></i>Ver Ranking
                        </button>
                    </form>
                    <form method="get" action="../shared/LogoutServlet" style="flex: 1;">
                        <button type="submit" class="btn btn-logout action-btn">
                            <i class="fas fa-sign-out-alt me-2"></i>Salir
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>