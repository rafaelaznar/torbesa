<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Resultado - Harry Potter Quiz</title>
</head>
<body>
    <h2><%= request.getAttribute("message") %></h2>

    <form action="GameServlet" method="get">
        <button type="submit">Jugar otra vez</button>
    </form>

    <form action="../index.jsp" method="get">
        <button type="submit">Volver al inicio</button>
    </form>
</body>
</html>

