package com.zhuxiaoming.kr36.news.all;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/27.
 * 轮播图的数据类
 */
public class CycleVIewBean {

    /**
     * code : 0
     * data : {"pics":[{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/27/4eecdbda794165fad6f126149cdde132.jpg","location":"https://z.36kr.com/roadshow/13","title":"路演集锦"},{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/25/1f39c3c4d3966ba1e8fd2e72a5ec8595.jpg","location":"https://huodong.36kr.com/h5/star/index.html?ktm_source=xsdappbanner","title":"创业星生代排行"},{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/25/9226ee3eea54231789ed837bcca431a0.png","location":"http://36kr.com/p/5047306.html","title":"视频直播"},{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/16/599293570522dcb4b29b364d8a25c635.jpg","location":"http://qiye.36kr.com/activity/?ktm_source=36kr&ktm_campaign=saas&ktm_medium=pc&ktm_term=appbanner","title":"靠谱"},{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/24/ebc7ef43db5bca2633973da4b217c905.png","location":"http://chuang.36kr.com/huodong#/activityApply/details/325?ktm_src=gsxywz","title":"怪兽学院"},{"action":"web","imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/16/7990bdfc420398442574d7fd4339c296.png","location":"http://chuang.36kr.com/huodong#/activityApply/details/324","title":"市场体育沙龙"}]}
     * msg : 操作成功！
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * action : web
         * imgUrl : https://krplus-pic.b0.upaiyun.com/201605/27/4eecdbda794165fad6f126149cdde132.jpg
         * location : https://z.36kr.com/roadshow/13
         * title : 路演集锦
         */

        private List<PicsBean> pics;

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class PicsBean {
            private String action;
            private String imgUrl;
            private String location;
            private String title;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
