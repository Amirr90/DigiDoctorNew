package com.digidoctor.android.model.pharmacyModel;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class ShopBycategoryModel {

    public ShopBycategoryModel(List<SliderImage> sliderImages, FragmentActivity requireActivity) {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    String categoryName;
    String imagePath;


    List<CategoryModel> categoryList;
    List<PopularProductList> popularProductsList;
    List<TopSearchproductList> searchProductList;
    List<SliderImage> bannerList;
    public List<SliderImage> getBannerList() {
        return bannerList;
    }




    public List<TopSearchproductList> getTopSearchproductLists() {
        return searchProductList;
    }

    public List<PopularProductList> getPopularProductsList() {
        return popularProductsList;
    }


    public List<CategoryModel> getCategoryList() {
        return categoryList;
    }

    public static class CategoryModel {
        private String categoryName;
       private int categoryId;
        private String imagePath;

        @Override
        public String toString() {
            return "CategoryModel{" +
                    "categoryName='" + categoryName + '\'' +
                    ", categoryId=" + categoryId +
                    ", imagePath='" + imagePath + '\'' +
                    '}';
        }

        public String getImagePath() {
            return imagePath;
        }


        public int getCategoryId() {
            return categoryId;
        }



        public String getCategoryName() {
            return categoryName;
        }


    }


    public static class PopularProductList  {
        String productId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        String productName;

        public String getShortDescription() {
            return shortDescription;
        }

        String shortDescription;

        public String getImageURL() {
            return imageURL;
        }

        String imageURL;

        @Override
        public String toString() {
            return "PopularProductList{" +
                    "productId='" + productId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }
    }

    public static class TopSearchproductList {
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        String productId;

        public String getProductName() {
            return productName;
        }

        String productName;

        public String getShortDescription() {
            return shortDescription;
        }

        String shortDescription;

        public String getImageURL() {
            return imageURL;
        }

        String imageURL;

        @Override
        public String toString() {
            return "{" +
                    "productId='" + productId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }

    }

    public static class SliderImage {

        public String getSliderImage() {
            return sliderImage;
        }

        public void setSliderImage(String sliderImage) {
            this.sliderImage = sliderImage;
        }

        private String sliderImage;



    }
}
