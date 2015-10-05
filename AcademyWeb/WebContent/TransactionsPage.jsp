<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
import="project.entity.Transaction, project.entity.Transaction, java.util.List, 
	project.business.MasterBeanLocal, javax.naming.InitialContext, javax.ejb.EJB, javax.naming.Context"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<div class="container">
  <h2>Transactions</h2>          
  <table id="myTable" class="table table-striped" class="tablesorter">

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
    <!--   <tr>
        <td>2</td>
        <td>B</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr>
        <td>3</td>
        <td>C</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr>
        <td>4</td>
        <td>D</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr>
      <tr>
        <td>5</td>
        <td>E</td>
        <td>Buy</td>
        <td>100</td>
        <td>8.99</td>
        <td>12:00am</td>
        <td>Closed</td>
      </tr> -->
    </tbody>
  </table>
</div>
</body>
</html>