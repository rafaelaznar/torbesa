<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>torbesa - Welcome</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .hero-section {
            background: linear-gradient(135deg, #1f2a4a 0%, #3c4e91 100%);
            color: #ffffff;
            padding: 80px 0;
        }

        .game-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .game-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
        }

        .footer {
            background-color: #2c3e50;
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="menu.jsp" />

<section class="hero-section text-center">
    <div class="container">
        <h1 class="display-4 fw-bold mb-3">
            <i class="bi bi-joystick"></i> Welcome back,
            <c:out value="${sessionScope.sessionUser.username}" />!
        </h1>
        <p class="lead fs-4">Keep exploring and improving your projects.</p>
        <p class="fs-5">Play, iterate, and share your games with the community.</p>
    </div>
</section>

<div class="container my-4 d-flex justify-content-center">
    <a href="../shared/LogoutServlet" class="btn btn-danger btn-lg" style="min-width: 200px;">
        <i class="bi bi-box-arrow-right"></i> Logout
    </a>
</div>

<section class="py-5">
    <div class="container">
        <h2 class="text-center mb-5 display-6">
            <i class="bi bi-collection-play"></i> Jump into a game
        </h2>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-globe-americas text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-primary mb-3">Country Capitals Game</h5>
                        <p class="card-text text-muted mb-4">
                            Test your geography knowledge and guess the capital city for each country.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Rafael Aznar</p>
                        <a href="../capitals/landing.jsp" class="btn btn-primary btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-calculator text-warning" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-warning mb-3">Math Challenge</h5>
                        <p class="card-text text-muted mb-4">
                            Solve timed arithmetic problems and climb the leaderboard.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Hector Jose Fernandez</p>
                        <a href="../math/landing.jsp" class="btn btn-warning btn-lg w-100 text-dark">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-question-circle text-warning" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-warning mb-3">Trivial</h5>
                        <p class="card-text text-muted mb-4">
                            Answer general knowledge questions and keep your streak alive.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Marcos Pallás</p>
                        <a href="../trivial/landing_j.jsp" class="btn btn-primary btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-cpu text-danger" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-danger mb-3">Computer Science Trivia</h5>
                        <p class="card-text text-muted mb-4">
                            Take on tech-flavored questions and prove your CS knowledge.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Reyna</p>
                        <a href="../trivialReyna/landing.jsp" class="btn btn-danger btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-translate text-success" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-success mb-3">Language Quiz</h5>
                        <p class="card-text text-muted mb-4">
                            Match words and expressions across multiple languages.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Ariel Sempertegui</p>
                        <a href="../sempertegui/languageLanding.jsp" class="btn btn-success btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-stack text-warning" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-warning mb-3">Trivia (API)</h5>
                        <p class="card-text text-muted mb-4">
                            Pull fresh questions from Open Trivia DB and see how far you can go.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Ivan Afonso</p>
                        <a href="<%= request.getContextPath() %>/trivia" class="btn btn-outline-warning btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-shield-shaded text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-primary mb-3">Who's That Pokémon?</h5>
                        <p class="card-text text-muted mb-4">
                            Identify the Pokémon from its silhouette before the time runs out.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Alan Alcañiz</p>
                        <a href="../whosthatpokemon/landingpokemon.jsp" class="btn btn-primary btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-lightning-charge-fill text-warning" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-warning mb-3">Pokémon Ability Quiz</h5>
                        <p class="card-text text-muted mb-4">
                            Pick the correct ability for each Pokémon and chase a perfect score.
                        </p>
                        <p class="card-text text-muted mb-4 small">By David Gabriel Calinescu</p>
                        <a href="../pokemon/landing.jsp" class="btn btn-warning btn-lg w-100 text-dark">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-emoji-smile text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-primary mb-3">Emoji Quiz</h5>
                        <p class="card-text text-muted mb-4">
                            Decode movie titles from emoji combinations and beat the ranking.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Vladislav Uski</p>
                        <a href="../emojiQuiz/landing.jsp" class="btn btn-primary btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-fire text-success" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-success mb-3">Demon Slayer Quiz</h5>
                        <p class="card-text text-muted mb-4">
                            Demon Slayer fans: answer lore questions and keep your streak going.
                        </p>
                        <a href="../kimetsu/landing.jsp" class="btn btn-success btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-stars text-info" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-info mb-3">Honkai Star Rail</h5>
                        <p class="card-text text-muted mb-4">
                            Guess each character's element and climb the leaderboard.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Álvaro Contreras</p>
                        <a href="../find_it_alvaro/landing.jsp" class="btn btn-info btn-lg w-100 text-dark">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-magic text-primary" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-primary mb-3">Harry Potter Quiz</h5>
                        <p class="card-text text-muted mb-4">
                            Decide which Hogwarts house each character belongs to and earn points.
                        </p>
                        <a href="../harrypotter/landing.jsp" class="btn btn-primary btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-arrow-through-heart text-dark" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-dark mb-3">Taylor Swift Test</h5>
                        <p class="card-text text-muted mb-4">
                            Match the song to its album in this music-themed challenge.
                        </p>
                        <p class="card-text text-muted mb-4 small">By Lucia Castañera</p>
                        <a href="../swift/landing.jsp" class="btn btn-dark btn-lg w-100">
                            <i class="bi bi-play-fill"></i> Play Now
                        </a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card game-card h-100 border-0 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="mb-3">
                            <i class="bi bi-joystick text-secondary" style="font-size: 3rem;"></i>
                        </div>
                        <h5 class="card-title text-secondary mb-3">More games coming soon</h5>
                        <p class="card-text text-muted mb-4">
                            Stay tuned for new class projects landing on torbesa.
                        </p>
                        <button class="btn btn-outline-secondary btn-lg w-100" disabled>
                            <i class="bi bi-hourglass-split"></i> In progress
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
