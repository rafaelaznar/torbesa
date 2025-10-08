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
                <title>High Scores - Country Capital Game</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"
                    rel="stylesheet">
            </head>

            <body class="bg-light">

                <jsp:include page="../shared/menu.jsp" />

                <div class="container py-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-10">

                            <!-- High Scores Table -->
                            <jsp:include page="leaderboard.jsp" />

                                <!-- if there is any session offer logout button -->
                                <c:if test="${not empty user}">
                                    <form method="get" action="LogoutServlet" class="d-inline">
                                        <button type="submit" class="btn btn-outline-danger btn-lg">
                                            <i class="bi bi-box-arrow-right"></i> Logout
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../shared/footer.jsp" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>