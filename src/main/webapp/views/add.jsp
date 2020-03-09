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
</head>
<body>
<div class="container">
    <div class="row h-100 justify-content-center align-items-center">
        <form action="add" class="form-horizontal" method="post">
            <div class="row justify-content-center">
                <div class="header-h1">
                    <h1>ADD NEW USER</h1>
                </div>
            </div>
            <c:if test="${not empty error}">
                <div class="row justify-content-center align-self-center mb-1">
                    <div class="badge badge-warning">${error}</div>
                </div>
            </c:if>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="text" name="login"
                           id="login" placeholder="Login" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="password" name="password"
                           id="password" placeholder="Password" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="password" name="passwordAgain"
                           id="passwordAgain" placeholder="Confirm Password" class="form-control"
                           autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="email" name="email"
                           id="email" placeholder="Email" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="text" name="firstName"
                           id="firstName" placeholder="First Name" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="text" name="lastName"
                           id="lastName" placeholder="Last Name" class="form-control" autofocus>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-20">
                    <input type="date" name="birthday" id="birthday" class="form-control"
                           placeholder="Birthday" autofocus>
                </div>
            </div>

            <div class="form-group justify-content-center">
                <label for="roleSelector" class="col-sm-2 col-form-label">Role</label>
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
            </div>
            <div class="row justify-content-center">
                <button type="submit" class="btn btn-primary btn-lg mr-1">ADD</button>
                <a href="admin" class="btn btn-primary btn-lg">BACK</a>
            </div>
        </form>
    </div>
</div>

<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-2.1.3.min.js"></script>
</body>
</html>
