<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<button>Add</button>
<br>
<table id="datatable" border="1">
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="dateTime" type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dateTime}"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>