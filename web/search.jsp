<%@ page import="nit.history.servlets.*" %>
<%@ page import="nit.history.data.*" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html> 
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>Time, as it happened</title>
	<link href="styles.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="title"></div>
	<div id="catch-phrase">A record of people, places and times, as it happened throughout history.</div>
	<div id="links">
		<a href="create-new.html">Create New...</a>
	</div>
	<div id="results">
	    <form name="search" action="search.jsp">
		<div id="search-pattern">
			<table align="center" cellspacing="0" cellpadding="0"> 
				<tr>
					<td>Location</td>
					<td><input type="text" name="search-location" id="search-location" value="" /></td>
					<td><a href="create-location.html"><img src="images/map-icon.png" width="25" height="25" /></a></td>
				</tr>
				<tr>
					<td>Time Period</td>
					<td>From <input type="text" name="search-from" id="search-from" value="01/01/2012" /> to <input type="text" name="search-to" id="search-to" value="01/01/2013" /></td>
					<td><a href="create-event.html"><img src="images/calendar-icon.png" width="25" height="25" /></a></td>
				</tr>
				<tr>
					<td>People/Names</td>
					<td><input type="text" name="search-entities" id="search-entities" value ="" /></td>
					<td><a href="create-entity.html"><img src="images/people-icon.png" width="25" height="25" /></a></td>
				</tr>
			</table>
			<input type="submit" value="Search"></input>
		</div>
	    </form>
		
		<%
			SearchHelper helper = new SearchHelper();
			List<LocationRelationShip> locRels = helper.doSearch(request, out);
		%>
		
		<div id="search-results">
			<ul>
				<% for (LocationRelationShip rel : locRels) { %>
				<li>
					<span class="month date">When: <%= rel.getTimespan().getStartTime() %> to <%= rel.getTimespan().getEndTime() %></span>
					<span class="entity">Who: <a href="search.jsp?search-entities=<%= rel.getEntity().getName() %>"><%= rel.getEntity().getName() %></a>
					<span class="location">Where: <a href="search.jsp?search-location=<%= rel.getLocation().getName() %>"><%= rel.getLocation().getName() %></a></span>
				</li>
				<% } %>
			</ul>
		</div>
		
		<hr />
	</div>
	
	<div id="footer">
		<a href="#">Privacy Policy</a> | <a href="#">Terms &amp; Conditions</a> | &copy Copyright vandalay industries 2012
	</div>
</body>
</html>



