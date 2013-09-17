package nit.history;

/**
 * This interface allows access to other DAOs and is used within the DAO classes. 
 * @author Neil
 *
 */
public interface DAOFactory {

	EntityDAO getEntityDAO();
	HistoryDAO getHistoryDAO();
	LocationDAO getLocationDAO();
	
}
