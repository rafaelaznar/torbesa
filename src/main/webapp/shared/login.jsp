<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login - Country Borders Game</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    </head>

    <body class="bg-light">

        <jsp:include page="menu.jsp" />

        <div class="container d-flex align-items-center justify-content-center min-vh-100">
            <div class="row w-100 justify-content-center">
                <div class="col-md-6 col-lg-4">
                    <div class="card shadow-lg">
                        <div class="card-body">
                            <h2 class="card-title text-center mb-4">Login</h2>
                            <form method="post" action="LoginServlet">
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Login</button>
                            </form>
                            
                            
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger mt-3 text-center">${error}</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="text-center mt-3">
                        <a href="../index.jsp" class="text-secondary">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>