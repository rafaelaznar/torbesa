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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Who's That Pokemon?</title>
    <style>
        :root{
            --navy: #0b2545; /* azul marino */
            --accent: #103a66; /* azul oscuro secundario */
            --white: #ffffff;
            --black: #000000;
            --muted: #f4f7fb;
        }

        html,body{
            height:100%;
            margin:0;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial;
            background: linear-gradient(180deg, var(--navy) 0%, var(--accent) 100%);
            color:var(--white);
            -webkit-font-smoothing:antialiased;
        }

        .wrap{
            max-width:900px;
            margin:3rem auto;
            padding:2rem;
        }

        .card{
            background:var(--white);
            color:var(--navy);
            border-radius:10px;
            padding:2rem;
            box-shadow: 0 6px 20px rgba(0,0,0,0.25);
            border:4px solid rgba(0,0,0,0.08);
        }

        h1{ margin-top:0; color:var(--navy); }

        .controls{
            display:flex;
            gap:1rem;
            margin:1rem 0 1.5rem 0;
        }

        .btn{
            background:var(--navy);
            color:var(--white);
            border:2px solid var(--black);
            padding:.6rem 1rem;
            border-radius:6px;
            cursor:pointer;
            font-weight:600;
            transition: transform .08s ease, box-shadow .08s ease;
        }

        .btn.secondary{
            background:transparent;
            color:var(--navy);
            border:2px solid var(--navy);
        }

        .btn:hover{ transform: translateY(-2px); box-shadow: 0 6px 14px rgba(0,0,0,0.15); }

        .info{
            margin-top:1rem;
            background:var(--muted);
            padding:1rem;
            border-radius:6px;
            color:var(--navy);
            border:1px solid rgba(0,0,0,0.06);
        }

        p.lead{ margin:0 0 1rem 0; font-size:1.05rem; }

        @media (max-width:600px){
            .controls{ flex-direction:column; }
            .wrap{ margin:1rem; }
        }
    </style>
</head>
<body>
    <div class="wrap">
        <div class="card">
            <h1>Who's That Pokemon?</h1>
            <p class="lead">Aparecerá la silueta de un Pokémon de cualquier generación. ¡Adivínalo antes de que se acabe el tiempo!</p>
            <!-- los tres botones para acceder a el juego, al ranking y a el index -->
            <div class="controls">
                <a href="../whosthatpokemon/GameServlet"><button class="btn">Start</button></a>
                <a href="../whosthatpokemon/ScoreServlet"><button class="btn secondary">Top Scores</button></a>
                <a href="../shared/welcome.jsp"><button class="btn">Volver al index</button></a>
                <form action="../whosthatpokemon/DeleteScoreServlet" method="post" style="display:inline;">
                    <button type="submit" class="btn secondary" onclick="return confirm('¿Seguro que quieres borrar tu score?');">Borrar Score</button>
                </form>
            </div>

            <div class="info">
                <h3 style="margin-top:0">Info</h3>
                <p style="margin:0">- Se mostrará la silueta de un Pokémon. Escribe su nombre correcto para puntuar. Cuanto más rápido, más puntos.</p>
                <p style="margin:0">- En la puntuación podrás ver cuantos intentos has hecho y cuantos has adivinado. </p>
            </div>
        </div>
    </div>
</body>
</html>