package nit.history;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import nit.history.dao.memory.MemoryDAOFactory;
import nit.history.data.HistoryEvent;
import nit.history.data.impl.HistoryEventImpl;
import nit.history.data.impl.LocationImpl;
import nit.history.data.impl.TimeImpl;
import nit.history.data.mock.TestEntity;
import nit.history.data.mock.TestTimeSpan;

public class HistoryDAOTest extends TestCase {

    MemoryDAOFactory factory = new MemoryDAOFactory();
    HistoryDAO dao = factory.getHistoryDAO();


    HistoryEvent createEvent(String ID, String name, int start, int end) {
        return new HistoryEventImpl(ID, new LocationImpl(name), new TestTimeSpan(start, end));
    }


    void assertHistoryList(Collection<HistoryEvent> events, String... expected) {
        List<String> ents = Arrays.asList(expected);
        for (HistoryEvent event : events) {
            System.out.println("trying event: " + event.getID());
            assertEquals(ents.contains(event.getID()), true);
        }
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao.createOrUpdateHistoryEvent(createEvent("E1", "home", 5, 10));
        dao.createOrUpdateHistoryEvent(createEvent("E2", "home", 11, 15));
        dao.createOrUpdateHistoryEvent(createEvent("E3", "markets", 16, 20));
        dao.createOrUpdateHistoryEvent(createEvent("E4", "hills", 21, 25));
    }


    public void testTest() {

        // by entity
        assertHistoryList(dao.getHistoryEvents(new TestEntity("me")), "E1", "E2", "E3", "E4");
        assertHistoryList(dao.getHistoryEvents(new TestEntity("Dea")), "E2");

        // by time
        assertHistoryList(dao.getHistoryEvents(new TimeImpl(18)), "E3");

        // by time span
        assertHistoryList(dao.getHistoryEvents(new TestTimeSpan(14, 22)), "E2", "E3", "E4");

        // by multiple
        assertHistoryList(dao.getHistoryEvents(new TestTimeSpan(12, 22), new TestEntity("waza")), "E3");
        assertHistoryList(dao.getHistoryEvents(new TestEntity("me"), new TestEntity("Dea")), "E2");
        assertHistoryList(dao.getHistoryEvents(new LocationImpl("hills"), new TimeImpl(23)), "E4");
    }
}
