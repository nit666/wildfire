package nit.history.data;

import junit.framework.TestCase;
import nit.history.data.impl.TimeImpl;
import nit.history.data.mock.TestTimeSpan;

public class TimeSpanTest extends TestCase {
	
	public void testIsWithinTimeSpan() {
		TimeImpl t1 = new TimeImpl(3);
		TimeImpl t2 = new TimeImpl(10);
		TimeSpan ts = new TimeSpan(t1, t2);
		
		// should be within
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(4)), true);
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(10)), true);
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(3)), true);
		
		// should not be within
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(0)), false);
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(1)), false);
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(20)), false);
		assertEquals(ts.isWithinTimeSpan(new TimeImpl(11)), false);
	}
	
	public void testIsWithinTimeSpanForTimeSpans() {
		TimeSpan tester = new TestTimeSpan(10, 20);
		
		// should be within
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(5, 15)), true); // before, within
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(15, 25)), true); // within, after
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(9, 21)), true); // before, after
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(10, 20)), true); // exact, exact
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(11, 19)), true); // within, within
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(1, 10)), true); // before, exact
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(20, 21)), true); // exact, after
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(20, 20)), true); // exact, same time
		
		// should not be within
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(1, 9)), false); // before, before
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(21, 30)), false); // after, after
		assertEquals(tester.isWithinTimeSpan(new TestTimeSpan(9, 9)), false); // before, same time
		
	}
}
