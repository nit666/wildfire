<%@ page import="nit.history.servlets.*" %>
<%@ page import="nit.history.data.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>

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
	    <form name="search" action="searchhistory.jsp">
		<div id="search-pattern">
			<table align="center" cellspacing="0" cellpadding="0"> 
				<tr>
					<td>Location</td>
					<td><input type="text" name="search-location" id="search-location" value="" /></td>
					<td><a href="create-location.html"><img src="images/map-icon.png" width="25" height="25" /></a></td>
				</tr>
				<tr>
					<td>Time Period</td>
					<td>From <input type="text" name="search-from" id="search-from" value="" /> to <input type="text" name="search-to" id="search-to" value="" /></td>
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
			List<HistoryEvent> history = helper.doHistorySearch(request, out);
		%>
		
		<div id="search-results">
			<ul>
				<% for (HistoryEvent event : history) { %>
				<li>
					<div><b><%= event.getID() %></b></div>
					<div><%= event.getDescription() %></div>
					<span class="month date">When: <%= event.getTimeSpan().getStartTime() %> to <%= event.getTimeSpan().getEndTime() %></span>
					<span class="location">Where: <a href="searchhistory.jsp?search-location=<%= URLEncoder.encode(event.getLocation().getName()) %>"><%= event.getLocation().getName() %></a></span>	
					<div> People at this event: 
					<% 
					Collection<Entity> entities = helper.getEntities(event.getLocation(), event.getTimeSpan());
					boolean first = true;
					for (Entity entity : entities) {
						if (!first) { 
							out.print(", ");
						}
						%>
						<a href="searchhistory.jsp?search-entities=<%= URLEncoder.encode(entity.getID()) %>"><%= entity.getName() %></a>
						<%
						first = false;
					}
					%>
					<hr />
					
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



