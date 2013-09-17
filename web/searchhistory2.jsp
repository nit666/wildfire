<%@ page import="nit.history.servlets.*" %>
<%@ page import="nit.history.data.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>

<!DOCTYPE html> 
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>Time, as it happened</title>
	<link href="styles2.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="header">
		<div id="title"></div>
		<span id="catch-phrase">A record of people, places and events.</span>
		<div id="top-side-part">
			<div id="links">
				<a href="create-new.html">Create New Event</a> | <a href="#">Settings</a> | <a href="#">Logout</a>
			</div>
			<div id="search-form">
				<form name="search" action="searchhistory2.jsp">
					<input type="text" name="search-field" id="search-field" value="" />
					<input type="submit" name="search-button" id="search-button" value="Search" />
				</form>
			</div>
		</div>
	</div>

		<%
			SearchHelper helper = new SearchHelper();
			List<HistoryEvent> history = helper.doHistorySearch(request, out);
		%>
		
		<div id="search-results">
			<table>
				<tbody>
				<% for (HistoryEvent event : history) { %>
				
					<tr class="search-result">
						<td class="date-part" valign="top">
							<%= event.getTimeSpan().getStartTime() %>
						</td>
						<td class="event-part" valign="top">
							<p><b><%= event.getID() %></b></p>
							<p><%= event.getDescription() %></p>
							<p class="location">Where: <a href="searchhistory2.jsp?search-location=<%= URLEncoder.encode(event.getLocation().getName()) %>"><%= event.getLocation().getName() %></a></p>	
							<p> People at this event: 
							<% 
							Collection<Entity> entities = helper.getEntities(event.getLocation(), event.getTimeSpan());
							boolean first = true;
							for (Entity entity : entities) {
								if (!first) { 
									out.print(", ");
								}
								%>
								<a href="searchhistory2.jsp?search-entities=<%= URLEncoder.encode(entity.getID()) %>"><%= entity.getName() %></a>
								<%
								first = false;
							}
							%>
							</p>
						</td>
					</tr>
				<% } %>
		</div>
		
		<hr />
	</div>
	
	<div id="footer">
		<a href="#">Privacy Policy</a> | <a href="#">Terms &amp; Conditions</a> | &copy Copyright vandalay industries 2012
	</div>
</body>
</html>



