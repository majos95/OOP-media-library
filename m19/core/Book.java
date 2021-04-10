package m19.core;

public class Book extends Work {
	
	private static final long serialVersionUID = 201901101348L;
	private String _author;
	private int _isbn;
	
	public Book(String title, String author, int price, Category category, int isbn, int numberOfCopies) {
		super(price, numberOfCopies, title, category);
		_author = author;
		_isbn = isbn;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.getId()) + " - " + Integer.toString(this.getNumberOfCopies()) + " de " + Integer.toString(this.getAllCopies()) + " - Livro - " + this.getTitle() + " - " + Integer.toString(this.getPrice()) + " - " + this.getCategory().toString() + " - " + this.getAuthor() + " - " + _isbn;
	}
	
	protected String getAuthor() {
		return _author;
	}

	
	
}
