package com.jingtao.zhbj_jt.bean;

import java.util.ArrayList;

/**
 * 页签详情页数据
 * Created by jingtao on 16/4/19.
 */
public class TabData {

    public int retcode;

    public TabDetail data;

    @Override
    public String toString() {
        return "TabData{" +
                "data=" + data +
                '}';
    }

    public class TabDetail{
        public String title;
        public String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetail{" +
                    "title='" + title + '\'' +
                    ", news=" + news +
                    ", topnews=" + topnews +
                    '}';
        }
    }

    /**
     * 新闻列表对象
     */
    public class TabNewsData{
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    /**
     * 头条新闻
     */
    public class TopNewsData{
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }
}
