package SpringMVC.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringMVC.Common.StarRating;
import SpringMVC.DAO.AuthorContributeDAO;
import SpringMVC.DAO.BookAuthorDAO;
import SpringMVC.DAO.BookDAO;
import SpringMVC.DAO.CategoryDAO;
import SpringMVC.DAO.PublishingHouseDAO;
import SpringMVC.DTO.BookAuthorInfo;
import SpringMVC.DTO.BookInfo;
import SpringMVC.Entity.AuthorContribute;
import SpringMVC.Entity.Book;
import SpringMVC.Entity.Category;
import SpringMVC.Entity.PublishingHouse;

@Service
public class BookServiceImpl implements IBookService {
	@Autowired
	private BookDAO bookDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private PublishingHouseDAO publishingHouseDAO;

	@Autowired
	private BookAuthorDAO bookAuthorDAO;
	
	@Autowired 
	AuthorContributeDAO authorContributeDAO;

	public List<Book> GetBooks() {
		List<Book> books = bookDAO.GetBooks();
		for(Book book : books) {
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
		}
		return books;
	}

	public List<BookInfo> GetLatestBooks(int count) {
		List<Book> books = bookDAO.GetLatestBooks(count);
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		for (Book book : books) {
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			StarRating starRating = new StarRating(book.getUpvote(), book.getDownvote());
			bookInfos.add(new BookInfo(book.getID(), book.getName(), book.getDescription(), null, null, null,
					book.getViews(), book.getUpvote(), book.getDownvote(), starRating.SolveStar(), book.getPdf(), book.getImg()));
		}
		return bookInfos;
	}

	public List<BookInfo> GetBooksByViews(int count) {
		List<Book> books = bookDAO.GetBooksByViews(count);
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		for (Book book : books) {
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			StarRating starRating = new StarRating(book.getUpvote(), book.getDownvote());
			bookInfos.add(new BookInfo(book.getID(), book.getName(), book.getDescription(), null, null, null,
					book.getViews(), book.getUpvote(), book.getDownvote(), starRating.SolveStar(), book.getPdf(), book.getImg()));
		}
		return bookInfos;
	}
	
	public List<BookInfo> GetBooksByCategoryId(int id){
		List<Book> books = bookDAO.GetBooksByCategoryId(id);
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		for (Book book : books) {
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			StarRating starRating = new StarRating(book.getUpvote(), book.getDownvote());
			bookInfos.add(new BookInfo(book.getID(), book.getName(), book.getDescription(), null, null, null,
					book.getViews(), book.getUpvote(), book.getDownvote(), starRating.SolveStar(), book.getPdf(), book.getImg()));
		}
		return bookInfos;
	}
	
	public List<BookInfo> GetBooksByKeyword(String keyword){
		List<Book> books = bookDAO.GetBooksByKeyword(keyword);
		List<BookInfo> bookInfos = new ArrayList<BookInfo>();
		for (Book book : books) {
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			Category category = categoryDAO.GetCategory(book.getCategoryId());
			StarRating starRating = new StarRating(book.getUpvote(), book.getDownvote());
			bookInfos.add(new BookInfo(book.getID(), book.getName(), book.getDescription(), category, null, null,
					book.getViews(), book.getUpvote(), book.getDownvote(), starRating.SolveStar(), book.getPdf(), book.getImg()));
		}
		return bookInfos;
	}

	public Book GetBook(long id) {
		if (bookDAO.IsExistBookById(id)) {
			Book book = bookDAO.GetBook(id);
			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			return book;
		}
		return null;
	}

	public BookInfo GetBookInfo(long id) {
		if (bookDAO.IsExistBookById(id)) {
			Book book = bookDAO.GetBook(id);
			if (book == null)
				return null;

			if(book.getImg() == null)
				book.setImg("book-default.png");
			
			if(book.getImg().trim().length() == 0)
				book.setImg("book-default.png");
			
			Category category = categoryDAO.GetCategory(book.getCategoryId());
			PublishingHouse publishingHouse = publishingHouseDAO.GetPublishingHouse(book.getPublishingHouseId());
			List<BookAuthorInfo> bookAuthorInfos = bookAuthorDAO.GetBookAuthorsByBookId(book.getID());
			
			StarRating starRating = new StarRating(book.getUpvote(), book.getDownvote());
			return new BookInfo(book.getID(), book.getName(), book.getDescription(), category, publishingHouse,
					bookAuthorInfos, book.getViews(), book.getUpvote(), book.getDownvote(), starRating.SolveStar(), book.getPdf(), book.getImg());
		}
		return null;
	}

	public boolean CreateBook(Book book) {
		if (bookDAO.IsExistBookByName(book.getName()))
			return false;
		return bookDAO.CreateBook(book);
	}

	public boolean UpdateBook(Book book) {
		if (bookDAO.IsExistBookById(book.getID()))
			return bookDAO.UpdateBook(book);
		return false;
	}
	
	public boolean AddBookAuthor(AuthorContribute authorContribute) {
		if(!bookDAO.IsExistBookById(authorContribute.getBookId()))
			return false;
		
		if(!bookAuthorDAO.IsExistBookAuthorById(authorContribute.getBookAuthorId()))
			return false;
		
		if(authorContributeDAO.IsExistAuthorContribute(authorContribute.getBookAuthorId(), authorContribute.getBookId()))
			return false;
		
		return authorContributeDAO.CreateAuthorContribute(authorContribute);
	}
	
	public boolean DeleteAllBookAuthor(long bookId) {
		if(bookDAO.IsExistBookById(bookId)) {
			return authorContributeDAO.DeleteAuthorContribute(bookId);
		}
		return false;
	}

	public boolean AddImage(long id, String fileName) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.AddImage(id, fileName);
		return false;
	}

	public boolean DeleteImage(long id) {
		if (bookDAO.IsExistBookById(id)) {
			Book book = bookDAO.GetBook(id);
			if(book.getImg() == null)
				return false;
			
			if(book.getImg().trim().length() == 0)
				return false;
			
			return bookDAO.DeleteImage(id);
		}
		return false;
	}

	public boolean AddPdf(long id, String fileName) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.AddPdf(id, fileName);
		return false;
	}

	public boolean DeletePdf(long id) {
		if (bookDAO.IsExistBookById(id)) {
			Book book = bookDAO.GetBook(id);
			if(book.getPdf() == null)
				return false;
			
			if(book.getPdf().trim().length() == 0)
				return false;
			
			return bookDAO.DeletePdf(id);
		}
		return false;
	}
	
	public boolean IncreaseView(long id) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.IncreaseView(id);
		return false;
	}
	
	public boolean Upvote(long id) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.Upvote(id);
		return false;
	}
	
	public boolean Downvote(long id) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.Downvote(id);
		return false;
	}

	public boolean DeleteBook(long id) {
		if (bookDAO.IsExistBookById(id))
			return bookDAO.DeleteBook(id);
		return false;
	}
	
	public int CountBook() {
		return bookDAO.CountBook();
	}
}
