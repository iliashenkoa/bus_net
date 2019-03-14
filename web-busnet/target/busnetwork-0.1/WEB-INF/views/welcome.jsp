<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />

<script src="${contextPath}/resources/js/jquery.min.g.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<title>Start with busnet!</title>

<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<link href="https://unpkg.com/ionicons@4.4.4/dist/css/ionicons.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${contextPath}/resources/bootstrap-material-design/assets/js/src/application.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<script type="text/javascript">		
		$(function() {
  $('button[type=submit]').click(function(e) {
    e.preventDefault();
    $(this).prop('disabled',true);
    console.log('ava');
    var form = document.forms[0];
    var formData = new FormData(form);
    
    
    var ajaxReq = $.ajax({
      url : 'start_simula',
      type : 'POST',
      data : formData,
      cache : false,
      contentType : false,
      processData : false
    });
    
     ajaxReq.fail(function(jqXHR) {
      $('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+
      		' - '+jqXHR.statusText+')');
      $('button[type=submit]').prop('disabled',false);
    });
  });
  }
</script>

	<img src="${contextPath}/resources/img/bus_stop.png"
		style="width: 450px;" alt="bus stop">

	<nav class="navbar navbar-light w3-theme-l3">
		<div class="btn-group" role="group" aria-label="Basic example">
			<button class="btn active" type="button">
				<i class="icon ion-md-bus"></i>&nbsp;
				${pageContext.request.userPrincipal.name}
			</button>
			<button type="button" class="btn btn-dark"
				onclick="window.open('statistics')">Statistics</button>
			<button type="button" class="btn btn-dark"
				onclick="window.open('bus_log')">Buses logs</button>
			<button type="button" class="btn btn-dark"
				onclick="window.open('search_fast')">Search fastest way</button>

			<div class="container">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<form method="POST" action="${contextPath}/start_simula">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<button class="btn btn-dark" type="submit"><code>Start simulation</code></button>
 
					</form>
				</c:if>
			</div>
		</div>

		<div class="container">
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<form id="logoutForm" method="POST" action="${contextPath}/logout">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<button type="button" class="btn btn-outline-dark"
					onclick="document.forms['logoutForm'].submit()">Logout</button>
			</c:if>
		</div>
	</nav>
	<p>
	<p>

	<table id="resTable" class="table table-hover table-striped">
		<thead>
			<tr>
				<th>Bus route number</th>
				<th>Bus stop id</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:forEach items="${routes}" var="route">
					<tr>
						<td>
							<button class="btn btn-raised btn-secondary"
								onclick="window.open('bus_route?num='+${route.number})">${route.number}</button>
						</td>
						<td><c:forEach items="${route.stops}" var="stop">
								<button class="btn btn-outline-secondary"
									onclick="window.open('bus_stop?id='+${stop.id})">${stop.id}</button>
							</c:forEach></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</body>
</html>