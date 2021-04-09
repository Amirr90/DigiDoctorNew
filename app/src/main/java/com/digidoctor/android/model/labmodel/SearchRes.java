package com.digidoctor.android.model.labmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class SearchRes {
    int responseCode;
    String responseMessage;
    List<SearchModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<SearchModel> getResponseValue() {
        return responseValue;
    }

    public static class SearchModel {
        List<Details> details;

        public List<Details> getDetails() {
            return details;
        }

        public static class Details {
            String id;
            String name;
            String cartStatus;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getCartStatus() {
                return cartStatus;
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Details details = (Details) o;
                return Objects.equals(id, details.id) &&
                        Objects.equals(name, details.name) &&
                        Objects.equals(cartStatus, details.cartStatus);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id, name, cartStatus);
            }

            @Override
            public String toString() {
                return "Details{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", cartStatus='" + cartStatus + '\'' +
                        '}';
            }


            public static DiffUtil.ItemCallback<Details> itemCallback = new DiffUtil.ItemCallback<Details>() {
                @Override
                public boolean areItemsTheSame(@NonNull Details oldItem, @NonNull Details newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Details oldItem, @NonNull Details newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }
    }

}
