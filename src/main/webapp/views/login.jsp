<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Welcome!</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet"
          id="bootstrap-css"/>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="css/style.css"/>
</head>

<body>
<div class="container">
    <div class="card card-container">
        <form action="login" class="form-signin" method="post">
            <input type="text" class="form-control" placeholder="Login" required
                   name="login"
                   onfocus="this.placeholder = ''"
                   onblur="this.placeholder = 'Login'">
            <input type="password" class="form-control" placeholder="Password" required
                   name="password"
                   onfocus="this.placeholder = ''"
                   onblur="this.placeholder = 'Password'">

            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Login</button>
            <c:if test="${not empty error}">
                <div class="row justify-content-center align-self-center">
                    <div class="badge badge-warning">${error}</div>
                </div>
            </c:if>
        </form>
    </div>
</div>
</body>
</html>