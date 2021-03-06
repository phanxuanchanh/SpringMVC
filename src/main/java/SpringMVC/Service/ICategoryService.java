package SpringMVC.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringMVC.Entity.Category;

@Service
public interface ICategoryService {
	@Autowired

	List<Category> GetCategories();

	Category GetCategory(int id);

	boolean CreateCategory(Category category);

	boolean UpdateCategory(Category category);

	boolean DeleteCategory(int id);
}
