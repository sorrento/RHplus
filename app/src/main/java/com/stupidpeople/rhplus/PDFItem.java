package com.stupidpeople.rhplus;

/**
 * Created by Milenko on 05/03/2016.
 */
public class PDFItem {
    private String dropboxUrl;
    private String imageUrl;
    private String name;
    private String description;

    public PDFItem(String dropboxUrl, String imageUrl, String name, String description) {
        this.dropboxUrl = dropboxUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
    }

    public String getDropboxUrl() {
        return dropboxUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
