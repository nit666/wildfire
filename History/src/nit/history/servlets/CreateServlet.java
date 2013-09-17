package nit.history.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nit.history.HistoryService;
import nit.history.data.Entity;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.TimeSpan;
import nit.history.data.impl.EntityImpl;
import nit.history.data.impl.HistoryEventImpl;
import nit.history.data.impl.LocationImpl;
import nit.history.data.impl.TimeImpl;

public class CreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> messages = new LinkedList<String>();
		String type = request.getParameter("type");
		try {
			if ("entity".equals(type)) {
				doEntity(request, messages);
			} else if ("location".equals(type)) {
				doLocation(request, messages);
			}  else if ("event".equals(type)) {
				doEvent(request, messages);
			} else if ("relationship".equals(type)) {
				doRelationship(request, messages);
			} else {
				messages.add("WTF?!");
			}
			
			WebResources.persist();
		} catch (Exception e) {
			// ignore, just to get out of methods
			e.printStackTrace();
		}
		
		// write the messages, this should be in jsp
		ServletOutputStream out = response.getOutputStream();
		out.print("<html><body>");
		for (String message : messages) {
			out.print("<p>" + message + "</p>");
		}
		out.print("<p><a href=\"javascript:history.back()\">Back</a></p>");
		out.print("</body></html>");
	}
	
	// create an entity
	private void doEntity(HttpServletRequest request, List<String> messages) throws Exception {
		String name = request.getParameter("entity-name");
		HistoryService service = WebResources.getHistoryService();
		
		WebHelper.doError(!WebHelper.verifyStringParam(name), "No value was entered for Name", messages);
		WebHelper.doError(service.exists(name, Entity.class), "Entity " + name + " already exists", messages);
			
		service.saveOrUpdate(new EntityImpl(name), Entity.class);
		messages.add("New Entity " + name + " Created");
	}
	
	private void doLocation(HttpServletRequest request, List<String> messages) throws Exception {
		String locationName = request.getParameter("location-name");
		String locationParent = request.getParameter("location-parent");
		
		WebHelper.doError(!WebHelper.verifyStringParam(locationName),"Location Name is not valid", messages); 
		if (!WebHelper.isEmpty(locationParent)) { // location can be null
			WebHelper.doError(!WebHelper.verifyStringParam(locationParent), "Location Parent is not valid", messages);
		}
		
		HistoryService service = WebResources.getHistoryService();
		WebHelper.doError(service.exists(locationName, Location.class), "Location already exists", messages);
		Location parent = null;
		if (!WebHelper.isEmpty(locationParent)) { // location can be null
			WebHelper.doError(!service.exists(locationParent, Location.class), "Parent location doesn't exist, add this first", messages);
			parent = service.fetch(locationParent, Location.class);
		}
		
		LocationImpl location = new LocationImpl(locationName, parent);
		service.saveOrUpdate(location, Location.class);
		
		messages.add("New Location " + locationName + " Created");
	}
	
	private void doRelationship(HttpServletRequest request, List<String> messages) throws Exception {
		String entityName = request.getParameter("entity-name");
		String locationName = request.getParameter("location-name");
		String relationshipStart  = request.getParameter("relationship-start");
		String relationshipEnd = request.getParameter("relationship-end");
		
		HistoryService service = WebResources.getHistoryService();
		
		// error checking for entity
		WebHelper.doError(!WebHelper.verifyStringParam(entityName), "The entity was not entered correctly", messages);
		Entity entity = null;
		try {
			entity = service.fetch(entityName, Entity.class);
		} catch (NoSuchElementException e) {
			WebHelper.doError(true, "The entity does not exist", messages);
		}
		
		// error checking for location
		WebHelper.doError(!WebHelper.verifyStringParam(locationName), "The location was not entered correctly", messages);
		Location location = null;
		try {
			location = service.fetch(locationName, Location.class);
		} catch (NoSuchElementException e) {
			WebHelper.doError(true, "The location does not exist", messages);
		}
		
		// error checking for dates
		Date startDate = WebHelper.stringToDatePamameter(relationshipStart);
		WebHelper.doError(startDate == null, "The start date is in the wrong format", messages);
		Date endDate = WebHelper.stringToDatePamameter(relationshipEnd);
		WebHelper.doError(endDate == null, "The end date is in the wrong format", messages);
		
		// now create the relationship
		LocationRelationShip relship = new LocationRelationShip(
				new TimeSpan(new TimeImpl(startDate.getTime()), new TimeImpl(endDate.getTime())),
				entity, location);
		service.saveOrUpdate(relship, LocationRelationShip.class);
		messages.add("New Location/Entity relationship created");
	}
	
	private void doEvent(HttpServletRequest request, List<String> messages) throws Exception {
		String eventName = request.getParameter("event-name");
		String eventDescription = request.getParameter("event-description");
		String eventLocation = request.getParameter("event-location");
		String eventStart = request.getParameter("event-start");
		String eventEnd = request.getParameter("event-end");
		
		HistoryService service = WebResources.getHistoryService();
		
		WebHelper.doError(!WebHelper.verifyStringParam(eventName), "The name of the event must have a value", messages);
		WebHelper.doError(!WebHelper.verifyStringParam(eventDescription), "The description must have a value", messages);
		WebHelper.doError(!WebHelper.verifyStringParam(eventLocation), "The event must have a location", messages);
		WebHelper.doError(!service.exists(eventLocation, Location.class), "The location doesn't exist, please create it first", messages);
		Location loc = service.fetch(eventLocation, Location.class);
		
		// error checking for dates
		Date startDate = WebHelper.stringToDatePamameter(eventStart);
		WebHelper.doError(startDate == null, "The start date is in the wrong format", messages);
		Date endDate = WebHelper.stringToDatePamameter(eventEnd);
		WebHelper.doError(endDate == null, "The end date is in the wrong format", messages);
		
		HistoryEventImpl event = new HistoryEventImpl(eventName, loc, new TimeSpan(new TimeImpl(startDate), new TimeImpl(endDate)));
		event.setDescription(eventDescription);
		service.saveOrUpdate(event, HistoryEvent.class);
		
		messages.add("New Event created");
	}
}
