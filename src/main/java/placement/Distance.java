package placement;

public class Distance {
	String fromPostcode;
	String toPostcode;
	long distance;
	public Distance(String fromPostcode, String toPostcode, long distance) {
		setFromPostcode(fromPostcode);
		setToPostcode(toPostcode);
		setDistance(distance);
	}

	public String getFromPostcode() {
		return fromPostcode;
	}
	public void setFromPostcode(String fromPostcode) {
		this.fromPostcode = fromPostcode;
	}
	public String getToPostcode() {
		return toPostcode;
	}
	public void setToPostcode(String toPostcode) {
		this.toPostcode = toPostcode;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
}
