<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("sessionUser")==null) {
        response.sendRedirect(request.getContextPath()+"/index.jsp");
        return;
    }
%>
<html>
<head>
<title>Math Game</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Solve the Math Problem</h2>
    <div class="card mt-4 p-4 text-center">
        <h3>Question: ${question}</h3>
        <form method="post" action="GameServlet">
            <input type="number" step="0.01" name="answer" class="form-control mt-3" placeholder="Enter your answer" required>
            <button type="submit" class="btn btn-success mt-3 w-100">Submit Answer</button>
        </form>
        <c:if test="${not empty correct}">
            <div class="mt-3">
                <span class="badge ${correct ? 'bg-success' : 'bg-danger'}">
                    ${correct ? 'Correct!' : 'Wrong!'}
                </span>
            </div>
        </c:if>
        <div class="mt-4">
            Score: <span class="badge bg-primary">${score.score}</span> | Tries: <span class="badge bg-info">${score.tries}</span>
        </div>
    </div>
</div>
</body>
</html>
