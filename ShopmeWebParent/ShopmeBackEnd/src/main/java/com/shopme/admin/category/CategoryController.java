package com.shopme.admin.category;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public String listAll(Model model) {
        List<Category> listCategories = service.lisAll();
        model.addAttribute("listCategories", listCategories);
        return "categories/category";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        List<Category> listCategories = service.listCategoriesUsedInform();
        model.addAttribute("pageTile", "Create New Category");
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("category", new Category());
        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    private String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);
        Category saveCategory = service.save(category);
        String uploadDir = "../category-images/" + saveCategory.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        redirectAttributes.addFlashAttribute("message", "the category has been saved succesfully");

        return "redirect:/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Category category = service.getId(id);
            List<Category> listCategory = service.listCategory();

            model.addAttribute("category", category);
            model.addAttribute("listRoles", listCategory);

            return "categories/category_form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/category";
        }
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "the category id " + id + "has been deleted");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage() );
        }
        return "redirect:/category_form";
    }

    @GetMapping("/category/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        service.updateCategoriesEnableStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + "has been" + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/categories";
    }

}
