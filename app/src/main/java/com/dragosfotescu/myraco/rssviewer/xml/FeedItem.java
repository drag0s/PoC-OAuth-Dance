package com.dragosfotescu.myraco.rssviewer.xml;

import java.io.Serializable;

/**
 * Holds an Individual Item in the RSS Feed
 * Created by surajx on 21/4/15.
 */
public class FeedItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String itemTitle;
    private String itemDescription;
    private String itemImage;
    private String itemLink;
    private String itemCategory;

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
