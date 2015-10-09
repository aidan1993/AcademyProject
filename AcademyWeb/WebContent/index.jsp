<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="project.entity.Stock, project.entity.Transaction, java.util.List, 
	project.business.MasterBeanLocal, javax.naming.InitialContext, javax.ejb.EJB, javax.naming.Context,
	java.util.List, project.strategies.TwoMovingAverage"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Stocks Homepage</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="bootstrap/css/Project.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script type="text/javascript" src="bootstrap/js/canvasjs.min.js"></script>
<script type="text/javascript">
	window.onload = function() {

		// dataPoints
		var dataPoints1 = [];
		var dataPoints2 = [];

		var symbol = "MSFT";

		var chart = new CanvasJS.Chart("chartContainer",{
			zoomEnabled: true,
			title: {
				text: "Share Value of Two Companies"		
			},
			toolTip: {
				shared: true
				
			},
			legend: {
				verticalAlign: "top",
				horizontalAlign: "center",
                                fontSize: 14,
				fontWeight: "bold",
				fontFamily: "calibri",
				fontColor: "dimGrey"
			},
			axisX: {
				title: "Two Moving Average for " + symbol
			},
			axisY:{
				prefix: '£',
				includeZero: false
			}, 
			data: [{ 
				// dataSeries1
				type: "line",
				xValueType: "number",
				showInLegend: true,
				name: "Short Average",
				dataPoints: dataPoints1
			},
			{				
				// dataSeries2
				type: "line",
				xValueType: "number",
				showInLegend: true,
				name: "Long Average" ,
				dataPoints: dataPoints2
			}],
          legend:{
            cursor:"pointer",
            itemclick : function(e) {
              if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
              }
              else {
                e.dataSeries.visible = true;
              }
              chart.render();
            }
          }
		});



		var updateInterval = 9000;
		// initial value
		var xValue = 0;
		var shortPrice = [];
		var longPrice = [];
		
		var updateChart = function (count) {
			count = count || 1;

			$.ajax({
				type : "GET",
				url : URL,
				cache : false,
				success : function(data) {
					shortPrice = data;
				},
				error : function(data) {
					console.log("Problem occurred");
				}
			});

			// count is number of times loop runs to generate random dataPoints. 

			for (var i = 0; i < shortPrice.length; i++) {
 				
 				// generating random values
 				xValue++;
 				
 				// pushing the new values
 				dataPoints1.push({
 					x: xValue,
 					y: shortPrice[i]
 				});
 				dataPoints2.push({
 					x: xValue,
 					y: longPrice
 				});
 			};
 
 			// updating legend text with  updated with y Value 
 			chart.options.data[0].legendText = " Short Price  £" + shortPrice;
 			chart.options.data[1].legendText = " Long Price B  £" + longPrice; 
 
 			chart.render();
 
 		};
 
 		// generates first set of dataPoints 
 		updateChart(1);	
 		 
 		// update chart after specified interval 
 		setInterval(function(){updateChart()}, updateInterval);

		var loop = 0;
		var URL = "rest/livefeed?loop=" + loop;
		function runFeed() {
			setTimeout(function() {
				$.ajax({
					type : "GET",
					url : URL,
					cache : false,
					success : function(data) {
						console.log("Live Feed Running");
						loop++;
					},
					error : function(data) {
						console.log("Problem occurred");
					}
				})

				runFeed()
			}, 10000);
		}

		runFeed();

		function baRefresh() {
			$("#left-div").load('index.jsp #left-div > *', function() {
				setTimeout(baRefresh, 10000);

			});

		}

		baRefresh();

		$("#strategy").submit(function() {
			if($.trim($('.formEl').val()) === "") {
				alert('Please enter a short and long length');
		        return false;
			}
		});

	}
</script>

