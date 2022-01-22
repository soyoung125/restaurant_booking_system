package booksys.domain;

public class OidDTO {
	private int last_id;
	
	public OidDTO() {
	}
	
	public OidDTO(int last_id) {
		this.last_id=last_id;
	}

	public int getLast_id() {
		return last_id;
	}

	public void setLast_id(int last_id) {
		this.last_id = last_id;
	}
}
