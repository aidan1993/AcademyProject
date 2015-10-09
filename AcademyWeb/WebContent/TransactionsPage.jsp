<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="project.entity.Stock, project.entity.Transaction, java.util.List, 
	project.business.MasterBeanLocal, project.business.LiveFeedBean, javax.naming.InitialContext, javax.ejb.EJB, javax.naming.Context"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript" src="bootstrap/js/jquery-latest.js"></script>
<script type="text/javascript" src="bootstrap/js/jquery.tablesorter.min.js"></script> 
<script type="text/javascript">
window.onload = function() {
	var loop = 0;
	var URL = "rest/livefeed?loop="+loop;
	function runFeed() {
		setTimeout(function() { 
			$.ajax({
		        type: "GET",
		        url: URL,
		        cache: false,
		        success: function (data) {
		         	console.log("Live Feed Running");
		         	loop++;
		        },
		        error: function (data) {
		            console.log("Problem occurred");
		        }
			})

			runFeed()
		}, 10000);
	}

	runFeed();
}


$(document).ready(function() { 
    $("table").tablesorter({ 
        sortList: [[0,0],[2,0]] }); 

}); 
</script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<title>TransactionPage</title>

</head>
<body>
<% 
InitialContext context = new InitialContext();
MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
List<Transaction> tr = bean.retrieveAllTransaction() ;
%>

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197" style="z-index: 9999; width: 100%;">
  <ul class="nav navbar-nav">
    <li><a href="index.jsp">Trading Home</a></li>
    <li class="active"><a href="TransactionsPage.jsp">Transactions</a></li>

  </ul>
</nav>

<h2>Transactions</h2>

<div class="container" style= "height:400px; overflow:auto";>
            
<<<<<<< HEAD

  <table id="myTable" class="table-condensed" class="tablesorter">

  
=======
  <table id="myTable" class="table table-condensed" class="tablesorter">
>>>>>>> master

    <thead>
      <tr>
        <th>Transaction ID<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Stock Symbol<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Action<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Volume<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Price<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Time<a><span class="glyphicon glyphicon-sort "></span></a></th>
        <th>Strategy Used<a><span class="glyphicon glyphicon-sort "></span></a></th>
      </tr>
    </thead>
    <tbody>
    <%
    for(Transaction t: tr)
    	{%>
      <tr>
        <td><%out.print(t.getTransactionid());%></td>
        <td><%out.print(t.getStockSymbol());%></td>
        <td><%out.print(t.getTranstype());%></td>
        <td><%out.print(t.getVolume());%></td>
        <td><%out.print(t.getPrice());%></td>
        <td><%out.print(t.getTranstime());%></td>
        <td><%out.print(t.getStrategy());%></td>
      </tr>
      <%} %>
  
    </tbody>
  </table>
</div>
</body>
</html>