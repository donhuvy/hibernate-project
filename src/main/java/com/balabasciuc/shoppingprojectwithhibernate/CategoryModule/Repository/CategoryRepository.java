package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CategoryProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<CategoryProjections.DescriptionOnly> findByCategoryId(Long categoryId);

    Category getCategoryByCategoryDescription_DescriptionName(String categoryName);
}
