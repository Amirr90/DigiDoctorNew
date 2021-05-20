package com.digidoctor.android.model.patientModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class BlogModel {

    Integer id;
    Integer totalLikes;
    String topic;
    String title;
    String writer;
    String description;
    String imagePath;
    String publishDate;

    public Integer getTotalLikes() {
        return null == totalLikes ? 0 : totalLikes;
    }

    public Integer getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPublishDate() {
        return publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogModel blogModel = (BlogModel) o;
        return Objects.equals(id, blogModel.id) &&
                Objects.equals(topic, blogModel.topic) &&
                Objects.equals(title, blogModel.title) &&
                Objects.equals(writer, blogModel.writer) &&
                Objects.equals(description, blogModel.description) &&
                Objects.equals(imagePath, blogModel.imagePath) &&
                Objects.equals(publishDate, blogModel.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, title, writer, description, imagePath, publishDate);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }

    public static DiffUtil.ItemCallback<BlogModel> itemCallback = new DiffUtil.ItemCallback<BlogModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull BlogModel oldItem, @NonNull BlogModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BlogModel oldItem, @NonNull BlogModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
