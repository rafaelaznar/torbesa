<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Error</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="menu.jsp" />
        <h2>Error</h2>
        <div style="color:red;">${errorMessage}</div>
        <form method="get" action="login.jsp">
            <input type="submit" value="Back to Login">
        </form>
        <jsp:include page="footer.jsp" />
    </body>

    </html>