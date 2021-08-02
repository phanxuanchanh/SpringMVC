package SpringMVC.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import SpringMVC.DTO.BookInfo;
import SpringMVC.Entity.Book;
import SpringMVC.Service.BookServiceImpl;
import SpringMVC.Service.CategoryServiceImpl;
import SpringMVC.Service.PublishingHouseServiceImpl;
import SpringMVC.Validator.BookValidator;

@Controller
public class BookManagementController {
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@Autowired
	private PublishingHouseServiceImpl publishingHouseServiceImpl;
	
	@RequestMapping(value = {"quan-tri/danh-sach-sach","quan-tri/danh-sach-sach/{message}"}, method = RequestMethod.GET)
	public ModelAndView BookList(@PathVariable(required = false) String message) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/book-list");
		modelAndView.addObject("books", bookServiceImpl.GetBooks());
		if(message != null) {
			if(message.equals("delete-success"))
				modelAndView.addObject("state", "Xóa thành công");
			else if(message.equals("delete-failed"))
				modelAndView.addObject("state", "Xóa thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "quan-tri/chi-tiet-sach/{id}", method = RequestMethod.GET)
	public ModelAndView BookDetail(@PathVariable long id) {
		if (id <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");
		
		BookInfo bookInfo = bookServiceImpl.GetBookInfo(id);
		if (bookInfo == null)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/book-detail");
		modelAndView.addObject("book", bookInfo);
		return modelAndView;
	}
	
	@RequestMapping(value = {"quan-tri/tao-moi-sach", "quan-tri/tao-moi-sach/{message}"}, method = RequestMethod.GET)
	public ModelAndView CreateBook(@PathVariable(required = false) String message) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/create-book");
		modelAndView.addObject("book", new Book());
		modelAndView.addObject("categories", categoryServiceImpl.GetCategories());
		modelAndView.addObject("publishingHouses", publishingHouseServiceImpl.GetPublishingHouses());
		if(message != null) {
			if(message.equals("add-success"))
				modelAndView.addObject("state", "Thêm thành công");
			else if(message.equals("add-failed"))
				modelAndView.addObject("state", "Thêm thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "quan-tri/tao-moi-sach", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView CreateBook(@ModelAttribute("book") Book book, BindingResult bindingResult, BookValidator bookValidator) {
		bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors())
			return new ModelAndView("admin/create-book", "book", book);

		if(bookServiceImpl.CreateBook(book))
			return new ModelAndView("redirect:/quan-tri/tao-moi-sach/add-success");
		
		return new ModelAndView("redirect:/quan-tri/tao-moi-sach/add-failed");
	}
	
	@RequestMapping(value = {"quan-tri/them-hinh-anh-cho-sach/{id}", "quan-tri/them-hinh-anh-cho-sach/{id}/{message}"}, method = RequestMethod.GET)
	public ModelAndView AddImage(@PathVariable long id, @PathVariable(required = false) String message) {
		if (id <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");
		
		Book book = bookServiceImpl.GetBook(id);
		if (book == null)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/add-image");
		modelAndView.addObject("book", book);
		if(message != null) {
			if(message.equals("add-success"))
				modelAndView.addObject("state", "Thêm thành công");
			else if(message.equals("add-failed"))
				modelAndView.addObject("state", "Thêm thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "quan-tri/them-hinh-anh-cho-sach/{id}", method = RequestMethod.POST)
	public ModelAndView AddImage(@RequestParam("file") CommonsMultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		return null;
	}
	
	@RequestMapping(value = {"quan-tri/chinh-sua-sach/{id}", "quan-tri/chinh-sua-sach/{id}/{message}"}, method = RequestMethod.GET)
	public ModelAndView UpdateBook(@PathVariable long id, @PathVariable(required = false) String message) {
		if (id <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");

		Book book = bookServiceImpl.GetBook(id);
		if (book == null)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/update-book");
		modelAndView.addObject("book", book);
		modelAndView.addObject("categories", categoryServiceImpl.GetCategories());
		modelAndView.addObject("publishingHouses", publishingHouseServiceImpl.GetPublishingHouses());
		if(message != null) {
			if(message.equals("edit-success"))
				modelAndView.addObject("state", "Chỉnh sửa thành công");
			else if(message.equals("edit-failed"))
				modelAndView.addObject("state", "Chỉnh sửa thất bại");
			else 
				modelAndView.addObject("state", "Không xác định được nội dung thông báo");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "quan-tri/chinh-sua-sach/{id}", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView UpdateBook(@ModelAttribute("book") Book book, BindingResult bindingResult, BookValidator bookValidator) {
		bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors())
			return new ModelAndView("admin/update-book", "book", book);

		if(bookServiceImpl.UpdateBook(book))
			return new ModelAndView("redirect:/quan-tri/chinh-sua-sach/{id}/edit-success");
		
		return new ModelAndView("redirect:/quan-tri/chinh-sua-sach/{id}/edit-failed");
	}

	@RequestMapping(value = "quan-tri/xoa-sach", method = RequestMethod.POST)
	public ModelAndView DeleteBook(@RequestParam(value = "id", required = true) long id) {
		if(id <= 0)
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach/delete-failed");
		
		if(bookServiceImpl.DeleteBook(id))
			return new ModelAndView("redirect:/quan-tri/danh-sach-sach/delete-success");
		
		return new ModelAndView("redirect:/quan-tri/danh-sach-sach/delete-failed");
	}
}
