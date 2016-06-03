package com.zhuxiaoming.kr36.search;

import com.zhuxiaoming.kr36.news.all.NewsBean;

/**
 * Created by zhuxiaoming on 16/5/21.
 */
public class SearchBean {
    private NewsBean newsBean;

    public SearchBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
