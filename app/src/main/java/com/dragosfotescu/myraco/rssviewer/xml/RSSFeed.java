package com.dragosfotescu.myraco.rssviewer.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of FeedItems make the entire RSSFeed
 * Created by surajx on 21/4/15.
 */
public class RSSFeed implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<FeedItem> feedItem;

    RSSFeed() {
        feedItem = new ArrayList<>();
    }

    public void addItem(FeedItem item) {
        feedItem.add(item);
    }

    public List<FeedItem> getFeedList() {
        return feedItem;
    }

    public int getItemCount() {
        if (feedItem != null) {
            return feedItem.size();
        }
        return 0;
    }

    public RSSFeed feedFilter(String itemCategory) {
        RSSFeed filteredFeed = new RSSFeed();
        for (FeedItem item : feedItem) {
            if (item.getItemCategory().equals(itemCategory))
                filteredFeed.addItem(item);
        }
        return filteredFeed;
    }
}
