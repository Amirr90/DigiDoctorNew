package com.digidoctor.android.model;

public class UploadedFileModel {
    private String filePath;
    private String fileType;

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

    @Override
    public String toString() {
        return "{" +
                "filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
