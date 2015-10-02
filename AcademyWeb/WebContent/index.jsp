<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="project.entity.Stock, java.util.List, project.business.MasterBeanLocal, javax.naming.InitialContext"
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
	window.onload = function () {

		var dps = []; // dataPoints

		var chart = new CanvasJS.Chart("chartContainer",{
			title :{
				text: "Moving Average"
			},			
			data: [{
				type: "line",
				dataPoints: dps 
			}]
		});

		var xVal = 0;
		var yVal = 100;	
		var updateInterval = 100;
		var dataLength = 500; // number of dataPoints visible at any point

		var updateChart = function (count) {
			count = count || 1;
			// count is number of times loop runs to generate random dataPoints.
			
			for (var j = 0; j < count; j++) {	
 				yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
 				dps.push({
 					x: xVal,
 					y: yVal
 				});
 				xVal++;
 			};
 			if (dps.length > dataLength)
			{
				dps.shift();				
			}
			
			chart.render();		

		};

		// generates first set of dataPoints
		updateChart(dataLength); 

		// update chart after specified time. 
		setInterval(function(){updateChart()}, updateInterval); 

	}
</script>
<script type="text/javascript" src="bootstrap/js/canvasjs.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197" style="z-index: 9999; width: 100%;">
  <ul class="nav navbar-nav">
    <li class="active" style="background-color: cyan;"><a href="#">Trading Home</a></li>
    <li><a href="TransactionsPage.html">Transactions</a></li>

  </ul>
</nav>

<div class="container-fluid" style="width: 1200px; border: 3px solid white;">

<div class="container-fluid" style="float: left; width: 50%; border: 2px solid orange;">

		<h1 align="center">Stock information</h1>
			
			<div id="left-div" class="col-sm-2 first-stock"
				style="background-color: black; border: 3px solid cyan;">
				<h3 align="center">
					<span class="label label-info" style="border-radius: 20px;">
						<% 
							InitialContext context = new InitialContext();
							MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
							List<Stock> av = bean.retrieveMostRecent("AV");
							for(Stock s: av) {
								out.print(s.getStockSymbol());
							}
						%>
					</span>
				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: 
						<% 
							for(Stock s: av) {
							out.print(s.getDayLow());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: 
						<% 
							for(Stock s: av) {
							out.print(s.getDayHigh());
							}
						%>
						</span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: 
						<% 
							for(Stock s: av) {
							out.print(s.getBidPrice());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask 
						<% 
							for(Stock s: av) {
								out.print(s.getAskPrice());
							}
						%>
						</span>
					</h5>
				</div>

				<div class="dropdown">

					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
  				<ul class="dropdown-menu"></ul>
				</div>

				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group">
							<button type="submit" class="btn btn-primary">Buy</button>
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
						List<Stock> bp = bean.retrieveMostRecent("BP");
						for(Stock s: bp) {
							out.print(s.getStockSymbol());
						}
					%>
					</span>
				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: 
						<% 
							for(Stock s: bp) {
							out.print(s.getDayLow());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: 
						<% 
							for(Stock s: bp) {
							out.print(s.getDayHigh());
							}
						%>
						</span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid: 
						<% 
							for(Stock s: bp) {
							out.print(s.getBidPrice());
							}
						%>
						</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask 
						<% 
							for(Stock s: bp) {
								out.print(s.getAskPrice());
							}
						%>
						</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop- " type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">

					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
					<span class="label label-info" style="border-radius: 20px;">AAPL</span>
				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: </span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: </span>
					</h5>
				</div>
				
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-default">Bid</span>
					</h5>
					<h5 style="float: right">
						<span class="label label-default">Ask</span>
					</h5>
				</div>

				<div class="dropdown">
					<button class="btn btn-primary btn-sm drop-" type="button" data-toggle="dropdown">Quantity
					<span class="caret"></span></button>
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
  <table class="table table-striped" style="border: solid 3px cyan;">
    <thead>
      <tr>
        <th style="color: cyan;">Transaction ID</th>
        <th style="color: cyan;">Stock Symbol</th>
        <th style="color: cyan;">Action</th>
        <th style="color: cyan;">Volume</th>
        <th style="color: cyan;">Price</th>
        <th style="color: cyan;">Time</th>
        <th style="color: cyan;">Current Position</th>
        
      </tr>
    </thead>
    <tbody>
      <tr style="background-color: #04B45F;">
        <td>1</td>
        <td>AAPL</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr style="background-color: #5882FA;">
        <td>2</td>
        <td>AAPL</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr style="background-color: #04B45F;">
        <td>3</td>
        <td>AAPL</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr style="background-color: #5882FA;">
        <td>4</td>
        <td>AAPL</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr style="background-color: #04B45F;">
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
  
  <div class="container-fluid" style="float: right; width: 50%; border: 2px solid orange; padding-bottom: 15px; padding-top: 15px;">

	<div id="chartContainer" style="height: 300px; width:100%; background-color: black;">
	</div>

</div>

  
  <h2 style="color: white; text-align: center;">Trading Strategy Manager</h2>
  <p style="color: white; text-align: center;">Select what trading strategy you would like to implement</p>
  <form role="form">
    <div class="checkbox">
      <label style="text-align: center;"><input type="checkbox" value="" style="color: white;">Two Moving Averages</label>
      <!-- Trigger the modal with a button -->
<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-cog"></span>
settings</button>

<div class="dropdown">
					<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Select Company
					<span class="caret"></span></button>
  				<ul class="dropdown-menu"></ul>
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
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
      
</div> 
    
  </form>
</body>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootstrap/js/bootstrap.min.js"></script>
</html>