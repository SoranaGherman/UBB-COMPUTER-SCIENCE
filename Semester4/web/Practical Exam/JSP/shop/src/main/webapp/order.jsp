<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Success Page</title>
</head>
<body>


<form action="order-controller" method="get">
  <label for="product_id">Enter product id:</label>
  <input type="text" id="product_id" name="product_id">
  <label for="quantity">Enter quantity:</label>
  <input type="text" id="quantity" name="quantity">
  <input type="submit" value="ADD TO BASKET"/>
</form>

</body>
</html>
