package com.digidoctor.android.model;

public class FileModel {

    private String filePath;
    private String fileType;
    private String id;


    public FileModel(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public FileModel() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
