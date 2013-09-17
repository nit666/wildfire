package nit.history.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import nit.history.EntityDAO;
import nit.history.HistoryDAO;
import nit.history.HistoryService;
import nit.history.LocationDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryDataType;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.TimeSpan;
import nit.history.data.impl.TimeImpl;

public class SearchHelper {

	public List<HistoryEvent> doHistorySearch(HttpServletRequest request, JspWriter out) throws IOException {
		HistoryService service = WebResources.getHistoryService();
				
		List<String> messages = new LinkedList<String>();

		HistoryDataType[] searchParams = getSearchParams(request, messages);
		
		List<HistoryEvent> results = service.getHistoryEvents(searchParams);
		
		// sort by start time descending
		Collections.sort(results, new Comparator<HistoryEvent>() {

			@Override
			public int compare(HistoryEvent r1, HistoryEvent r2) {
				
				if (r1.getTimeSpan().getStartTime().equals(r2.getTimeSpan().getStartTime())) {
					return 0;
				} else if (r1.getTimeSpan().getStartTime().before(r2.getTimeSpan().getStartTime())) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		
		printMessages(messages, out);
		
		return results;
	}
	
	public List<LocationRelationShip> doSearch(HttpServletRequest request, JspWriter out) throws IOException {
		HistoryService service = WebResources.getHistoryService();
		
		List<String> messages = new LinkedList<String>();

		HistoryDataType[] searchParams = getSearchParams(request, messages);
		
		List<LocationRelationShip> results = service.getRelationShips(searchParams);
	
		// sort by start time
		Collections.sort(results, new Comparator<LocationRelationShip>() {

			@Override
			public int compare(LocationRelationShip r1, LocationRelationShip r2) {
				
				if (r1.getTimespan().getStartTime().equals(r2.getTimespan().getStartTime())) {
					return 0;
				} else if (r1.getTimespan().getStartTime().before(r2.getTimespan().getStartTime())) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		
		printMessages(messages, out);
		
		return results;
	}
	
	
	private HistoryDataType[] getSearchParams(HttpServletRequest request, List<String> messages) {
		HistoryService service = WebResources.getHistoryService();
		
		String locationParam = request.getParameter("search-location");
		String fromParam = request.getParameter("search-from");
		String toParam = request.getParameter("search-to");
		String entities = request.getParameter("search-entities");
		
		List<HistoryDataType> searchParams = new LinkedList<HistoryDataType>();
		if (!WebHelper.isEmpty(locationParam)) {
			if (WebHelper.doWarning(!service.exists(locationParam, Location.class), "The location '" + locationParam + "' does not exist.", messages)) {
				searchParams.add(service.fetch(locationParam, Location.class));
			}
		}
		if (!WebHelper.isEmpty(entities)) {
			String[] listOfEntities = entities.split(",");
			for (String entity : listOfEntities) {
				if (WebHelper.doWarning(!service.exists(entity, Entity.class), "The Person '" + entity + "' does not exist", messages)) {
					searchParams.add(service.fetch(entity, Entity.class));
				}
			}
		}
		Date from = null;
		Date to = null;
		if (!WebHelper.isEmpty(fromParam)) {
			from = WebHelper.stringToDatePamameter(fromParam);
			WebHelper.doWarning(from == null, "The start date is not in the right format", messages);
		}
		if (!WebHelper.isEmpty(toParam)) {
			to = WebHelper.stringToDatePamameter(fromParam);
			WebHelper.doWarning(to == null, "The end date is not in the right format", messages);
		}
		if (from != null && to != null) { // mean we need a timespan
			searchParams.add(new TimeSpan(new TimeImpl(from), new TimeImpl(to)));
		} else if (from != null) { // just add the time
			searchParams.add(new TimeImpl(from));
		}
		
		return searchParams.toArray(new HistoryDataType[searchParams.size()]);
	}
	
	private void printMessages(List<String> messages, JspWriter out) throws IOException {
		// write the messages, this should be in jsp
		out.print("<div id=\"messages\">");
		for (String message : messages) {
			out.print("<p>" + message + "</p>");
		}
		out.print("</div>");
	}
	
	public Collection<Entity> getEntities(Location location, TimeSpan timeSpan) {
		HistoryService service = WebResources.getHistoryService();
		
		return service.getEntitiesForLocationForTimeSpan(location, timeSpan);
	}
}
