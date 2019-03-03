package placement;

public class Practice {
	private int id;
	private String gp;
	private String practiceName;
	private String postcode;
	private int places;

	public Practice(String gp, String practiceName, String postcode, int places) {
		setGp(gp);
		setPracticeName(practiceName);
		setPostcode(postcode);
		setPlaces(places);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGp() {
		return gp;
	}

	public void setGp(String gp) {
		this.gp = gp;
	}

	public String getPracticeName() {
		return practiceName;
	}

	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

}
