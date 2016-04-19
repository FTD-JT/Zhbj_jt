package com.jingtao.zhbj_jt.bean;


import java.util.ArrayList;


/**
 * Created by jingtao on 16/4/18.
 */
public class NewsData {

    public int retcode;
    public ArrayList<NewsMenuData> data;

    @Override
    public String toString() {
        return "NewsData{" +
                "data=" + data +
                '}';
    }

    //侧边栏数据对象
    public class NewsMenuData {
        public String id;
        public String title;
        public int type;
        public String url;
        public ArrayList<NewsTabData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    //新闻页面下11个子页标签的数据对象
    public class NewsTabData {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }
}
