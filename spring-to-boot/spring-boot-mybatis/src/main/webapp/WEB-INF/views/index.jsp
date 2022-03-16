<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <title>Welcome page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/index.css"/>
</head>
<body>
<div id="greeting">Hello, World!</div>
<a href="/users">users</a><br>
<a href="/unknown">unknown page</a>
</body>
</html>
