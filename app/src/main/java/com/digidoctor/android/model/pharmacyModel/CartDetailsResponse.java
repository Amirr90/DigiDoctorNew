package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class CartDetailsResponse {
    int responseCode;
    String responseMessage;
    List<GetCartDetails> responseValue;


    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<GetCartDetails> getResponseValue() {
        return responseValue;
    }


    public static class GetCartDetails {

        List<GetCartDetails> productDetails;
        List<GetPriceDetails> priceDetails;
        String productName;
        String quantity;
        int amount;
        String brandName;
        String cartId;
        String productImage;
        String productInfoCode;

        public List<GetPriceDetails> getPriceDetails() {
            return priceDetails;
        }

        public List<GetCartDetails> getProductDetails() {
            return productDetails;
        }

        @Override
        public String toString() {
            return "GetCartDetails{" +
                    "productDetails=" + productDetails +
                    ", priceDetails=" + priceDetails +
                    ", productName='" + productName + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", amount=" + amount +
                    ", brandName='" + brandName + '\'' +
                    ", cartId='" + cartId + '\'' +

                    ", productImage='" + productImage + '\'' +

                    ", productInfoCode='" + productInfoCode + '\'' +
                    '}';
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }


        public String getProductInfoCode() {
            return productInfoCode;
        }

        public void setProductInfoCode(String productInfoCode) {
            this.productInfoCode = productInfoCode;
        }


        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }


        public class GetPriceDetails {

            String totalProducts;
            int totalMrp;
            int totalAmount;
            int saveAmount;
            int delievryCharge;
            int couponAmount;
            String couponCode;
            String finalAmount;

            @Override
            public String toString() {
                return "GetPriceDetails{" +
                        "totalProducts='" + totalProducts + '\'' +
                        ", totalMrp=" + totalMrp +
                        ", totalAmount=" + totalAmount +
                        ", saveAmount=" + saveAmount +
                        ", delievryCharge=" + delievryCharge +
                        ", couponAmount=" + couponAmount +
                        ", couponCode='" + couponCode + '\'' +
                        ", finalAmount='" + finalAmount + '\'' +
                        '}';
            }

            public String getFinalAmount() {
                return finalAmount;
            }

            public void setFinalAmount(String finalAmount) {
                this.finalAmount = finalAmount;
            }

            public String getCouponCode() {
                return couponCode;
            }

            public void setCouponCode(String couponCode) {
                this.couponCode = couponCode;
            }

            public int getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(int couponAmount) {
                this.couponAmount = couponAmount;
            }

            public String getTotalProducts() {
                return totalProducts;
            }

            public void setTotalProducts(String totalProducts) {
                this.totalProducts = totalProducts;
            }

            public int getTotalMrp() {
                return totalMrp;
            }

            public void setTotalMrp(int totalMrp) {
                this.totalMrp = totalMrp;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public int getSaveAmount() {
                return saveAmount;
            }

            public void setSaveAmount(int saveAmount) {
                this.saveAmount = saveAmount;
            }

            public int getDelievryCharge() {
                return delievryCharge;
            }

            public void setDelievryCharge(int delievryCharge) {
                this.delievryCharge = delievryCharge;
            }


        }
    }
}

