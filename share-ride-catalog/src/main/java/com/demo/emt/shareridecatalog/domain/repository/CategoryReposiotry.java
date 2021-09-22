package com.demo.emt.shareridecatalog.domain.repository;

import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.CategoryId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryReposiotry extends JpaRepository<Category, CategoryId> {
    Optional<Category> findByCategoryNameLike(CategoryName categoryName);
    Optional<Category> findByCategoryNameLikeAndGradLike(CategoryName categoryname, City grad);
    Optional<Category> findByGradLike(City grad);
    List<Category> findAllByCategoryNameLike(CategoryName categoryName);
    List<Category> findAllByCategoryNameLikeAndGradLike(CategoryName categoryname,City grad);
    List<Category> findAllByGradLike(City grad);
}
