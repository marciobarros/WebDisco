<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>Error: ${requestScope["javax.servlet.error.status_code"]}</h1>
    <h2>URL: ${requestScope["javax.servlet.error.request_uri"]}</h2>
   	<h2>${requestScope["javax.servlet.error.exception"].message}</h2>
</body>
</html>