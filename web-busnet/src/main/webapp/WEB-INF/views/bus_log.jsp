<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buses trips logs</title>

<link rel="shortcut icon" href="${contextPath}/resources/img/ico.ico"
	type="image/x-icon" />
<link href="${contextPath}/resources/css/color-theme-w3.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/datatable.css" rel="stylesheet">
<link
	href="${contextPath}/resources/bootstrap-material-design/assets/css/docs.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/theme.default.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${contextPath}/resources/bootstrap-material-design/assets/js/src/application.js"></script>
<script src="${contextPath}/resources/js/jquery.min.g.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.18/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.18/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/plug-ins/1.10.19/pagination/input.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/plug-ins/1.10.19/pagination/full_numbers_no_ellipses.js"></script>
</head>
<body>
	<div class="generic-container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="text-uppercase font-weight-light w3-theme-l3"
					style="width: 100%">List of buses trips records</h3>
			</div>
			<table id="resTable"
				class="display table table-striped table-bordered"
				style="width: 100%">
				<thead>
					<tr>
						<th class="text-center">id</th>
						<th class="text-center">Time moment</th>
						<th class="text-center">Bus stop id</th>
						<th class="text-center">Route num</th>
						<th class="text-center">Bus id</th>
						<th class="text-center">Passengers count entry</th>
						<th class="text-center">Passengers count exit</th>
						<th class="text-center">Passengers count in</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th class="text-center">id</th>
						<th class="text-center">Time moment</th>
						<th class="text-center">Bus stop id</th>
						<th class="text-center">Route num</th>
						<th class="text-center">Bus id</th>
						<th class="text-center">Passengers count entry</th>
						<th class="text-center">Passengers count exit</th>
						<th class="text-center">Passengers count in</th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = eval('${busLogList}');
			var table = $('#resTable').DataTable({
				"aaData" : data,
				"order" : [ [ 0, 'desc' ] ],
				"columnDefs" : [ {
					"searchable" : false,
					"targets" : [ 0, 2, 3, 4, 5, 6, 7 ]
				} ],
				"pagingType" : "full_numbers_no_ellipses",
				"aoColumns" : [ {
					"mData" : "id"
				}, {
					"mData" : "timeMoment"
				}, {
					"mData" : "busStopId"
				}, {
					"mData" : "routeNum"
				}, {
					"mData" : "busId"
				}, {
					"mData" : "passengersCountEntry"
				}, {
					"mData" : "passengersCountExit"
				}, {
					"mData" : "passengersCountIn"
				} ]
			});

		});
	</script>
</body>
</html>