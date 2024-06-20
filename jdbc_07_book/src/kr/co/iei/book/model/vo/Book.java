package kr.co.iei.book.model.vo;

public class Book {
	private int bookNo;
	private String bookGenre;
	private String bookTitle;
	private String bookAuthor;
	private int bookStock;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int bookNo, String bookGenre, String bookTitle, String bookAuthor, int bookStock) {
		super();
		this.bookNo = bookNo;
		this.bookGenre = bookGenre;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.bookStock = bookStock;
	}
	public int getBookNo() {
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	public String getBookGenre() {
		return bookGenre;
	}
	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public int getBookStock() {
		return bookStock;
	}
	public void setBookStock(int bookStock) {
		this.bookStock = bookStock;
	}
	@Override
	public String toString() {
		return "Book [bookNo=" + bookNo + ", bookGenre=" + bookGenre + ", bookTitle=" + bookTitle + ", bookAuthor="
				+ bookAuthor + ", bookStock=" + bookStock + "]";
	}
	
	
	
	
}