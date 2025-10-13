<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Emoji Quiz Game</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
      rel="stylesheet"
    />
  </head>

  <body class="bg-light">
    <jsp:include page="../shared/menu.jsp" />

    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card shadow-lg">
            <div class="card-body text-center">
              <h3 class="card-title mb-4">Adivina la película:</h3>
              <p class="fs-4"><strong>${question.question}</strong></p>

              <form action="../emojiQuiz/play" method="post">
                <input type="hidden" name="questionId" value="${question.id}" />

                <c:forEach var="option" items="${options}" varStatus="status">
                  <div class="form-check text-start mb-3">
                    <input
                      class="form-check-input"
                      type="radio"
                      name="selectedOption"
                      id="option${status.index}"
                      value="${option}"
                      required
                    />
                    <label class="form-check-label fs-5" for="option${status.index}">${option}</label>
                  </div>
                </c:forEach>

                <button type="submit" class="btn btn-success w-100 mt-3 shadow-sm">
                  <i class="bi bi-arrow-right-circle me-2"></i> Submit Answer
                </button>
              </form>

              <div class="mt-4 text-center">
                <span class="fw-bold">Score:</span>
                <span class="badge bg-primary">${score}</span>
              </div>

              <form method="get" action="../emojiQuiz/ScoreServlet" class="mt-3">
                <button type="submit" class="btn btn-info text-white w-100 shadow-sm">
                  <i class="bi bi-trophy me-2"></i> View High Scores
                </button>
              </form>

              <form method="get" action="../shared/LogoutServlet" class="mt-2">
                <button type="submit" class="btn btn-danger w-100 shadow-sm">
                  <i class="bi bi-box-arrow-right me-2"></i> Logout
                </button>
              </form>
            </div>

        </div>
      </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
