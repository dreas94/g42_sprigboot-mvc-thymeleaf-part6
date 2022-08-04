package se.lexicon.mvcthymeleaf.service;

import se.lexicon.mvcthymeleaf.model.dto.CategoryForm;
import se.lexicon.mvcthymeleaf.model.dto.CategoryView;
import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;

import java.util.List;

public interface CategoryService {

    CategoryView findById(int id);

    CategoryView create(CategoryForm form);

    CategoryView update(CategoryForm form);

    List<CategoryView> findAll();

    boolean delete(int id);

    int categoriesSize();

}
