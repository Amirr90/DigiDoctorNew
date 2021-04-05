package com.digidoctor.android.model.pharmacyModel;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.digidoctor.android.BR;

import java.util.List;

public class ProductDetailModelResponse {

    int responseCode;
    String responseMessage;
    List<ProductDetailsList> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<ProductDetailsList> getResponseValue() {
        return responseValue;
    }

    public static class ProductDetailsList {
        List<ProductDetail> productDetails;
        List<ReviewDetails> reviewDetails;
        List<SimilarProduct> similarProduct;
        List<ProductDetailsSlider> imagePath;
        List<SizeDetails> sizeDetails;
        List<FlavourDetails> flavourDetails;
        List<MaterialDetails> materialDetails;
        List<ColorDetails> colorDetails;

        public List<ColorDetails> getColorDetails() {
            return colorDetails;
        }

        public List<MaterialDetails> getMaterialDetails() {
            return materialDetails;
        }

        public List<FlavourDetails> getFlavourDetails() {
            return flavourDetails;
        }


        public List<SizeDetails> getSizeDetails() {
            return sizeDetails;
        }

        public List<ProductDetailsSlider> getImagePath() {
            return imagePath;
        }

        public List<SimilarProduct> getSimilarProduct() {
            return similarProduct;
        }

        public List<ReviewDetails> getReviewDetails() {
            return reviewDetails;
        }

        public List<ProductDetail> getProductDetails() {
            return productDetails;
        }

        public class ProductDetail extends BaseObservable {


            String wishlistStatus;
            String discountedRs;
            private String productName;
            private String brandName;
            private String memberId;
            private int productId;
            private String starRating;
            private String totalRating;
            private int threestarPerc;
            private int twostarPerc;
            private int onestarPerc;
            private int fourstarPerc;
            private int fivestarPerc;
            private String totalreviews;
            private int offeredPrice;
            private String mrp;
            private int availableStock;
            private String shortDescription;
            private String description;
            private String productInfoCode;
            private Integer inCartStatus;
            int isMedicine;
            int knowmedMedicineId;

            @Override
            public String toString() {
                return "ProductDetail{" +
                        "wishlistStatus='" + wishlistStatus + '\'' +
                        ", discountedRs='" + discountedRs + '\'' +
                        ", productName='" + productName + '\'' +
                        ", brandName='" + brandName + '\'' +
                        ", memberId='" + memberId + '\'' +
                        ", productId=" + productId +
                        ", starRating='" + starRating + '\'' +
                        ", totalRating='" + totalRating + '\'' +
                        ", threestarPerc=" + threestarPerc +
                        ", twostarPerc=" + twostarPerc +
                        ", onestarPerc=" + onestarPerc +
                        ", fourstarPerc=" + fourstarPerc +
                        ", fivestarPerc=" + fivestarPerc +
                        ", totalreviews='" + totalreviews + '\'' +
                        ", offeredPrice=" + offeredPrice +
                        ", mrp='" + mrp + '\'' +
                        ", availableStock=" + availableStock +
                        ", shortDescription='" + shortDescription + '\'' +
                        ", description='" + description + '\'' +
                        ", productInfoCode='" + productInfoCode + '\'' +
                        ", inCartStatus=" + inCartStatus +
                        ", isMedicine=" + isMedicine +
                        ", knowmedMedicineId=" + knowmedMedicineId +
                        '}';
            }

            public int getIsMedicine() {
                return isMedicine;
            }

            public void setIsMedicine(int isMedicine) {
                this.isMedicine = isMedicine;
            }

            public int getKnowmedMedicineId() {
                return knowmedMedicineId;
            }

            public void setKnowmedMedicineId(int knowmedMedicineId) {
                this.knowmedMedicineId = knowmedMedicineId;
            }

            public String getDiscountedRs() {
                return discountedRs;
            }

            public void setDiscountedRs(String discountedRs) {
                this.discountedRs = discountedRs;
            }

            public String getWishlistStatus() {
                return wishlistStatus;
            }

            public void setWishlistStatus(String wishlistStatus) {
                this.wishlistStatus = wishlistStatus;
            }

