package se.lexicon.mvcthymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.lexicon.mvcthymeleaf.model.dto.CategoryForm;
import se.lexicon.mvcthymeleaf.model.dto.CategoryView;
import se.lexicon.mvcthymeleaf.model.dto.CategoryForm;
import se.lexicon.mvcthymeleaf.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showAllList(Model model) {
        List<CategoryView> categoryViews = service.findAll();
        model.addAttribute("categoryViews", categoryViews);

        model.addAttribute("categoryViewsSize", categoryViews.size());

        return "category/categories-view";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable("id") Integer id, Model model)
    {
        System.out.println("ID:" + id);

        model.addAttribute("categoryView", service.findById(id));

        return "category/category-view";
    }


    @PostMapping("/view")
    public String findByIdPost(@RequestParam("id") Integer id, Model model) {
        CategoryView categoryView = service.findById(id);
        model.addAttribute("categoryView", categoryView);

        return "category/category-view";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        System.out.println("ID:" + id);
        boolean result = service.delete(id);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Category Id " + id + " was successfully deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert alert-info");
        } else {
            // display error message
        }

        return "redirect:/category/list";
    }

    @GetMapping("/edit/{id}")
    public String displayCategoryEditForm(@PathVariable("id") Integer id, Model model)
    {
        System.out.println("editCategoryForm method has been executed for id " + id + "!");

        CategoryView categoryView = service.findById(id);;

        CategoryForm categoryForm = new CategoryForm(categoryView.getId(), categoryView.getName());

        model.addAttribute("category", categoryForm);

        return "category/category-edit-form";
    }


    @GetMapping("/form")
    public String categoryForm(Model model) {

        CategoryForm categoryForm = new CategoryForm();
        model.addAttribute("category", categoryForm);

        return "category/category-form";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("category") @Valid CategoryForm categoryForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("categoryForm = " + categoryForm);

        if (bindingResult.hasErrors()) {
            return "category/category-form";
        }

        CategoryView createdCategory = service.create(categoryForm);

        redirectAttributes.addFlashAttribute("message", "Category  name " + createdCategory.getName() + " was successfully added");
        redirectAttributes.addFlashAttribute("alertClass", "alert alert-info");

        // simulate a custom error message
        //throw new IllegalArgumentException("Custom Exception");

        return "redirect:/category/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoryForm") @Valid CategoryForm categoryForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        System.out.println("categoryForm = " + categoryForm);

        if (bindingResult.hasErrors()){
            model.addAttribute("categoryViews", service.findAll());
            return "category/category-form";
        }

        service.update(categoryForm);

        redirectAttributes.addFlashAttribute("message", " Category name " + categoryForm.getName() + " is added successfully!" );
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/category/list";
    }

}
