<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bus stop</title>

<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />

<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="generic-container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h3 class="text-uppercase font-weight-light w3-theme-l3">Info
					of bus stop ${stop.name}</h3>
			</div>
			<table id="resTable" class="table">
				<thead>
					<tr>
						<th class="text-center">Id</th>
						<th class="text-center">Name</th>
						<th class="text-center">Parking space count</th>
						<th class="text-center">X coord</th>
						<th class="text-center">Y coord</th>
						<th class="text-center w3-theme-l1">Passenger traffic per
							hour</th>
						<th class="text-center">Bus routes</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center w3-theme-l3">${stop.id}</td>
						<td class="text-center">${stop.name}</td>
						<td class="text-center">${stop.parkingSpace}</td>
						<td class="text-center">${stop.xCoord}</td>
						<td class="text-center">${stop.yCoord}</td>
						<td class="text-center">${transpPas}</td>
						<td class="text-center"><c:forEach items="${stop.routes}"
								var="route">
								<button class="btn btn-raised btn-secondary"
									onclick="window.open('bus_route?num='+${route.number})">${route.number}</button>
							</c:forEach></td>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>