            public Integer getInCartStatus() {
                return inCartStatus;
            }

            public void setInCartStatus(Integer inCartStatus) {
                this.inCartStatus = inCartStatus;
            }

            @Bindable
            public String getProductInfoCode() {
                return productInfoCode;
            }

            public void setProductInfoCode(String productInfoCode) {
                this.productInfoCode = productInfoCode;
                notifyPropertyChanged(BR.productInfoCode);
            }

            public int getFourstarPerc() {
                return fourstarPerc;
            }

            public void setFourstarPerc(int fourstarPerc) {
                this.fourstarPerc = fourstarPerc;
            }

            public int getFivestarPerc() {
                return fivestarPerc;
            }

            public void setFivestarPerc(int fivestarPerc) {
                this.fivestarPerc = fivestarPerc;
            }

            public int getTwostarPerc() {
                return twostarPerc;
            }

            public void setTwostarPerc(int twostarPerc) {
                this.twostarPerc = twostarPerc;
            }

            public int getOnestarPerc() {
                return onestarPerc;
            }

            public void setOnestarPerc(int onestarPerc) {
                this.onestarPerc = onestarPerc;
            }


            public int getThreestarPerc() {
                return threestarPerc;
            }

            public void setThreestarPerc(int threestarPerc) {
                this.threestarPerc = threestarPerc;
            }


            public Spanned getDescription() {
                return Html.fromHtml(description);
            }

            public void setDescription(String description) {
                this.description = description;
            }


            public String getStarRating() {
                return starRating;
            }

            public void setStarRating(String starRating) {
                this.starRating = starRating;
            }

            public String getTotalRating() {
                return totalRating;
            }

            public void setTotalRating(String totalRating) {
                this.totalRating = totalRating;
            }

            public String getTotalreviews() {
                return totalreviews;
            }

            public void setTotalreviews(String totalreviews) {
                this.totalreviews = totalreviews;
            }

            public int getOfferedPrice() {
                return offeredPrice;
            }

            public void setOfferedPrice(int offeredPrice) {
                this.offeredPrice = offeredPrice;
            }

            public SpannableString getMrp() {
                SpannableString spannableString = new SpannableString(this.mrp);
                spannableString.setSpan(new StrikethroughSpan(), 0, this.mrp.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                return spannableString;
            }

            public void setMrp(String mrp) {
                this.mrp = mrp;
            }

            public int getAvailableStock() {
                return availableStock;
            }

            public void setAvailableStock(int availableStock) {
                this.availableStock = availableStock;
            }

            public String getShortDescription() {
                return shortDescription;
            }

            public void setShortDescription(String shortDescription) {
                this.shortDescription = shortDescription;
            }

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

        }

        public class ReviewDetails {

            String reviewBy;
            String reviewDate;
            String starRating;
            String review;

            public String getReviewBy() {
                return reviewBy;
            }

            public void setReviewBy(String reviewBy) {
                this.reviewBy = reviewBy;
            }

            public String getName() {
                return reviewBy;
            }

            public void setName(String name) {
                this.reviewBy = name;
            }

            public String getReviewDate() {
                return reviewDate;
            }

            public void setReviewDate(String reviewDate) {
                this.reviewDate = reviewDate;
            }

            public String getStarRating() {
                return starRating;
            }

            public void setStarRating(String starRating) {
                this.starRating = starRating;
            }

            public String getReview() {
                return review;
            }

            public void setReview(String review) {
                this.review = review;
            }

            @Override
            public String toString() {
                return "productReview{" +
                        "name='" + reviewBy + '\'' +
                        ", reviewDate='" + reviewDate + '\'' +
                        ", starRating=" + starRating +
                        ", review='" + review + '\'' +
                        '}';
            }


        }

        public class SimilarProduct {

            int productId;
            String productName;
            String shortDescription;
            String mrp;
            int offeredPrice;
            String productInfoCode;
            String inCartStatus;
            String wishlistStatus;
            String imageURL;


            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getShortDescription() {
                return shortDescription;
            }

            public void setShortDescription(String shortDescription) {
                this.shortDescription = shortDescription;
            }

