<%@page import="javax.naming.Context"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="project.entity.Stock, project.entity.Transaction, java.util.List, project.business.*, javax.naming.InitialContext, javax.ejb.EJB"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Stocks Homepage</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/Project.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- <script type="text/javascript">
	var auto_refresh = setInterval(
	function ()
	{
	   $('#left-div').load('HomePage.jsp');
	}, 10000); // refresh every 10000 milliseconds
</script> -->

<script type="text/javascript">
	window.onload(function() {

		var dps = []; // dataPoints

		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : "Stock Performance"
			},
			data : [ {
				type : "line",
				dataPoints : dps
			} ]
		});

		var xVal = 0;
		var yVal = 100;
		var updateInterval = 100;
		var dataLength = 500; // number of dataPoints visible at any point

		var updateChart = function(count) {
			count = count || 1;
			// count is number of times loop runs to generate random dataPoints.

			for (var j = 0; j < count; j++) {
				yVal = yVal + Math.round(5 + Math.random() * (-5 -5));
				dps.push({
					x : xVal,
					y : yVal
				});
				xVal++;
			}
			;
			if (dps.length > dataLength) {
				dps.shift();
			}

			chart.render();

		};

		// generates first set of dataPoints
		updateChart(dataLength);

		// update chart after specified time. 
		setInterval(function() {
			updateChart()
		}, updateInterval);

	});
</script>