</head>
<body>

	<nav class="navbar navbar-inverse" data-spy="affix"
		data-offset-top="197" style="z-index: 9999; width: 100%;">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Trading Home</a></li>
			<li class="inactive"><a href="TransactionsPage.jsp">Transactions</a></li>

		</ul>
	</nav>

	<div id="left-div" class="container-fluid"
		style="float: left; width: 50%; border: 1px solid #868b8c; padding-left: 20px;">

		<div class="center-container">
			<h1 align="center">
				Stock information
				<button type="button" class="btn btn-settings btn-sm"
					data-toggle="modal" data-target="#alterStockModal">Alter
					Grid</button>
			</h1>
		</div>
		<div class="center-container">
			<div class="stock-div col-sm-2 first-stock"
				style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">
					<%
						InitialContext context = new InitialContext();
						MasterBeanLocal bean = (MasterBeanLocal) context
								.lookup("java:comp/env/ejb/Master");
						out.print(bean.getDiv1());
						List<Stock> div1 = bean.retrieveMostRecent(bean.getDiv1());
					%>
				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div1) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div1) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div1) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div1) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>


				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv1">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv1">Sell</button>


						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-2 middle-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv2());
						List<Stock> div2 = bean.retrieveMostRecent(bean.getDiv2());
					%>

				</h3>

				<div class="row row-format">

					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div2) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div2) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh2" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div2) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div2) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>



				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv2">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv2">Sell</button>


						</div>
					</div>
				</div>
			</div>


			<div class="col-sm-2 last-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv3());
						List<Stock> div3 = bean.retrieveMostRecent(bean.getDiv3());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div3) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div3) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh3" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div3) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div3) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>




				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv3">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv3">Sell</button>


						</div>
					</div>
				</div>
			</div>
		</div>


		<!-- 		</div> -->
		<!-- 		<div class="row" style="width: 720px; border: 3px solid white;"> -->
		<div class="center-container">
			<div class="col-sm-2 first-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv4());
						List<Stock> div4 = bean.retrieveMostRecent(bean.getDiv4());
					%>

				</h3>
				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div4) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div4) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh4" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div4) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div4) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>



				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv4">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv4">Sell</button>


						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-2 middle-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv5());
						List<Stock> div5 = bean.retrieveMostRecent(bean.getDiv5());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div5) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div5) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh5" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div5) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div5) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>


				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv5">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv5">Sell</button>


						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-2 last-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv6());
						List<Stock> div6 = bean.retrieveMostRecent(bean.getDiv6());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div6) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div6) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh6" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div6) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div6) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>




				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv6">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv6">Sell</button>


						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 		</div> -->

		<!-- 		<div class="row" style="width: 720px; border: 3px solid white;"> -->
		<div class="center-container">
			<div class="col-sm-2 first-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv7());
						List<Stock> div7 = bean.retrieveMostRecent(bean.getDiv7());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div7) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div7) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh7" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div7) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div7) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>


				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv7">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv7">Sell</button>


						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-2 middle-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv8());
						List<Stock> div8 = bean.retrieveMostRecent(bean.getDiv8());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div8) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div8) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh8" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div8) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div8) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>



				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv8">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv8">Sell</button>


						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-2 last-stock" style="background-color: black;">
				<h3 align="center" style="font-weight: bold;">

					<%
						out.print(bean.getDiv9());
						List<Stock> div9 = bean.retrieveMostRecent(bean.getDiv9());
					%>

				</h3>

				<div class="row row-format">
					<h5 style="float: left">
						<span class="label label-success">Low: <%
							for (Stock s : div9) {
								out.print(s.getDayLow());
							}
						%></span>
					</h5>
					<h5 style="float: right">
						<span class="label label-warning">High: <%
							for (Stock s : div9) {
								out.print(s.getDayHigh());
							}
						%></span>
					</h5>
				</div>

				<div id="baRefresh9" class="row row-format">
					<h4 style="float: left">
						Bid:<br>
						<%
							for (Stock s : div9) {

								out.print(s.getBidPrice());
							}
						%>

					</h4>
					<h4 style="float: right">
						Ask:<br>
						<%
							for (Stock s : div9) {

								out.print(s.getAskPrice());
							}
						%>
					</h4>
				</div>




				<div class="row">
					<div class='wrapper text-center'>
						<div class="btn-group" style="width: 100%;">


							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#BuyModalDiv9">Buy</button>

							<button type="submit" style="width: 50%;"
								class="cloneBtn btn btn-primary" data-toggle="modal"
								data-target="#SellModalDiv9">Sell</button>


						</div>
					</div>
				</div>
			</div>
		</div>


		<!-- 		</div> -->
		<!--  <h7>Last 5 Transactions</h7>           -->
		<table id="myTable" class="table table-striped">
			<%
				List<Transaction> tr = bean.retrieveMostRecent1();
			%>
			<thead>
				<tr>
					<th>Transaction ID</th>
					<th>Stock Symbol</th>
					<th>Action</th>
					<th>Volume</th>
					<th>Price</th>
					<th>Time</th>
					<th>Strategy Used</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Transaction t : tr) {
				%>
				<tr>
					<td>
						<%
							out.print(t.getTransactionid());
						%>
					</td>
					<td>
						<%
							out.print(t.getStockSymbol());
						%>
					</td>
					<td>
						<%
							out.print(t.getTranstype());
						%>
					</td>
					<td>
						<%
							out.print(t.getVolume());
						%>
					</td>
					<td>
						<%
							out.print(t.getPrice());
						%>
					</td>
					<td>
						<%
							out.print(t.getTranstime());
						%>
					</td>
					<td>
						<%
							out.print(t.getStrategy());
						%>
					</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>

	<div class="container-fluid"
		style="float: right; width: 50%; border: 1px solid #868b8c; padding-bottom: 15px; padding-top: 15px;">

		<div id="chartContainer"
			style="height: 300px; width: 100%; background-color: black;"></div>

		<h2 style="color: white; text-align: center;">Trading Strategy
			Manager</h2>
		<p style="color: white; text-align: center;">Select what trading
			strategy you would like to implement</p>
		<!-- 		<form role="form"> -->
		<div class="checkbox">
			<label style="text-align: center;">Two Moving Averages</label>
			<!-- Trigger the modal with a button -->
			<button type="button" class="btn btn-info btn-sm" data-toggle="modal"
				data-target="#myModal">
				<span class="glyphicon glyphicon-cog"></span> settings
			</button>
		</div>
		<div>
			<%
				List<TwoMovingAverage> list = null;
				// retrieve your list from the request, with casting 
				if (request.getAttribute("movingAvgList") != null) {
					list = ((List<TwoMovingAverage>) request.getAttribute("movingAvgList"));
					// print the information about every category of the list
					for (TwoMovingAverage mAvg : list) {
						if (mAvg.isActive()) {
							out.println("<p>Two Moving Average running for "
									+ mAvg.getStock() + " with Short of " + mAvg.getShortLength() + 
									" minutes and Long of " + mAvg.getLongLength() + " minutes</p>");
						}
					}
				}
			%>
		</div>

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
					<form id="strategy" action="TwoMovingAverageServlet" method="post">
						<label>Active?</label> <input type="checkbox" name="activate"><br>
						<label for="selSymbol">Choose Symbol</label> <select
							class="form-control" style="display: inline-block; width: auto;"
							name="selSymbol">
							<option value="<%=bean.getDiv1()%>">
								<%
									out.print(bean.getDiv1());
								%>
							</option>
							<option value="<%=bean.getDiv2()%>">
								<%
									out.print(bean.getDiv2());
								%>
							</option>
							<option value="<%=bean.getDiv3()%>">
								<%
									out.print(bean.getDiv3());
								%>
							</option>
							<option value="<%=bean.getDiv4()%>">
								<%
									out.print(bean.getDiv4());
								%>
							</option>
							<option value="<%=bean.getDiv5()%>">
								<%
									out.print(bean.getDiv5());
								%>
							</option>
							<option value="<%=bean.getDiv6()%>">
								<%
									out.print(bean.getDiv6());
								%>
							</option>
							<option value="<%=bean.getDiv7()%>">
								<%
									out.print(bean.getDiv7());
								%>
							</option>
							<option value="<%=bean.getDiv8()%>">
								<%
									out.print(bean.getDiv8());
								%>
							</option>
							<option value="<%=bean.getDiv9()%>">
								<%
									out.print(bean.getDiv9());
								%>
							</option>
						</select><br> <label for="shortAvg">Short Average Length (Minutes)</label> <input
							type="text" class="formEl form-control"
							style="display: inline-block; width: auto; margin-top: 10px;"
							name="shortAvg" /><br> <label for="longAvg">Long
							Average Length (Minutes)</label> <input type="text" class="formEl form-control"
							style="display: inline-block; width: auto; margin: 10px 0px;"
							name="longAvg" /><br> <label for="volume">Volume of Stock</label> 
							<input type="text" class="formEl form-control"
							style="display: inline-block; width: auto; margin: 10px 0px;"
							name="volume" /><br><input type="submit"
							class="form-control" style="width: auto;"
							value="Confirm Settings" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>


	<div id="BuyModalDiv1" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>Purchase
						<div class="dropdown">
							<select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select>
						</div>
						of
						<%
							out.print(bean.getDiv1());
						%>
						Stock
						</p>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default" name="buyStockDiv1">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv1" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select>of
							<%
								out.print(bean.getDiv1());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">


						<button type="submit" class="btn btn-default" name="sellStockDiv1">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv2" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv2());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv2">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv2" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">

						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv2());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv2">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv3" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">

						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv3());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv3">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv3" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">

						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv3());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv3">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv4" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">

						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv4());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv4">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv4" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv4());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv4">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv5" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv5());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv5">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv5" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv5());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv5">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv6" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv6());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv6">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv6" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv6());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv6">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv7" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv7());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv7">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv7" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv7());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv7">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv8" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv8());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv8">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv8" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv8());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv8">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="BuyModalDiv9" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Purchase <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv9());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="buyStockDiv9">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="SellModalDiv9" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Settings</h4>
				</div>
				<form action="StockTransactionServlet" method="post">
					<div class="modal-body">
						<p>
							Sell <select class="form-control" name="txtQuantity"
								style="width: 25%">
								<option disabled="disabled" selected="selected">Select
									Quantity</option>
								<option>100</option>
								<option>200</option>
								<option>300</option>
								<option>400</option>
								<option>500</option>
								<option>600</option>
								<option>700</option>
								<option>800</option>
								<option>900</option>
								<option>1000</option>
							</select> of
							<%
								out.print(bean.getDiv9());
							%>
							Stock
						</p>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-default" name="sellStockDiv9">Confirm
							Transaction</button>

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>

	</div>

	<div id="alterStockModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<form action="EditStockSymbol" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Stock Preference</h4>
					</div>
					<div class="modal-body">
						<table style="position: center">
							<tr>
								<td><label for="usr">Stock 1</label> <input type="text"
									class="form-control" name="txtDiv1"
									value="<%out.print(bean.getDiv1());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 2</label> <input type="text"
									class="form-control" id="usr" name="txtDiv2"
									value="<%out.print(bean.getDiv2());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 3</label> <input type="text"
									class="form-control" id="usr" name="txtDiv3"
									value="<%out.print(bean.getDiv3());%>" style="width: 50%;"></td>
							</tr>
							<tr>
								<td><label for="usr">Stock 4</label> <input type="text"
									class="form-control" id="usr" name="txtDiv4"
									value="<%out.print(bean.getDiv4());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 5</label> <input type="text"
									class="form-control" id="usr" name="txtDiv5"
									value="<%out.print(bean.getDiv5());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 6</label> <input type="text"
									class="form-control" id="usr" name="txtDiv6"
									value="<%out.print(bean.getDiv6());%>" style="width: 50%;"></td>
							</tr>
							<tr>
								<td><label for="usr">Stock 7</label> <input type="text"
									class="form-control" id="usr" name="txtDiv7"
									value="<%out.print(bean.getDiv7());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 8</label> <input type="text"
									class="form-control" id="usr" name="txtDiv8"
									value="<%out.print(bean.getDiv8());%>" style="width: 50%;"></td>
								<td><label for="usr">Stock 9</label> <input type="text"
									class="form-control" id="usr" name="txtDiv9"
									value="<%out.print(bean.getDiv9());%>" style="width: 50%;"></td>
							</tr>
						</table>

						<br>
						<button type="submit" class="btn btn-primary" name="setStockPrefs"
							style="position: center">Update Stock Preferences</button>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
