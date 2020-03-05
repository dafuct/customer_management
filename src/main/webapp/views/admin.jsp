<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head>
<body>
<div class="row justify-content-center align-self-center">
    <div class="w-75 p-3">
        <div class="d-flex justify-content-between">
            <div>
                <p><a href="add">Add new user</a></p>
            </div>
            <div>
                <p>Admin: ${admin.lastName} (<a href="logout">exit</a>)</p>
            </div>
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Login</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Age</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${list}">
                <tr>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.age}"/></td>
                    <td><c:out value="${user.role.name}"/></td>
                    <td>
                        <div class="d-flex justify-content-between">
                            <form action="edit" method="get">
                                <input type="hidden" name="user_login" value="${user.login}">
                                <button type="submit">Edit</button>
                            </form>
                            <form onsubmit="return confirm('Are you sure?')" action="delete"
                                  method="post">
                                <input type="hidden" name="user_login" value="${user.login}">
                                <button type="submit">Delete</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <c:if test="${not empty error}">
                <div class="row justify-content-center align-self-center mb-1">
                    <div class="badge badge-warning">${error}</div>
                </div>
            </c:if>
        </table>
    </div>
</div>
</body>
</html>