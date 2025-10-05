<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </style>
</head>
<body>
    <div class="container">
        <!-- titulo -->
        <h1 style="text-align:center">Who's That Pokémon?</h1>
        <!--  -->
        <c:choose>
            <c:when test="${not empty error}">
                <p style="color:red">${error}</p>
            </c:when>
        </c:choose>

        <c:set var="p" value="${sessionScope.whosthatpokemon_current}" />

        <c:if test="${empty p}">
            <p>No hay Pokémon cargado. <a href="/torbesa/whosthatpokemon/GameServlet?new=true">Cargar uno</a></p>
        </c:if>

        <c:if test="${not empty p}">
            <img src="${p.spriteUrl}" class="sprite ${revealed ? 'reveal' : ''}" alt="pokemon silhouette" />

            <form method="post" action="/torbesa/whosthatpokemon/GameServlet" class="guess-form">
                <input type="text" name="guess" placeholder="Escribe el nombre..." required />
                <button type="submit">Adivinar</button>
            </form>

            <div class="result">
                <c:if test="${not empty guess}">
                    <c:if test="${correct}">
                        <p style="color:green">¡Correcto! Era <strong><c:out value="${p.name}"/></strong>.</p>
                        <p><a href="/torbesa/whosthatpokemon/GameServlet?new=true">Siguiente Pokémon</a></p>
                    </c:if>
                    <c:if test="${not correct}">
                        <p style="color:red">Incorrecto. Era <strong><c:out value="${p.name}"/></strong>.</p>
                        <p><a href="/torbesa/whosthatpokemon/GameServlet?new=true">Probar con otro</a></p>
                    </c:if>
                </c:if>
            </div>
        </c:if>

        <p style="text-align:center;margin-top:1rem"><a href="/torbesa/whosthatpokemon/highscores.jsp">Ver puntuaciones</a></p>
    </div>
</body>
</html>