<script type="text/javascript" src="bootstrap/js/canvasjs.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197" style="z-index: 9999; width: 100%;">
  <ul class="nav navbar-nav">
    <li class="active"><a href="#">Trading Home</a></li>
    <li><a href="TransactionsPage.jsp">Transactions</a></li>

		</ul>
	</nav>

	<div class="container-fluid"
		style="width: 1200px; border: 3px solid white;">

		<div class="container-fluid"
			style="float: left; width: 50%; border: 2px solid orange;">

			<h1 align="center">Stock information</h1>

			<div id="left-div" class="col-sm-2 first-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							InitialContext context = new InitialContext();
							StockBeanLocal bean = (StockBeanLocal) context
									.lookup("java:comp/env/ejb/Stock");
							List<Stock> ibm = bean.retrieveMostRecent("IBM");
							for (Stock s : ibm) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : ibm) {
								out.print(s.getDayLow());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : ibm) {
								out.print(s.getDayHigh());
							}
						%>
						</span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : ibm) {
								out.print(s.getBidPrice());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask <%
							for (Stock s : ibm) {
								out.print(s.getAskPrice());
							}
						%>
						</span>
					</h5>
				</div>
				<div class="dropdown">

					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" style="background-color: black;">
						<li><a href="#">100</a></li>
						<li><a href="#">200</a></li>
						<li><a href="#">300</a></li>
						<li><a href="#">400</a></li>
						<li><a href="#">500</a></li>
						<li><a href="#">600</a></li>
						<li><a href="#">700</a></li>
						<li><a href="#">800</a></li>
						<li><a href="#">900</a></li>
						<li><a href="#">1000</a></li>
					</ul>
				</div>

				<%
					InitialContext context2 = new InitialContext();
					TransactionBeanLocal Bean = (TransactionBeanLocal) context2
							.lookup("java:comp/env/ejb/Transaction");
				%>
				
				

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">

							<button type="submit" class="btn btn-primary" onclick="<%
									
							for(Stock s: ibm) {
					 							Transaction t = new Transaction();
					 							t.setStockid(s.getStockId());
					 							t.setStockSymbol(s.getStockSymbol());
					 							t.setPrice(s.getBidPrice());
												t.setTranstype("BUY");								 
												Bean.saveTransaction(t);
												} %>">Buy</button>

							<button type="submit" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>


			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> msft = bean.retrieveMostRecent("MSFT");
							for (Stock s : msft) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : msft) {
								out.print(s.getDayLow());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : msft) {
								out.print(s.getDayHigh());
							}
						%>
						</span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : msft) {
								out.print(s.getBidPrice());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask <%
							for (Stock s : msft) {
								out.print(s.getAskPrice());
							}
						%>
						</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop- " type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary" name=>Sell</button>
						</div>
					</div>
				</div>
			</div>


			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> goog = bean.retrieveMostRecent("GOOG");
							for (Stock s : goog) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : goog) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : goog) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : goog) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : goog) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>


			<!-- 		</div> -->
			<!-- 		<div class="row" style="width: 720px; border: 3px solid white;"> -->

			<div class="col-sm-2 first-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> ge = bean.retrieveMostRecent("GE");
							for (Stock s : ge) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : ge) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : ge) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : ge) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : ge) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> tsco = bean.retrieveMostRecent("YHOO");
							for (Stock s : tsco) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : tsco) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : tsco) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : tsco) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : tsco) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> csco = bean.retrieveMostRecent("CSCO");
							for (Stock s : csco) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : csco) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : csco) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : csco) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : csco) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">

					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>
			<!-- 		</div> -->

			<!-- 		<div class="row" style="width: 720px; border: 3px solid white;"> -->

			<div class="col-sm-2 first-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> aapl = bean.retrieveMostRecent("AAPL");
							for (Stock s : aapl) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : aapl) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : aapl) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : aapl) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : aapl) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> bab = bean.retrieveMostRecent("BAB");
							for (Stock s : bab) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : bab) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : bab) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : bab) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : bab) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-2 other-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<%
							List<Stock> ba = bean.retrieveMostRecent("AV");
							for (Stock s : ba) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : ba) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : ba) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: <%
							for (Stock s : ba) {
								out.print(s.getBidPrice());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask: <%
							for (Stock s : ba) {
								out.print(s.getAskPrice());
							}
						%></span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button"
						data-toggle="dropdown">
						Quantity <span class="caret"></span>
					</button>
					<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Buy</button>
							<button type="button" class="btn btn-primary">Sell</button>
						</div>
					</div>
				</div>
			</div>

			<!-- 		</div> -->
			<!--  <h7>Last 5 Transactions</h7>           -->
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Transaction ID</th>
						<th>Stock Symbol</th>
						<th>Action</th>
						<th>Volume</th>
						<th>Price</th>
						<th>Time</th>
						<th>Current Position</th>

					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>AAPL</td>
						<td>Buy</td>
						<td>100</td>
						<td>8.99</td>
						<td>12:00am</td>
						<td>Closed</td>
					</tr>
					<tr>
						<td>2</td>
						<td>AAPL</td>
						<td>Buy</td>
						<td>100</td>
						<td>8.99</td>
						<td>12:00am</td>
						<td>Closed</td>
					</tr>
					<tr>
						<td>3</td>
						<td>AAPL</td>
						<td>Buy</td>
						<td>100</td>
						<td>8.99</td>
						<td>12:00am</td>
						<td>Closed</td>
					</tr>
					<tr>
						<td>4</td>
						<td>AAPL</td>
						<td>Buy</td>
						<td>100</td>
						<td>8.99</td>
						<td>12:00am</td>
						<td>Closed</td>
					</tr>
					<tr>
						<td>5</td>
						<td>AAPL</td>
						<td>Buy</td>
						<td>100</td>
						<td>8.99</td>
						<td>12:00am</td>
						<td>Closed</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="container-fluid"
			style="float: right; width: 50%; border: 2px solid orange; padding-bottom: 15px; padding-top: 15px;">

			<div id="chartContainer"
				style="height: 300px; width: 100%; background-color: black;">
			</div>

			<h2 style="color: white; text-align: center;">Trading Strategy
				Manager</h2>
			<p style="color: white; text-align: center;">Select what trading
				strategy you would like to implement</p>
			<!-- 		<form role="form"> -->
			<div class="checkbox">
				<label style="text-align: center;"><input type="checkbox"
					value="" style="color: white;">Two Moving Averages</label>
				<!-- Trigger the modal with a button -->
				<button type="button" class="btn btn-info btn-sm"
					data-toggle="modal" data-target="#myModal">
					<span class="glyphicon glyphicon-cog"></span> settings
				</button>

				<div class="dropdown">
					<button class="btn btn-primary dropdown-toggle" type="button"
						data-toggle="dropdown">
						Select Company <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
						<li><a href="#">
								<%
									for (Stock s : goog) {
										out.print(s.getStockSymbol());
									}
								%>
						</a></li>
					</ul>
				</div>

			</div>




			<!-- Modal -->
			<div id="myModal" class="modal fade" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Change Settings</h4>
						</div>
						<div class="modal-body">
							<p>To be added...</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
