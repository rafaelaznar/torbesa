<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <div class="card shadow-sm border-0 mt-4">
            <div class="card-header bg-warning text-dark">
                <h5 class="card-title mb-0">
                    <i class="bi bi-trophy"></i> Ranking
                </h5>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover table-striped mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th scope="col"><i class="bi bi-hash"></i> Ranking</th>
                                <th scope="col"><i class="bi bi-person-fill"></i> User</th>
                                <th scope="col" class="text-center"><i class="bi bi-star-fill"></i> Score</th>
                                <th scope="col" class="text-center"><i class="bi bi-target"></i> Tries</th>
                                <th scope="col" class="text-center"><i class="bi bi-percent"></i> Accuracy</th>
                                <th scope="col"><i class="bi bi-calendar-event"></i> Date/Time
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="score" items="${highScores}" varStatus="status">
                                <tr
                                    class="${status.index < 3 ? (status.index == 0 ? 'table-warning' : (status.index == 1 ? 'table-info' : 'table-light')) : ''}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${status.index == 0}">
                                                <h5><i class="bi bi-trophy-fill text-secondary"></i>
                                                    #1</h5>
                                            </c:when>
                                            <c:when test="${status.index == 1}">
                                                <h5><i class="bi bi-award-fill text-secondary"></i>
                                                    #2</h5>
                                            </c:when>
                                            <c:when test="${status.index == 2}">
                                                <h5><i class="bi bi-award text-secondary"></i>
                                                    #3
                                                </h5>
                                            </c:when>
                                            <c:otherwise>
                                                <h5>#${status.index + 1}</h5>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><strong class="h5">${score.username}</strong></td>
                                    <td class="text-center">
                                        <h4><span class="badge bg-success">${score.score}</span>
                                        </h4>
                                    </td>
                                    <td class="text-center">
                                        <h4><span class="badge bg-info">${score.tries}</span>
                                        </h4>
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
                                        </small>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>