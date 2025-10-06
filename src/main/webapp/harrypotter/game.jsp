<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Quiz de Harry Potter</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

    <jsp:include page="../shared/menu.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">

                <!-- Card -->
                <div class="card shadow-lg border-0 rounded-4">
                    <div class="card-body text-center p-5">

                        <!-- Icon -->
                        <div class="mb-4">
                            <i class="bi bi-magic text-primary" style="font-size: 3rem;"></i>
                        </div>

                        <!-- Pregunta -->
                        <h2 class="mb-4">¿A qué casa pertenece 
                            <span class="text-primary"><%= request.getAttribute("characterName") %></span>?
                        </h2>

                        <!-- Quiz Form -->
                        <form action="GameServlet" method="post">
                            <input type="hidden" name="characterName" value="<%= request.getAttribute("characterName") %>">

                            <div class="row row-cols-1 row-cols-md-2 g-3 mb-4">
                                <%
                                    java.util.List<String> options = (java.util.List<String>) request.getAttribute("options");
                                    for (String house : options) {
                                %>
                                    <div class="col">
                                        <div class="form-check d-flex align-items-center border rounded p-3 h-100">
                                            <input class="form-check-input me-3" type="radio" name="houseGuess" value="<%= house %>" required>
                                            <label class="form-check-label h5 mb-0"><%= house %></label>
                                        </div>
                                    </div>
                                <%
                                    }
                                %>
                            </div>

                            <button type="submit" class="btn btn-lg btn-primary w-100">
                                <i class="bi bi-check-circle"></i> Responder
                            </button>
                        </form>

                    </div>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="../shared/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

