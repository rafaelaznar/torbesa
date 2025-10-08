<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>🐕 Dog Quiz - Prueba tus conocimientos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: white;
        }
        .hero-section {
            padding: 80px 0;
            text-align: center;
        }
        .hero-title {
            font-size: 4rem;
            font-weight: 700;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
            animation: fadeInUp 1s ease-out;
        }
        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 3rem;
            opacity: 0.9;
            animation: fadeInUp 1s ease-out 0.2s both;
        }
        .btn-play {
            background: linear-gradient(45deg, #ff6b6b, #feca57);
            border: none;
            padding: 15px 40px;
            font-size: 1.2rem;
            font-weight: 600;
            border-radius: 50px;
            transition: all 0.3s ease;
            box-shadow: 0 8px 25px rgba(255, 107, 107, 0.3);
            animation: fadeInUp 1s ease-out 0.4s both;
        }
        .btn-play:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(255, 107, 107, 0.4);
            background: linear-gradient(45deg, #ff5252, #ffb74d);
        }
        .features-section {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            margin-top: 60px;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
        .feature-card {
            background: rgba(255, 255, 255, 0.15);
            border-radius: 15px;
            padding: 30px 20px;
            text-align: center;
            transition: all 0.3s ease;
            border: 1px solid rgba(255, 255, 255, 0.2);
            height: 100%;
        }
        .feature-card:hover {
            transform: translateY(-10px);
            background: rgba(255, 255, 255, 0.25);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
        }
        .feature-icon {
            font-size: 3rem;
            margin-bottom: 20px;
            color: #feca57;
        }
        .dog-emoji {
            font-size: 2rem;
            animation: bounce 2s infinite;
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
        .stats-section {
            margin-top: 40px;
        }
        .stat-item {
            text-align: center;
            padding: 20px;
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: 700;
            color: #feca57;
        }
        .floating-dog {
            position: absolute;
            font-size: 2rem;
            animation: float 3s ease-in-out infinite;
            opacity: 0.3;
        }
        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(-20px); }
        }
    </style>
</head>
<body>
    <!-- Floating Dogs -->
    <div class="floating-dog" style="top: 10%; left: 10%;">🐕</div>
    <div class="floating-dog" style="top: 20%; right: 15%; animation-delay: -1s;">🐶</div>
    <div class="floating-dog" style="top: 60%; left: 5%; animation-delay: -2s;">🦮</div>
    <div class="floating-dog" style="bottom: 20%; right: 10%; animation-delay: -0.5s;">🐕‍🦺</div>

    <div class="container">
        <div class="hero-section">
            <span class="dog-emoji">🐕</span>
            <h1 class="hero-title">Dog Quiz Challenge</h1>
            <p class="hero-subtitle">¿Eres un verdadero experto en razas caninas? ¡Demuéstralo!</p>
            <a href="JuegoServlet" class="btn btn-play text-white">
                <i class="fas fa-play me-2"></i>¡Comenzar Aventura!
            </a>
        </div>

        <div class="features-section">
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-brain feature-icon"></i>
                        <h4>Desafía tu Mente</h4>
                        <p>Más de 50 razas diferentes para identificar</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-trophy feature-icon"></i>
                        <h4>Compite</h4>
                        <p>Sube al top del ranking y demuestra quién sabe más</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-heart feature-icon"></i>
                        <h4>Aprende</h4>
                        <p>Descubre nuevas razas y características únicas</p>
                    </div>
                </div>
            </div>

            <div class="stats-section">
                <div class="row">
                    <div class="col-md-4 stat-item">
                        <div class="stat-number">50+</div>
                        <div>Razas de Perros</div>
                    </div>
                    <div class="col-md-4 stat-item">
                        <div class="stat-number">∞</div>
                        <div>Diversión Garantizada</div>
                    </div>
                    <div class="col-md-4 stat-item">
                        <div class="stat-number">100%</div>
                        <div>Amor por los Perros</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>