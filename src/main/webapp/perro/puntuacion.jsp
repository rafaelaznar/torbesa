<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <title>High Scores - Dog's Game</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
                    rel="stylesheet">
            </head>

            <body class="bg-light">

                <jsp:include page="../shared/menu.jsp" />

                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-10">

                            <!-- Header -->
                            <div class="text-center mb-4">
                                <h1 class="display-4 text-primary">
                                    <i class="bi bi-trophy-fill"></i> Game Results
                                </h1>
                            </div>

            <!-- Message Alert -->
            <c:if test="${not empty message}">
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <i class="bi bi-info-circle-fill"></i> ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Game Results Section -->
            <div class="card shadow-lg border-0 mb-4">
                <div class="card-header bg-gradient text-white" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                    <div class="row align-items-center">
                        <div class="col">
                            <h3 class="card-title mb-0">
                                <i class="bi bi-trophy-fill"></i> Game Results
                            </h3>
                        </div>
                        <div class="col-auto">
                            <span class="badge bg-light text-dark fs-6">
                                <i class="bi bi-calendar-event"></i>
                                <fmt:formatDate value="${userScore.timestampAsDate}" pattern="MMM dd, HH:mm"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="card-body p-4">
                    <div class="row g-4">
                        <!-- Your Answer Card -->
                        <div class="col-lg-6">
                            <div class="card border-0 bg-light h-100">
                                <div class="card-header bg-info text-white border-0">
                                    <h5 class="card-title mb-0">
                                        <i class="bi bi-question-circle-fill"></i> Your Answer
                                    </h5>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-borderless mb-0">
                                            <tbody>
                                                <tr>
                                                    <td class="fw-bold text-muted">
                                                        <i class="bi bi-geo-alt-fill"></i> Question:
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-primary fs-6">${country}</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="fw-bold text-muted">
                                                        <i class="bi bi-chat-square-text-fill"></i> Your Guess:
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-warning text-dark fs-6">${capitalGuess}</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="fw-bold text-muted">
                                                        <i class="bi bi-check-circle-fill"></i> Correct Answer:
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-success fs-6">${correctCapital}</span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Your Performance Card -->
                        <div class="col-lg-6">
                            <div class="card border-0 bg-light h-100">
                                <div class="card-header bg-success text-white border-0">
                                    <h5 class="card-title mb-0">
                                        <i class="bi bi-star-fill"></i> Your Performance
                                    </h5>
                                </div>
                                <div class="card-body">
                                    <div class="row text-center mb-3">
                                        <div class="col-4">
                                            <div class="border-end">
                                                <div class="display-6 text-success mb-1">${userScore.score}</div>
                                                <small class="text-muted fw-bold">
                                                    <i class="bi bi-star-fill"></i> Score
                                                </small>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div class="border-end">
                                                <div class="display-6 text-info mb-1">${userScore.tries}</div>
                                                <small class="text-muted fw-bold">
                                                    <i class="bi bi-target"></i> Tries
                                                </small>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div>
                                                <div class="display-6 text-primary mb-1">
                                                    <fmt:formatNumber
                                                        value="${userScore.score / userScore.tries * 100}"
                                                        maxFractionDigits="1" />%
                                                </div>
                                                <small class="text-muted fw-bold">
                                                    <i class="bi bi-percent"></i> Accuracy
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="my-3">
                                    <div class="text-center">
                                        <small class="text-muted">
                                            <i class="bi bi-clock-fill"></i>
                                            Last played: <fmt:formatDate value="${userScore.timestampAsDate}" pattern="MMM dd, yyyy 'at' HH:mm"/>
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-body text-center p-4">
                    <h5 class="card-title mb-3">
                        <i class="bi bi-joystick"></i> What's next?
                    </h5>
                    <div class="d-flex justify-content-center gap-3 flex-wrap">
                        <form method="get" action="JuegoServlet" class="d-inline">
                            <button type="submit" class="btn btn-primary btn-lg px-4 py-2">
                                <i class="bi bi-play-fill"></i> Play Again
                            </button>
                        </form>

                        <form method="get" action="PuntuacionServlet" class="d-inline">
                            <button type="submit" class="btn btn-outline-info btn-lg px-4 py-2">
                                <i class="bi bi-trophy"></i> View Leaderboard
                            </button>
                        </form>

                        <form method="get" action="../shared/LogoutServlet" class="d-inline">
                            <button type="submit" class="btn btn-outline-danger btn-lg px-4 py-2">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </button>
                        </form>
                    </div>
                </div>
            </div>                            <!-- High Scores Table -->
                            <jsp:include page="altaPuntuacion.jsp" />


                        </div>
                    </div>
                </div>
                <jsp:include page="../shared/footer.jsp" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>