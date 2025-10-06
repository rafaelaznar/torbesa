<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Pokémon Ability Quiz Game</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
            }
            .pokemon-hero {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 25px;
                border: 4px solid #ffcc02;
                box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            }
            .pokemon-title {
                background: linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1);
                -webkit-background-clip: text;
                background-clip: text;
                -webkit-text-fill-color: transparent;
                font-weight: bold;
            }
            .btn-start {
                background: linear-gradient(45deg, #ff6b6b, #ffcc02);
                border: none;
                color: white;
                font-weight: bold;
                border-radius: 25px;
                padding: 15px 30px;
                transition: all 0.3s ease;
            }
            .btn-start:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 25px rgba(255, 107, 107, 0.4);
                color: white;
            }
            .btn-scores {
                background: linear-gradient(45deg, #4ecdc4, #45b7d1);
                border: none;
                color: white;
                font-weight: bold;
                border-radius: 25px;
                padding: 15px 30px;
                transition: all 0.3s ease;
            }
            .btn-scores:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 25px rgba(78, 205, 196, 0.4);
                color: white;
            }
            .pokemon-card {
                background: rgba(255, 255, 255, 0.9);
                border-radius: 20px;
                border: 2px solid #4ecdc4;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../shared/menu.jsp" />

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center pokemon-hero p-5">
                    <h1 class="display-4 pokemon-title mb-4">
                        🎮 Welcome to the Pokémon Ability Quiz! ⚡
                    </h1>
                    <p class="lead text-muted mb-4">
                        🔍 Test your knowledge by guessing the abilities of various Pokémon! 
                        Can you become the ultimate Pokémon Master? 🏆
                    </p>
                    <a href="../pokemon/ServletJuego" class="btn btn-start btn-lg m-3">
                        🚀 Start Playing
                    </a>
                    <a href="../pokemon/PuntajeServlet" class="btn btn-scores btn-lg m-3">
                        🏅 Top Scores
                    </a>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-8 offset-md-2">
                    <div class="card pokemon-card shadow">
                        <div class="card-body">
                            <h5 class="card-title text-center mb-4" style="color: #2c5aa0; font-weight: bold;">
                                📋 How to Play
                            </h5>
                            <ul class="list-group list-group-flush text-start">
                                <li class="list-group-item border-0 bg-transparent">
                                    🔐 Log in with your username and password.
                                </li>
                                <li class="list-group-item border-0 bg-transparent">
                                    ⚡ Guess the Pokémon ability from the options provided.
                                </li>
                                <li class="list-group-item border-0 bg-transparent">
                                    🎯 Earn points for correct answers.
                                </li>
                                <li class="list-group-item border-0 bg-transparent">
                                    🏆 View high scores and challenge your friends!
                                </li>
                                <li class="list-group-item border-0 bg-transparent">
                                    📝 This code is MIT licensed.
                                </li>
                                <li class="list-group-item border-0 bg-transparent">
                                    💻 Use this code to learn Java, servlets, JSP, JSTL, sessions and more!
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../shared/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>