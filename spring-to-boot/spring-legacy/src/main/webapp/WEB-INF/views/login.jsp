<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <title>Login page</title>
</head>
<body>
Login
<br>
<form action="/loginProcess" method="post">
    <input type="text" name="uesrname" placeholder="Type markruler"/>
    <input type="password" name="password" placeholder="Type password"/>
    <button type="submit">Sign in</button>
</form>
</body>
</html>
