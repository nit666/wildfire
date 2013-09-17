package nit.history.dao.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import nit.history.DAOFactory;
import nit.history.LocationDAO;
import nit.history.data.Entity;
import nit.history.data.HistoryEvent;
import nit.history.data.Location;
import nit.history.data.LocationRelationShip;
import nit.history.data.TimeSpan;
import nit.history.data.impl.EntityImpl;
import nit.history.data.impl.HistoryEventImpl;
import nit.history.data.impl.LocationImpl;
import nit.history.data.impl.TimeImpl;

public class MemoryToFileWriter {
	public static String FILENAME = "C:\\temp\\data.txt";
	public static String DELIMITER = "#";
	
	public void writeFile(DAOFactory factory) throws IOException {
		
		File f = new File(FILENAME);
		if (!f.exists()) {
			f.createNewFile();
		}
		PrintStream writer = new PrintStream(new FileOutputStream(FILENAME));
		
		MemoryEntityDAOImpl entityDAO = (MemoryEntityDAOImpl) factory.getEntityDAO();
		// write entities
		for (Entity ent : entityDAO.data.values()) {
			writer.println("@Entity" + DELIMITER + 
					ent.getID() + DELIMITER + 
					ent.getName());
		}
		
		// write locations
		MemoryLocationDAOImpl locations = (MemoryLocationDAOImpl) factory.getLocationDAO();
		Set<String> alreadyWritten = new HashSet<String>();
		for (Location loc : locations.data.values()) {
			writeLocation(locations, alreadyWritten, loc, writer);
		}
		
		// write location entity relationships
		for (LocationRelationShip locRel : locations.relationships.data.values()) {
			writer.println("@LocationRelationship" + DELIMITER + 
					locRel.getID() + DELIMITER +
					locRel.getEntity().getID() + DELIMITER +
					locRel.getLocation().getID() + DELIMITER + 
					locRel.getTimespan().getStartTime().getID() + DELIMITER + 
					locRel.getTimespan().getEndTime().getID());
		}
		
		// write history events
		MemoryHistoryDAOImpl history = (MemoryHistoryDAOImpl) factory.getHistoryDAO();
		for (HistoryEvent event : history.data.values()) {
			writer.println("@HistoryEvent" + DELIMITER +
					event.getID() + DELIMITER +
					event.getLocation().getID() + DELIMITER +
					event.getDescription() + DELIMITER +
					event.getTimeSpan().getStartTime().getID() + DELIMITER +
					event.getTimeSpan().getEndTime().getID());
		}
		writer.flush();
		writer.close();
	}
	
	private void writeLocation(LocationDAO locations, Set<String> alreadyWritten, Location loc, PrintStream writer) {
		String parent = (loc.getParent() == null? "null" : loc.getParent().getID());
		if (loc.getParent() != null && !alreadyWritten.contains(parent)) {
			writeLocation(locations, alreadyWritten, locations.getLocation(loc.getParent().getID()), writer);
		}
		
		if (!alreadyWritten.contains(loc.getID())) {
			writer.println("@Location" + DELIMITER + 
					loc.getID() + DELIMITER + 
					loc.getName() + DELIMITER + 
					parent);
			alreadyWritten.add(loc.getID());
		}
	}
	
	public void readFile(DAOFactory factory) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILENAME));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("@Entity")) {
				String[] bits = line.split(DELIMITER);
				Entity e = new EntityImpl(bits[1]);
				factory.getEntityDAO().createOrUpdateEntity(e);
			}
			
			else if (line.startsWith("@Location" + DELIMITER)) {
				String[] bits = line.split(DELIMITER);
				Location parent = null;
				if (factory.getLocationDAO().locationExists(bits[3])) {
					parent = factory.getLocationDAO().getLocation(bits[3]);
				}
				factory.getLocationDAO().createOrUpdateLocation(new LocationImpl(bits[2], parent));
				
			} else if (line.startsWith("@HistoryEvent")) {
				String[] bits = line.split(DELIMITER);
				Location location = factory.getLocationDAO().getLocation(bits[2]);
				long timeStart = Long.valueOf(bits[4]);
				long endTime = Long.valueOf(bits[5]);
				HistoryEventImpl event = new HistoryEventImpl(bits[1], location, new TimeSpan(new TimeImpl(timeStart), new TimeImpl(endTime)));
				event.setDescription(bits[3]);
				factory.getHistoryDAO().createOrUpdateHistoryEvent(event);
				
			} else if (line.startsWith("@LocationRelationship")) {
				String[] bits = line.split(DELIMITER);
				// these should exist already
				Entity entity = factory.getEntityDAO().getEntity(bits[2]);
				Location location = factory.getLocationDAO().getLocation(bits[3]);
				long timeStart = Long.valueOf(bits[4]);
				long endTime = Long.valueOf(bits[5]);
				TimeSpan timespan = new TimeSpan(new TimeImpl(timeStart), new TimeImpl(endTime));
				
				LocationRelationShip ship = new LocationRelationShip(timespan, entity, location);
				factory.getLocationDAO().createOrUpdateLocationRelationShip(ship);
			}
		}
		br.close();
	}
}
