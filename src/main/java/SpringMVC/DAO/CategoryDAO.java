package SpringMVC.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import SpringMVC.Entity.Category;
import SpringMVC.Entity.CategoryMapper;

@Repository
public class CategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Category> GetCategories() {
		List<Category> categories = new ArrayList<Category>();
		String query = "Select * from Category";
		categories = jdbcTemplate.query(query, new CategoryMapper());
		return categories;
	}

	public Category GetCategory(int id) {
		Category category = null;
		String query = "Select * from Category where Id = ?";
		category = jdbcTemplate.queryForObject(query, new Object[] { id }, new CategoryMapper());
		return category;
	}

	public boolean CreateCategory(Category category) {
		String query = "Insert into Category(name, description) values(?, ?)";
		int affected = jdbcTemplate.update(query, new Object[] { category.getName(), category.getDescription() });
		return (affected > 0);
	}

	public boolean UpdateCategory(Category category) {
		String query = "Update Category set name = ?, description = ? where Id = ?";
		int affected = jdbcTemplate.update(query,
				new Object[] { category.getName(), category.getDescription(), category.getID() });
		return (affected > 0);
	}

	public boolean DeleteCategory(int id) {
		String query = "Delete from Category where Id = ?";
		int affected = jdbcTemplate.update(query, new Object[] { id });
		return (affected > 0);
	}

	public int CountCategory() {
		String query = "Select count(*) from Category";
		int count = jdbcTemplate.queryForObject(query, Integer.class);
		return count;
	}

	public boolean IsExistCategoryById(int id) {
		String query = "Select count(*) from Category where Id = ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] { id }, Integer.class);
		return (count > 0);
	}

	public boolean IsExistCategoryByName(String name) {
		String query = "Select count(*) from Category where name = ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] { name }, Integer.class);
		return (count > 0);
	}
}
