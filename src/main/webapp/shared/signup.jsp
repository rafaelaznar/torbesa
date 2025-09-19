<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up - torbesa</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow">
                        <div class="card-body">
                            <h2 class="mb-4 text-center">Sign Up</h2>
                            <form action="signup" method="post" id="signupForm">
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                                <button type="submit" class="btn btn-warning w-100">Sign Up</button>
                            </form>
                            <c:if test="${not empty info}">
                                <div class="alert alert-success mt-3 text-center">${info}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger mt-3 text-center">${error}</div>
                            </c:if>
                            <div class="mt-3 text-center">
                                <a href="login.jsp">Already have an account? Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            async function sha256(str) {
                const encoder = new TextEncoder();
                const data = encoder.encode(str);
                const hashBuffer = await window.crypto.subtle.digest('SHA-256', data);
                return Array.from(new Uint8Array(hashBuffer)).map(b => b.toString(16).padStart(2, '0')).join('');
            }
            document.getElementById('signupForm').addEventListener('submit', async function (e) {
                const pwdInput = document.getElementById('password');
                if (pwdInput.value.length > 0) {
                    e.preventDefault();
                    const hash = await sha256(pwdInput.value);
                    pwdInput.value = hash;
                    this.submit();
                }
            });
        </script>
    </body>

    </html>