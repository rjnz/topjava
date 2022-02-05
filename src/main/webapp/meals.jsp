<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.web.MealServlet" %>
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
    <%
        List<Meal> meals = (List<Meal>) request.getAttribute("meals");
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        for (Meal meal : meals) {
            if (caloriesSumByDate.get(meal.getDate()) > MealServlet.caloriesPerDay) {
    %>
    <tr style="color: red"><%} else {%>
    <tr style="color: green"><%}%>
        <td><%=meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))%>
        </td>
        <td><%=meal.getDescription()%>
        </td>
        <td><%=meal.getCalories()%>
        </td>
        <td>Edit</td>
        <td>Delete</td>
    </tr>
    <%}%>
    </tbody>
</table>
</body>
</html>