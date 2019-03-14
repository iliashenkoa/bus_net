<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bus routes</title>

<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />

<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${contextPath}/resources/bootstrap-material-design/assets/js/src/application.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="generic-container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-title">
				<h3 class="text-uppercase font-weight-light w3-theme-l3"
					style="width: 100%">Info of route #${route.number}</h3>
			</div>
			<table id="resTable" class="table">
				<thead>
					<tr>
						<th class="text-center">Number</th>
						<th class="text-center">Price</th>
						<th class="text-center">Roundabout</th>
						<th class="text-center">Time interval</th>
						<th class="text-center w3-theme-l1">Transported passengers
							per hour</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center w3-theme-l3">${route.number}</td>
						<td class="text-center">${route.price}</td>
						<td class="text-center">${route.isRoundabout == 1 ? 'yes' : 'no'}
						</td>
						<td class="text-center">${route.timeInterval}</td>
						<td class="text-center">${transpPas}</td>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>