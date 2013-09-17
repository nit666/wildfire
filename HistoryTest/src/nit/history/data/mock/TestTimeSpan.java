package nit.history.data.mock;

import nit.history.data.TimeSpan;
import nit.history.data.impl.TimeImpl;

public class TestTimeSpan extends TimeSpan {

	public TestTimeSpan(int start, int end) {
		super(new TimeImpl(start), new TimeImpl(end));
	}

}