            public String getMrp() {
                return mrp;
            }

            public void setMrp(String mrp) {
                this.mrp = mrp;
            }

            public int getOfferedPrice() {
                return offeredPrice;
            }

            public void setOfferedPrice(int offeredPrice) {
                this.offeredPrice = offeredPrice;
            }

            public String getProductInfoCode() {
                return productInfoCode;
            }

            public void setProductInfoCode(String productInfoCode) {
                this.productInfoCode = productInfoCode;
            }

            public String getInCartStatus() {
                return inCartStatus;
            }

            public void setInCartStatus(String inCartStatus) {
                this.inCartStatus = inCartStatus;
            }

            public String getWishlistStatus() {
                return wishlistStatus;
            }

            public void setWishlistStatus(String wishlistStatus) {
                this.wishlistStatus = wishlistStatus;
            }

            public String getImageURL() {
                return imageURL;
            }

            public void setImageURL(String imageURL) {
                this.imageURL = imageURL;
            }

            @Override
            public String toString() {
                return "SimilarProduct{" +
                        "productId='" + productId + '\'' +
                        ", productName='" + productName + '\'' +
                        ", shortDescription='" + shortDescription + '\'' +
                        ", mrp='" + mrp + '\'' +
                        ", offeredPrice='" + offeredPrice + '\'' +
                        ", productInfoCode='" + productInfoCode + '\'' +
                        ", inCartStatus='" + inCartStatus + '\'' +
                        ", wishlistStatus='" + wishlistStatus + '\'' +
                        ", imageURL='" + imageURL + '\'' +
                        '}';
            }


        }

        public class ProductDetailsSlider {


            String imagePath;

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            @Override
            public String toString() {
                return "ProductDetailsSlider{" +
                        "imagePath='" + imagePath + '\'' +
                        '}';
            }


        }

        public class SizeDetails {
            int sizeid;
            String size;
            int isSelected;

            @Override
            public String toString() {
                return "SizeDetails{" +
                        "sizeid=" + sizeid +
                        ", size='" + size + '\'' +
                        ", isSelected=" + isSelected +
                        '}';
            }

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }

            public int getSizeid() {
                return sizeid;
            }

            public void setSizeid(int sizeid) {
                this.sizeid = sizeid;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }


        }

        public class FlavourDetails {
            String flavourId;
            String flavour;
            int isSelected;

            @Override
            public String toString() {
                return "FlavourDetails{" +
                        "flavourId='" + flavourId + '\'' +
                        ", flavour='" + flavour + '\'' +
                        ", isSelected=" + isSelected +
                        '}';
            }

            public String getFlavourId() {
                return flavourId;
            }

            public void setFlavourId(String flavourId) {
                this.flavourId = flavourId;
            }

            public String getFlavour() {
                return flavour;
            }

            public void setFlavour(String flavour) {
                this.flavour = flavour;
            }

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }
        }

        public class MaterialDetails {
            int matreialId;
            String material;
            int isSelected;

            @Override
            public String toString() {
                return "MaterialDetails{" +
                        "matreialId=" + matreialId +
                        ", material='" + material + '\'' +
                        ", isSelected=" + isSelected +
                        '}';
            }

            public int getMatreialId() {
                return matreialId;
            }

            public void setMatreialId(int matreialId) {
                this.matreialId = matreialId;
            }

            public String getMaterial() {
                return material;
            }

            public void setMaterial(String material) {
                this.material = material;
            }

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }
        }

        public class ColorDetails {
            int colorId;
            String color;
            String colorCode;

            @Override
            public String toString() {
                return "ColorDetails{" +
                        "colorId=" + colorId +
                        ", color='" + color + '\'' +
                        ", colorCode='" + colorCode + '\'' +
                        ", isSelected=" + isSelected +
                        '}';
            }

            public String getColorCode() {
                return colorCode;
            }

            public void setColorCode(String colorCode) {
                this.colorCode = colorCode;
            }

            int isSelected;

            public int getColorId() {
                return colorId;
            }

            public void setColorId(int colorId) {
                this.colorId = colorId;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }
        }
    }


}
