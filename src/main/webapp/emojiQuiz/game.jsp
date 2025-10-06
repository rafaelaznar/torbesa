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
                    <label
                      class="form-check-label fs-5"
                      for="option${status.index}"
                    >
                      ${option}
                    </label>
                  </div>
                </c:forEach>

                <button type="submit" class="btn btn-primary mt-3">
                  Submit answer
                </button>
              </form>

              <c:if test="${not empty message}">
                <div class="alert alert-info mt-4">${message}</div>
                <form action="landing.jsp" method="get">
                  <button type="submit" class="btn btn-success mt-2">
                    <i class="bi bi-arrow-right-circle"></i> Ir a la página
                    principal
                  </button>
                </form>
              </c:if>
            </div>
            <div class="mt-4 text-center">
              <span class="fw-bold">Score:</span>
              <span class="badge bg-primary">${score}</span>
            </div>
            <form method="get" action="ScoreServlet" class="mt-3">
              <button type="submit" class="btn btn-outline-info w-100">
                View High Scores
              </button>
            </form>
            <form method="get" action="../shared/LogoutServlet" class="mt-2">
              <button type="submit" class="btn btn-outline-danger w-100">
                Logout
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
