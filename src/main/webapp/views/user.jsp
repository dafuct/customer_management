<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>Hello page</title>
</head>
<body>
<div class="container mt-5">
    <div class="row h-100 justify-content-center align-items-center">
        <h1>Hello, ${user.firstName}!</h1>
    </div>
</div>

<div class="container mt-5">
    <div class="row h-100 justify-content-center align-items-center">
        <p>
            Click <a href="logout">here</a> to logout
        </p>
    </div>
</div>
</body>
</html>