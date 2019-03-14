<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search data</title>
<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />
<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<script src="${contextPath}/resources/js/jquery.min.g.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>


<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />
</head>
<body>
	<div class="panel-heading">
		<h3 class="text-uppercase font-weight-light w3-theme-l3"
			style="width: 100%">Search Fastest way</h3>
	</div>
	<script>
		function search() {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var stop_start = $("#stop_start").val();
			var stop_end = $("#stop_end").val();

			$.ajax({
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				type : "POST",
				url : "search",
				data : {
					stop_start : stop_start,
					stop_end : stop_end
				},
				error : function() {
					alert('error');
				}
			});
		}
	</script>
	<div id="container" class="container">
		<form name="search" method="post">

			<div class="form-group ">
				<label for="stop_start" class="bmd-label-static">Start</label>
				<!-- <input type="text"
					id="stop_start" name="stop_start" onblur="search()" required /> -->
				<select id="stop_start" name="stop_start" class="form-control">
					<c:forEach items="${ids}" var="id">
						<option value="${id}">${id}</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<label for="stop_end" class="bmd-label-static">End</label> <select
					id="stop_end" name="stop_end" class="form-control">
					<c:forEach items="${ids}" var="id">
						<option value="${id}">${id}</option>
					</c:forEach>
				</select>
			</div>
			<p>
			<div class="line submit text-center">
				<input type="submit" value="Search"
					class="btn btn-raised btn-primary" onclick="search()" />
				<p />
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</div>
		</form>
	</div>
</body>
</html>