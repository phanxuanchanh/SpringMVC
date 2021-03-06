package SpringMVC.Entity;

public class PublishingHouse {
	private long ID;
	private String name;
	private String description;
	private String email;
	private String address;

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PublishingHouse(long id, String name, String description, String email, String address) {
		super();
		ID = id;
		this.name = name;
		this.description = description;
		this.email = email;
		this.address = address;
	}

	public PublishingHouse() {
		super();
	}

}
