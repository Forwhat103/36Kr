package com.zhuxiaoming.kr36.news.details;

/**
 * Created by zhuxiaoming on 16/5/17.
 * 新闻详情界面数据类
 */
public class DetailsBean {

    /**
     * code : 0
     * data : {"summary":"在国内医疗主要靠自费的现状下，慢病管理的盈利可能还是要靠线下提供优质的医疗服务，甚至是一对一、面对面的辅导和管理，让患者信服服务的疗效，才能产生付费意愿。","publishTime":1463475716000,"updateTime":1463478622000,"columnId":70,"commentCount":0,"featureImg":"http://pic1.36krcnd.com/nil_class/17f453c5-336e-4d38-a5f0-cb50c5482070/27558PIC9hJ_1024___.jpg","type":"article","postId":5047082,"content":"","title":"深谈移动医疗 ： 慢病管理领域创业，突围点何在","favoriteCount":7,"myFavorites":false,"columnName":"深度","user":{"avatar":"https://krplus-pic.b0.upaiyun.com/201603/22055321/cg0huhoojxi0iipw.jpg!800","name":"36氪的朋友们","ssoId":2690},"viewCount":386}
     * msg : 操作成功！
     */

    private int code;
    /**
     * summary : 在国内医疗主要靠自费的现状下，慢病管理的盈利可能还是要靠线下提供优质的医疗服务，甚至是一对一、面对面的辅导和管理，让患者信服服务的疗效，才能产生付费意愿。
     * publishTime : 1463475716000
     * updateTime : 1463478622000
     * columnId : 70
     * commentCount : 0
     * featureImg : http://pic1.36krcnd.com/nil_class/17f453c5-336e-4d38-a5f0-cb50c5482070/27558PIC9hJ_1024___.jpg
     * type : article
     * postId : 5047082
     * content :
     * title : 深谈移动医疗 ： 慢病管理领域创业，突围点何在
     * favoriteCount : 7
     * myFavorites : false
     * columnName : 深度
     * user : {"avatar":"https://krplus-pic.b0.upaiyun.com/201603/22055321/cg0huhoojxi0iipw.jpg!800","name":"36氪的朋友们","ssoId":2690}
     * viewCount : 386
     */

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
        private String summary;
        private long publishTime;
        private long updateTime;
        private int columnId;
        private int commentCount;
        private String featureImg;
        private String type;
        private int postId;
        private String content;
        private String title;
        private int favoriteCount;
        private boolean myFavorites;
        private String columnName;
        /**
         * avatar : https://krplus-pic.b0.upaiyun.com/201603/22055321/cg0huhoojxi0iipw.jpg!800
         * name : 36氪的朋友们
         * ssoId : 2690
         */

        private UserBean user;
        private int viewCount;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getFeatureImg() {
            return featureImg;
        }

        public void setFeatureImg(String featureImg) {
            this.featureImg = featureImg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public boolean isMyFavorites() {
            return myFavorites;
        }

        public void setMyFavorites(boolean myFavorites) {
            this.myFavorites = myFavorites;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public static class UserBean {
            private String avatar;
            private String name;
            private int ssoId;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSsoId() {
                return ssoId;
            }

            public void setSsoId(int ssoId) {
                this.ssoId = ssoId;
            }
        }
    }
}
