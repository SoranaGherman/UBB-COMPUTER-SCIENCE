<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Success Page</title>
</head>
<body>

<h2>List of Products</h2>
<form action="${pageContext.request.contextPath}/shop-controller" method="get">
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <% for (com.example.shop.model.Product product: (java.util.ArrayList<com.example.shop.model.Product>) request.getAttribute("products")) { %>
        <tr>
            <td><%= product.getName()%></td>
            <td><%= product.getDescription()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</form>

<form action="product-controller" method="get">
    <label for="search_product_name">Enter name:</label>
    <input type="text" id="search_product_name" name="search_product_name">
    <input type="submit" value="SEARCH PRODUCT"/>
</form>

<form action="product-controller" method="post">
    <label for="product_name">Enter name:</label>
    <input type="text" id="product_name" name="product_name">
    <label for="product_description">Enter description:</label>
    <input type="text" id="product_description" name="product_description">
    <input type="submit" value="ADD PRODUCT"/>
</form>

<form action="order-controller" method="post">
    <input type="submit" value="TAKE AN ORDER"/>
</form>

<form action="submit-controller" method="post">
    <input type="submit" value="SUBMIT ORDER"/>
</form>

</body>
</html>
