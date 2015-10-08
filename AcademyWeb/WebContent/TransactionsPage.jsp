<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import="project.entity.Stock, project.entity.Transaction, java.util.List, 
	project.business.MasterBeanLocal, project.business.LiveFeedBean, javax.naming.InitialContext, javax.ejb.EJB, javax.naming.Context"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197" style="z-index: 9999; width: 100%;">
  <ul class="nav navbar-nav">
    <li class="active"><a href="index.jsp">Trading Home</a></li>
    <li><a href="TransactionsPage.jsp">Transactions</a></li>

  </ul>
</nav>

<script type="text/javascript" src="bootstrap/js/jquery-latest.js"></script>
<script type="text/javascript" src="bootstrap/js/jquery.tablesorter.min.js"></script> 
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<title>TransactionPage</title>

<script type="text/javascript" src="bootstrap/js/project.js"></script>
</head>
<body>
<% 
InitialContext context = new InitialContext();
MasterBeanLocal bean = (MasterBeanLocal)context.lookup("java:comp/env/ejb/Master");
List<Transaction> tr = bean.retrieveAllTransaction() ;
%>
<script type="text/javascript">
$(document).ready(function() { 
    $("table").tablesorter({ 
        sortList: [[0,0],[2,0]] }); 
    }); 
</script>

<h2>Transactions</h2>

<div class="container" style= "height:400px; overflow:auto";>
            

  <table id="myTable" class="table-condensed" class="tablesorter">

  

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