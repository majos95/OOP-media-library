package m19.core;

public class Dvd extends Work {
	
	private static final long serialVersionUID = 201901101348L;
	private  String _director;
	private int _igac;
	
	public Dvd(String title, String director, int price, Category category, int igac, int numberOfCopies) {
		super(price, numberOfCopies, title, category);
		_director = director;
		_igac = igac;
	}

	@Override
	public String toString() {
		return Integer.toString(this.getId()) + " - " + Integer.toString(this.getNumberOfCopies()) + " de  " + Integer.toString(this.getAllCopies()) + " - DVD - " + this.getTitle() + " - " + Integer.toString(getPrice()) + " - " + getCategory().toString() + " - " + this.getDirector() + " - " + _igac;
	}
	
	protected String getDirector() {
		return _director;
	}

}
