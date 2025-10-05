<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>High Scores - Who's That Pokemon</title>
    <style> body{ font-family:Arial; padding:2rem; background:#0b2545; color:#fff } .card{background:#fff;color:#0b2545;padding:1rem;border-radius:8px;max-width:800px;margin:0 auto;} </style>
</head>
<body>
    <div class="card">
        <h2>High Scores</h2>
        <%-- Prefer request-scoped highScores (ScoreDto list), fallback to session simple list --%>
        <c:choose>
            <c:when test="${not empty requestScope.highScores}">
                <table style="width:100%;border-collapse:collapse">
                    <thead>
                        <tr>
                            <th style="text-align:left;padding:.5rem">Usuario</th>
                            <th style="text-align:center;padding:.5rem">Score</th>
                            <th style="text-align:center;padding:.5rem">Tries</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.highScores}" var="score">
                            <tr>
                                <td style="padding:.5rem"><c:out value="${score.username}"/></td>
                                <td style="text-align:center;padding:.5rem"><c:out value="${score.score}"/></td>
                                <td style="text-align:center;padding:.5rem"><c:out value="${score.tries}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <c:set var="scores" value="${sessionScope.whosthatpokemonScores}" />
                <c:if test="${empty scores}">
                    <p>No hay puntuaciones todavía.</p>
                </c:if>
                <c:if test="${not empty scores}">
                    <ol>
                        <c:forEach items="${scores}" var="s">
                            <li><c:out value="${s}"/></li>
                        </c:forEach>
                    </ol>
                </c:if>
            </c:otherwise>
        </c:choose>
        <p><a href="/torbesa/whosthatpokemon/GameServlet?new=true">Jugar</a></p>
    </div>
</body>
</html>
