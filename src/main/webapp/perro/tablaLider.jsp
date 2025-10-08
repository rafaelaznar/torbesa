<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    .leaderboard-table {
        border-radius: 15px;
        overflow: hidden;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        margin: 0;
    }
    .leaderboard-table thead th {
        background: linear-gradient(45deg, #667eea, #764ba2);
        color: white;
        border: none;
        padding: 20px 15px;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 1px;
        font-size: 0.9rem;
    }
    .leaderboard-table tbody tr {
        transition: all 0.3s ease;
        border-bottom: 1px solid #e9ecef;
    }
    .leaderboard-table tbody tr:hover {
        background: linear-gradient(45deg, #f8f9fa, #e9ecef);
        transform: translateX(5px);
    }
    .leaderboard-table tbody td {
        padding: 18px 15px;
        vertical-align: middle;
        border: none;
    }
    .position-badge {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 35px;
        height: 35px;
        border-radius: 50%;
        font-weight: 700;
        color: white;
        font-size: 1rem;
    }
    .pos-1 { 
        background: linear-gradient(45deg, #ffd700, #ffed4e); 
        color: #2d3436;
        box-shadow: 0 4px 15px rgba(255, 215, 0, 0.4);
    }
    .pos-2 { 
        background: linear-gradient(45deg, #c0c0c0, #e8e8e8); 
        color: #2d3436;
        box-shadow: 0 4px 15px rgba(192, 192, 192, 0.4);
    }
    .pos-3 { 
        background: linear-gradient(45deg, #cd7f32, #deb887); 
        color: white;
        box-shadow: 0 4px 15px rgba(205, 127, 50, 0.4);
    }
    .pos-other { 
        background: linear-gradient(45deg, #74b9ff, #0984e3);
        box-shadow: 0 4px 15px rgba(116, 185, 255, 0.4);
    }
    .player-name {
        font-weight: 600;
        color: #2d3436;
        font-size: 1.1rem;
    }
    .score-display {
        background: linear-gradient(45deg, #00b894, #00cec9);
        color: white;
        padding: 8px 16px;
        border-radius: 20px;
        font-weight: 600;
        display: inline-block;
    }
    .tries-display {
        background: linear-gradient(45deg, #fd79a8, #fdcb6e);
        color: white;
        padding: 8px 16px;
        border-radius: 20px;
        font-weight: 600;
        display: inline-block;
    }
    .date-display {
        color: #636e72;
        font-size: 0.9rem;
    }
    .dog-icon {
        color: #feca57;
        margin-right: 8px;
    }
</style>

<table class="table leaderboard-table">
    <thead>
        <tr>
            <th><i class="fas fa-hashtag me-2"></i>Pos</th>
            <th><i class="fas fa-user me-2"></i>Jugador</th>
            <th class="text-center"><i class="fas fa-star me-2"></i>Puntos</th>
            <th class="text-center"><i class="fas fa-target me-2"></i>Intentos</th>
            <th class="text-center"><i class="fas fa-calendar me-2"></i>Fecha</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="score" items="${highScores}" varStatus="status">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${status.index == 0}">
                            <span class="position-badge pos-1">
                                <i class="fas fa-crown"></i>
                            </span>
                        </c:when>
                        <c:when test="${status.index == 1}">
                            <span class="position-badge pos-2">2</span>
                        </c:when>
                        <c:when test="${status.index == 2}">
                            <span class="position-badge pos-3">3</span>
                        </c:when>
                        <c:otherwise>
                            <span class="position-badge pos-other">${status.index + 1}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <span class="player-name">
                        <i class="fas fa-dog dog-icon"></i>
                        ${score.username}
                    </span>
                </td>
                <td class="text-center">
                    <span class="score-display">${score.score}</span>
                </td>
                <td class="text-center">
                    <span class="tries-display">${score.tries}</span>
                </td>
                <td class="text-center">
                    <span class="date-display">
                        <i class="fas fa-clock me-1"></i>
                        <fmt:formatDate value="${score.timestampAsDate}" pattern="dd/MM/yy"/>
                    </span>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>