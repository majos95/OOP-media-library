package m19.core;


import java.io.Serializable;

public abstract class Work implements Comparable<Work>, Serializable {
	
	private static final long serialVersionUID = 201901101350L;
	
	private int _id;
	private int _price;
	private int _numberOfCopies;
	private final int _allCopies;
	private String _title;
	private Category _category;
	
	public Work(int price, int numberOfCopies, String title, Category category) {
		_price = price;
		_title = title;
		_allCopies = numberOfCopies;
		_numberOfCopies = numberOfCopies;
		_category = category;
	}
	
	public abstract String toString();
	
	protected int getId() {
		return _id;
	}

	protected void setId(int id) {
		_id = id;
	}
	
	public int getPrice() {
		return _price;
	}
	
	protected void augmentPrice(int value) {
		_price += value;
	}
	
	protected int getAllCopies() {
		return _allCopies;
	}
	
	protected int getNumberOfCopies() {
		return _numberOfCopies;
	}
	
	protected void returnWork() {
		_numberOfCopies++;
	}
	
	protected void requestWork() {
		_numberOfCopies--;
	}
	
	public String getTitle() {
		return _title;
	}
	
	protected Category getCategory() {
		return _category;
	}
	
	@Override
	public int compareTo(Work work) {
		int id = work._id;
	    return (_id < id ? -1 : (_id == id ? 0 : 1));
	}
	
	@Override
	  public boolean equals(Object obj) {
	    return (obj instanceof Work) && ((Work) obj)._id == _id;
	  }
	
}
