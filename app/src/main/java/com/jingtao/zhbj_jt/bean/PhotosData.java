package com.jingtao.zhbj_jt.bean;

import java.util.ArrayList;

/**
 * 组图数据
 * Created by jingtao on 16/4/22.
 */
public class PhotosData {


    public int retcode;
    public PhotosInfo data;

    public class PhotosInfo {
        public String title;
        public ArrayList<PhotoInfo> news;
    }

    public class PhotoInfo {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
}
