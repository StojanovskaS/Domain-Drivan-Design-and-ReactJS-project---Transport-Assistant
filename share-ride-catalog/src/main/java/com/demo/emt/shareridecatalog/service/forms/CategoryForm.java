package com.demo.emt.shareridecatalog.service.forms;

import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import lombok.Data;

@Data
public class CategoryForm {
    CategoryName categoryName;
    City grad;

    public CategoryForm(String categoryName, City grad) {
        this.categoryName=CategoryName.valueOf(categoryName);
        this.grad=grad;

    }
}
