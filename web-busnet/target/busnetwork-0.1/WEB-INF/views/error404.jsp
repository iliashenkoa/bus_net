<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>404</title>
<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<img src="${contextPath}/resources/img/404.png" style="width: 450px;"
		alt="404">
	<div class="text-center">
		<button class="btn btn-raised btn-secondary btn-lg" type="submit"
			name="back" onclick="history.back()">Go back a page</button>
	</div>
	<p>
	<div class="text-center">
		<button class="btn btn-raised btn-primary btn-lg" type="submit"
			name="back" onclick="window.location.href='/busnetwork/welcome'">Go
			homepage</button>
	</div>
</body>
</html>
