package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class DeleteCartModelResponse {
    int responseCode;
    String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<getcartdeletedetails> getResponseValue() {
        return responseValue;
    }

    List<getcartdeletedetails> responseValue;


    public class getcartdeletedetails {

        List<CartDetailsResponse.GetCartDetails> productDetails;
        List<CartDetailsResponse.GetCartDetails.GetPriceDetails> priceDetails;

        public List<CartDetailsResponse.GetCartDetails.GetPriceDetails> getPriceDetails() {
            return priceDetails;
        }

        public List<CartDetailsResponse.GetCartDetails> getProductDetails() {
            return productDetails;
        }

        String productName;
        String quantity;
        int amount;

        @Override
        public String toString() {
            return "GetCartDetails{" +
                    "productDetails=" + productDetails +
                    ", priceDetails=" + priceDetails +
                    ", productName='" + productName + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", amount='" + amount + '\'' +
                    ", brandName='" + brandName + '\'' +
                    ", cartId='" + cartId + '\'' +
                    ", memberId='" + memberId + '\'' +
                    ", uniqueNo='" + uniqueNo + '\'' +
                    ", productInfoCode='" + productInfoCode + '\'' +
                    '}';
        }

        String brandName;
        String cartId;
        String memberId;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getUniqueNo() {
            return uniqueNo;
        }

        public void setUniqueNo(String uniqueNo) {
            this.uniqueNo = uniqueNo;
        }

        public String getProductInfoCode() {
            return productInfoCode;
        }

        public void setProductInfoCode(String productInfoCode) {
            this.productInfoCode = productInfoCode;
        }

        String uniqueNo;
        String productInfoCode;


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
            @Override
            public String toString() {
                return "GetPriceDetails{" +
                        "totalProducts='" + totalProducts + '\'' +
                        ", totalMrp='" + totalMrp + '\'' +
                        ", totalAmount='" + totalAmount + '\'' +
                        ", saveAmount='" + saveAmount + '\'' +
                        ", delievryCharge='" + delievryCharge + '\'' +
                        '}';
            }



        }
    }
}


