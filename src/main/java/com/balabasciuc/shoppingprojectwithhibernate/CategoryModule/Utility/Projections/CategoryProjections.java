package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;

public class CategoryProjections {

    public static class DescriptionOnly
    {
        private Description categoryDescription;

        public DescriptionOnly(Description categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public Description getCategoryDescription() {
            return categoryDescription;
        }
    }
}
