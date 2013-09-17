package nit.history.servlets;

import java.io.IOException;

import nit.history.DAOFactory;
import nit.history.HistoryService;
import nit.history.dao.memory.MemoryDAOFactory;
import nit.history.dao.memory.MemoryHistoryService;
import nit.history.dao.memory.MemoryToFileWriter;

public class WebResources {

	static boolean fileRead = false;
	
	static DAOFactory factory = new MemoryDAOFactory();
	static HistoryService service = new MemoryHistoryService(factory);
	static MemoryToFileWriter fileWriter = new MemoryToFileWriter();
	
	public static HistoryService getHistoryService() {
		if (!fileRead) {
			try {
				fileWriter.readFile(factory);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileRead = true;
		}
		return service;
	}
	
	public static void persist() throws IOException {
		if (!fileRead) {
			fileWriter.readFile(factory);
			fileRead = true;
		}
		fileWriter.writeFile(factory);
	}
}
