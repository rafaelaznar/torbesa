<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Who's That Pokemon - Juego</title>
    <style>
        body{ font-family: Arial, Helvetica, sans-serif; background: #0b2545; color:#fff; padding:2rem; }
        .container{ max-width:720px; margin:0 auto; background:#fff; color:#0b2545; padding:2rem; border-radius:8px; }
        .sprite{ width:300px; height:300px; display:block; margin:0 auto 1rem auto; object-fit:contain; filter: brightness(0) saturate(100%); }
        .reveal{ filter: none; }
        .guess-form{ display:flex; gap:.5rem; justify-content:center; margin-top:1rem; }
        input[type=text]{ padding:.5rem; width:60%; }
        button{ padding:.5rem 1rem; background:#0b2545; color:#fff; border:none; border-radius:4px; cursor:pointer; }
        .result{ text-align:center; margin-top:1rem; }
        img{
            image-rendering: pixelated;
            image-rendering: crisp-edges;
            user-select: none;
        }
        .btn {
            display: inline-block;
            padding: .5rem 1.2rem;
            background: #0b2545;
            color: #fff;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-weight: bold;
        }
        .btn:hover {
            background: #19376d;
        }
        .volver-landing {
            background: #fa1313;
        }
        .volver-landing:hover {
            background: #ff4141;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- titulo -->
        <h1 style="text-align:center">Who's That Pokémon?</h1>
        <!-- si hay algún error aparecerá en pantalla -->
        <c:choose>
            <c:when test="${not empty error}">
                <p style="color:red">${error}</p>
            </c:when>
        </c:choose>

        <!-- coge el pokemon actual guardado en la sesión -->
        <c:set var="p" value="${sessionScope.whosthatpokemon_current}" />

        <!-- si no hay pokemon nos redirije al GameServlet para así cargar uno -->
        <c:if test="${empty p}">
            <p>No hay Pokémon cargado. <a href="/torbesa/whosthatpokemon/GameServlet?new=true">Cargar uno</a></p>
        </c:if>
        <!-- si hay pokemon lo mostramos -->
        <c:if test="${not empty p}">
            <!-- ponemos la imagen, revelated es false tendrá una clase para que el sprite salga negro -->
            <img src="${p.spriteUrl}" class="sprite ${revealed ? 'reveal' : ''}" alt="pokemon silhouette" />
            <!-- input y botón para poner el nombre del pokemon y lo envíe al GameServlet, comprovando si está correcto -->
            <c:if test="${empty guess}">
                <form method="post" action="/torbesa/whosthatpokemon/GameServlet" class="guess-form">
                    <input type="text" name="guess" placeholder="Escribe el nombre..." required />
                    <button type="submit">Adivinar</button>
                </form>
            </c:if>
            <!-- resultado -->
            <div class="result">
                <c:if test="${not empty guess}">
                    <c:if test="${correct}">
                        <p style="color:green">¡Correcto! Era <strong><c:out value="${p.name}"/></strong>.</p>
                        <p><a href="/torbesa/whosthatpokemon/GameServlet?new=true"><button class="btn">Siguiente Pokémon</button></a></p>
                    </c:if>
                    <c:if test="${not correct}">
                        <p style="color:red">Incorrecto. Era <strong><c:out value="${p.name}"/></strong>.</p>
                        <p><a href="/torbesa/whosthatpokemon/GameServlet?new=true"><button class="btn">Probar con otro</button></a></p>
                    </c:if>
                </c:if>
            </div>
        </c:if>

        <p style="text-align:center;margin-top:1rem"><a href="/torbesa/whosthatpokemon/ScoreServlet"><button class="btn">Ver puntuaciones</button></a></p>
        <p style="text-align:center;margin-top:1rem"><a href="/torbesa/whosthatpokemon/landingpokemon.jsp"><button class="btn volver-landing">Volver a la Landing</button></a></p>
    </div>
</body>
</html>
