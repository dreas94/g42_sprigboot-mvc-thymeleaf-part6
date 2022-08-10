package se.lexicon.mvcthymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;
import se.lexicon.mvcthymeleaf.service.CategoryService;
import se.lexicon.mvcthymeleaf.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController
{

    ProductService productService;
    CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService)
    {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // http://localhost:8080/product/list
    @GetMapping("/list")
    public String showAllProductList(Model model)
    {
        System.out.println("showAllProductList method has been executed!");
        List<ProductView> productViews = productService.findAll();
        model.addAttribute("productViews", productViews);

        model.addAttribute("productListSize", productService.productListSize());
        return "product/products-view";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable("id") Integer id, Model model)
    {
        System.out.println("ID:" + id);

        model.addAttribute("productView", productService.findById(id));
        model.addAttribute("categoryViews", categoryService.findAll());

        return "product/product-view";
    }

    @PostMapping("/view")
    public String findByIdPost(@RequestParam("id") Integer id, Model model)
    {
        ProductView productView = productService.findById(id);
        model.addAttribute("productView", productView);
        model.addAttribute("categoryViews", categoryService.findAll());

        return "product/product-view";
    }

    @GetMapping("/edit/{id}")
    public String displayProductEditForm(@PathVariable("id") Integer id, Model model)
    {
        System.out.println("editProductForm method has been executed for id " + id + "!");

        ProductView productView = productService.findById(id);
        ;

        ProductForm productForm = new ProductForm(productView.getId(), productView.getName(), productView.getPrice(), productView.getCategoryView().getId(), productView.getDate());

        model.addAttribute("productForm", productForm);
        model.addAttribute("categoryViews", categoryService.findAll());

        return "product/product-edit-form";
    }

    // http://localhost:8080/product/form
    @GetMapping("/form")
    public String displayProductForm(Model model)
    {
        System.out.println("displayProductForm method has been executed!");
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);

        model.addAttribute("categoryViews", categoryService.findAll());
        return "product/product-form";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("productForm") @Valid ProductForm productForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        System.out.println("productForm = " + productForm);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("categoryViews", categoryService.findAll());
            return "product/product-form";
        }

        productService.create(productForm);

        redirectAttributes.addFlashAttribute("message", " Product name " + productForm.getName() + " is added successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/product/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("productForm") @Valid ProductForm productForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        System.out.println("productForm = " + productForm);

        if (bindingResult.hasErrors())
        {
            model.addAttribute("categoryViews", categoryService.findAll());
            return "product/product-form";
        }

        productService.update(productForm);

        redirectAttributes.addFlashAttribute("message", " Product name " + productForm.getName() + " is added successfully!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes)
    {
        System.out.println("ID:" + id);
        boolean result = productService.delete(id);
        if (result)
        {
            redirectAttributes.addFlashAttribute("message", "Category Id " + id + " was successfully deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert alert-info");
        }
        else
        {
            // display error message
        }

        return "redirect:/product/list";
    }

}
