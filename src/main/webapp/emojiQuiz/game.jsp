<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Emoji Quiz Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
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

                        <form action="checkAnswer" method="post">
                            <c:forEach var="option" items="${options}">
                                <div class="mb-3">
                                    <button type="submit" name="answer" value="${option}" 
                                            class="btn btn-outline-primary w-100 fs-5">
                                        ${option}
                                    </button>
                                </div>
                            </c:forEach>
                            <input type="hidden" name="questionId" value="${question.id}" />
                        </form>

                        <c:if test="${not empty message}">
                            <div class="alert alert-info mt-4">
                                ${message}
                            </div>
                            <!-- <form id="formAfterAnswer" action="emojiQuiz/play" method="post">
                                <button>
                                    <a href="game.jsp" class="btn btn-success mt-2">
                                        <i class="bi bi-arrow-repeat"></i> Jugar otra vez
                                    </a>
                                </button>
                            </form> -->
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
