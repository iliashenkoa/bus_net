<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fastest way</title>
<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />
<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${contextPath}/resources/bootstrap-material-design/assets/js/src/application.js"></script>
</head>
<body>
	<div class="panel-heading">
		<h3 class="text-uppercase font-weight-light w3-theme-l3"
			style="width: 100%">Fastest way</h3>
	</div>
	<c:if test="${empty stops}">
		<h4 class="text-uppercase" style="width: 100%; text-align: center">Network
			has no way for your input!</h4>
	</c:if>
	<c:if test="${not empty stops}">
		<c:forEach items="${stops}" var="stop">
			<button class="btn btn-outline-secondary"
				onclick="window.open('bus_stop?id='+${stop.id})">${stop.id}</button>
		</c:forEach>
	</c:if>
</body>
</html>