<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    if (session.getAttribute("sessionUser") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<style>
    .ranking-card {
        background: rgba(255, 255, 255, 0.95);
        border-radius: 20px;
        border: 3px solid #ffcc02;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    }
    .ranking-header {
        background: linear-gradient(45deg, #ffcc02, #ff6b6b);
        color: white;
        border-radius: 17px 17px 0 0;
    }
    .table-pokemon {
        border-radius: 0 0 17px 17px;
        overflow: hidden;
    }
    .pokemon-rank-1 {
        background: linear-gradient(45deg, #ffd700, #ffed4e) !important;
        color: #333;
    }
    .pokemon-rank-2 {
        background: linear-gradient(45deg, #c0c0c0, #e8e8e8) !important;
        color: #333;
    }
    .pokemon-rank-3 {
        background: linear-gradient(45deg, #cd7f32, #daa520) !important;
        color: white;
    }
    .pokemon-badge {
        background: linear-gradient(45deg, #4ecdc4, #45b7d1);
        color: white;
        border-radius: 15px;
    }
</style>

<div class="card ranking-card shadow-sm border-0 mt-4">
    <div class="card-header ranking-header">
        <h5 class="card-title mb-0">
            <i class="bi bi-trophy"></i> 🏆 Pokémon Masters Ranking
        </h5>
    </div>
            <div class="card-body p-0">
                <div class="table-responsive table-pokemon">
                    <table class="table table-hover table-striped mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col"><i class="bi bi-hash"></i> 🏅 Rank</th>
                                <th scope="col"><i class="bi bi-person-fill"></i> 👤 Trainer</th>
                                <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> ⭐ Score</th>
                                <th scope="col" class="text-center"><i class="bi bi-target"></i> 🎯 Tries</th>
                                <th scope="col" class="text-center"><i class="bi bi-percent"></i> 📊 Accuracy</th>
                                <th scope="col"><i class="bi bi-calendar-event"></i> 📅 Date/Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="score" items="${highScores}" varStatus="status">
                                <tr class="${status.index < 3 ? (status.index == 0 ? 'pokemon-rank-1' : (status.index == 1 ? 'pokemon-rank-2' : 'pokemon-rank-3')) : ''}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${status.index == 0}">
                                                <h5><i class="bi bi-trophy-fill text-warning"></i> 🥇 #1</h5>
                                            </c:when>
                                            <c:when test="${status.index == 1}">
                                                <h5><i class="bi bi-award-fill text-secondary"></i> 🥈 #2</h5>
                                            </c:when>
                                            <c:when test="${status.index == 2}">
                                                <h5><i class="bi bi-award text-warning"></i> 🥉 #3</h5>
                                            </c:when>
                                            <c:otherwise>
                                                <h5>📍 #${status.index + 1}</h5>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><strong class="h5">${score.username}</strong></td>
                                    <td class="text-center">
                                        <h4><span class="badge pokemon-badge">${score.score}</span></h4>
                                    </td>
                                    <td class="text-center">
                                        <h4><span class="badge bg-info">${score.tries}</span></h4>
                                    </td>
                                    <td class="text-center">
                                        <h4>
                                            <span class="badge bg-primary">
                                                <fmt:formatNumber value="${score.score / score.tries * 100}"
                                                    maxFractionDigits="1" />%
                                            </span>
                                        </h4>
                                    </td>
                                    <td><small class="text-muted h5">
                                        <fmt:formatDate value="${score.timestampAsDate}"
                                            pattern="MMM dd, yyyy 'at' HH:mm" />
                                    </small></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>