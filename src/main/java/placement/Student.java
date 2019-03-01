package placement;

public class Student {
	private int id;
	private String ref;
	private String first;
	private String last;
	private String postcode;
	private String allocatedPractice;
	
	public Student(int id, String ref, String first, String last, String postcode, String allocatedPractice) {
		setId(id);
		setRef(ref);
		setFirst(first);
		setLast(last);
		setPostcode(postcode);
		setAllocatedPractice(allocatedPractice);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAllocatedPractice() {
		return allocatedPractice;
	}

	public void setAllocatedPractice(String allocatedPractice) {
		this.allocatedPractice = allocatedPractice;
	}

}
