package se.lexicon.mvcthymeleaf.service;

import se.lexicon.mvcthymeleaf.model.dto.CategoryView;
import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;

import java.util.List;

public interface ProductService {

    ProductView findById(int id);

    ProductView create(ProductForm productForm);

    ProductView update(ProductForm productForm);
    List<ProductView> findAll();

    boolean delete(int id);

    int productListSize();
}
