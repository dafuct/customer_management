<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add client</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
          id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-2.1.3.min.js"></script>

</head>
<body>
<div class="container">
    <div class="row h-100 justify-content-center align-items-center">
        <form action="edit" class="form-horizontal" method="post">
            <div class="row justify-content-center">
                <div class="header-h1">
                    <h1>EDIT USER</h1>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="row justify-content-center align-self-center mb-1">
                    <div class="badge badge-warning">${error}</div>
                </div>
            </c:if>

            <input type="hidden" name="id" value="${id}">
            <!-- Login -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="login_span">Login</span>
                </div>
                <input type="text" id="login" class="form-control" value="${login}" name="login"
                       readonly
                       placeholder="Login" aria-label="Login" aria-describedby="basic-addon1">
            </div>

            <!-- Password -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="password_span">Password</span>
                </div>
                <input type="password" id="password" class="form-control" value="${password}"
                       name="password"
                       placeholder="Password" aria-label="Password" aria-describedby="basic-addon1">
            </div>
            <!-- Password Again -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="passwordAgain_span">Confirm Password</span>
                </div>
                <input type="password" id="passwordAgain" class="form-control"
                       value="${passwordAgain}"
                       name="passwordAgain"
                       placeholder="Confirm Password" aria-label="Password"
                       aria-describedby="basic-addon1">
            </div>

            <!-- Email -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="email_span">Email</span>
                </div>
                <input type="email" id="email" class="form-control" value="${email}"
                       name="email"
                       placeholder="Email" aria-label="Email" aria-describedby="basic-addon1">
            </div>

            <!-- First name -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="firstName_span">First name</span>
                </div>
                <input type="text" id="firstName" class="form-control" value="${firstName}"
                       name="firstName"
                       placeholder="First name" aria-label="First Name"
                       aria-describedby="basic-addon1">
            </div>

            <!-- Last name -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="lastName_span">Last name</span>
                </div>
                <input type="text" id="lastName" class="form-control" value="${lastName}"
                       name="lastName"
                       placeholder="last name" aria-label="Last name"
                       aria-describedby="basic-addon1">
            </div>

            <!-- Birthday -->
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="birthday_span">Birthday</span>
                </div>
                <input type="date" id="birthday" class="form-control" value="${birthday}"
                       name="birthday"
                       placeholder="Birthday" aria-label="Birthday" aria-describedby="basic-addon1">
            </div>

            <!-- Role -->
            <div class="form-group">
                <label for="roleSelector" class="col-sm-10 col-form-label">Role</label>
                <div class="col-sm-15">
                    <select class="form-control" id="roleSelector" name="role">
                        <c:choose>
                            <c:when test="${roleName eq 'admin'}">
                                <option>admin</option>
                                <option>client</option>
                            </c:when>
                            <c:otherwise>
                                <option>client</option>
                                <option>admin</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="container">
                    <div class="row justify-content-center mt-2">
                        <button type="submit" class="btn btn-primary btn-lg mr-1">OK</button>
                        <a href="admin" class="btn btn-primary btn-lg">Cancel</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
