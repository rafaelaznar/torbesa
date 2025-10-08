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
    <style>
        .house-option {
            transition: all 0.3s ease;
            border: 2px solid transparent !important;
        }
        
        .house-option:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            border: 2px solid #fff !important;
        }
        
        .house-option input[type="radio"]:checked + label {
            font-weight: bold;
        }
        
        .house-option:has(input[type="radio"]:checked) {
            border: 3px solid #fff !important;
            box-shadow: 0 6px 12px rgba(0,0,0,0.3);
            transform: translateY(-3px);
        }
    </style>
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
                                        String houseColor = "";
                                        String houseTextColor = "text-white";
                                        
                                        // Asignar colores según la casa
                                        switch(house.toLowerCase()) {
                                            case "gryffindor":
                                                houseColor = "bg-danger"; // Rojo
                                                break;
                                            case "ravenclaw":
                                                houseColor = "bg-primary"; // Azul
                                                break;
                                            case "slytherin":
                                                houseColor = "bg-success"; // Verde
                                                break;
                                            case "hufflepuff":
                                                houseColor = "bg-warning"; // Amarillo
                                                houseTextColor = "text-dark";
                                                break;
                                            default:
                                                houseColor = "bg-secondary";
                                                break;
                                        }
                                %>
                                    <div class="col">
                                        <div class="form-check d-flex align-items-center border rounded p-3 h-100 <%= houseColor %> <%= houseTextColor %> house-option" style="cursor: pointer;">
                                            <input class="form-check-input me-3" type="radio" name="houseGuess" value="<%= house %>" required id="house_<%= house %>">
                                            <label class="form-check-label h5 mb-0 <%= houseTextColor %>" for="house_<%= house %>" style="cursor: pointer; width: 100%;"><%= house %></label>
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

