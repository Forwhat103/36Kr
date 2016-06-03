package com.zhuxiaoming.kr36.news.krtv;

import java.util.List;

/**
 * Created by zhuxiaoming on 16/5/14.
 * 新闻界面氪TV的数据类
 */
public class KrTvBean {

    /**
     * code : 0
     * data : {"page":1,"totalCount":0,"data":[{"tv":{"videoSource360":"http://7rfkz6.com1.z0.glb.clouddn.com/360p_5934_XMTMzMDUwMDYwOA.mp4","id":"553","videoSource720":"http://7rfkz6.com1.z0.glb.clouddn.com/720p_5950_XMTMzMDUwMDYwOA.mp4","title":"「一分钟产品」睡不着的时候，听着这个叫Snooz白噪音风扇入眠","duration":"1 分钟","videoSource":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","publishTime":1441879127000,"youkuUrl":"http://v.youku.com/v_show/id_XMTMzMDUwMDYwOA==","videoSource480":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","featureImg":"http://a.36krcnd.com/nil_class/bfcfb399-9b8a-46d4-bb66-5eb4c99335fa/photo-original.png","durationLong":70},"columnName":"氪TV","columnId":"tv","type":"tv"}]}
     */

    private int code;
    /**
     * page : 1
     * totalCount : 0
     * data : [{"tv":{"videoSource360":"http://7rfkz6.com1.z0.glb.clouddn.com/360p_5934_XMTMzMDUwMDYwOA.mp4","id":"553","videoSource720":"http://7rfkz6.com1.z0.glb.clouddn.com/720p_5950_XMTMzMDUwMDYwOA.mp4","title":"「一分钟产品」睡不着的时候，听着这个叫Snooz白噪音风扇入眠","duration":"1 分钟","videoSource":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","publishTime":1441879127000,"youkuUrl":"http://v.youku.com/v_show/id_XMTMzMDUwMDYwOA==","videoSource480":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","featureImg":"http://a.36krcnd.com/nil_class/bfcfb399-9b8a-46d4-bb66-5eb4c99335fa/photo-original.png","durationLong":70},"columnName":"氪TV","columnId":"tv","type":"tv"}]
     */

    private DataBean data;

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

    public static class DataBean {
        private int page;
        private int totalCount;
        private int pageSize;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * tv : {"videoSource360":"http://7rfkz6.com1.z0.glb.clouddn.com/360p_5934_XMTMzMDUwMDYwOA.mp4","id":"553","videoSource720":"http://7rfkz6.com1.z0.glb.clouddn.com/720p_5950_XMTMzMDUwMDYwOA.mp4","title":"「一分钟产品」睡不着的时候，听着这个叫Snooz白噪音风扇入眠","duration":"1 分钟","videoSource":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","publishTime":1441879127000,"youkuUrl":"http://v.youku.com/v_show/id_XMTMzMDUwMDYwOA==","videoSource480":"http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4","featureImg":"http://a.36krcnd.com/nil_class/bfcfb399-9b8a-46d4-bb66-5eb4c99335fa/photo-original.png","durationLong":70}
         * columnName : 氪TV
         * columnId : tv
         * type : tv
         */

        private List<DataBeanInternal> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<DataBeanInternal> getData() {
            return data;
        }

        public void setData(List<DataBeanInternal> data) {
            this.data = data;
        }

        public static class DataBeanInternal {
            /**
             * videoSource360 : http://7rfkz6.com1.z0.glb.clouddn.com/360p_5934_XMTMzMDUwMDYwOA.mp4
             * id : 553
             * videoSource720 : http://7rfkz6.com1.z0.glb.clouddn.com/720p_5950_XMTMzMDUwMDYwOA.mp4
             * title : 「一分钟产品」睡不着的时候，听着这个叫Snooz白噪音风扇入眠
             * duration : 1 分钟
             * videoSource : http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4
             * publishTime : 1441879127000
             * youkuUrl : http://v.youku.com/v_show/id_XMTMzMDUwMDYwOA==
             * videoSource480 : http://7rfkz6.com1.z0.glb.clouddn.com/480p_150908_snooz.mp4
             * featureImg : http://a.36krcnd.com/nil_class/bfcfb399-9b8a-46d4-bb66-5eb4c99335fa/photo-original.png
             * durationLong : 70
             */
            private TvBean tv;
            private String columnName;
            private String columnId;
            private String type;

            public TvBean getTv() {
                return tv;
            }

            public void setTv(TvBean tv) {
                this.tv = tv;
            }

            public String getColumnName() {
                return columnName;
            }

            public void setColumnName(String columnName) {
                this.columnName = columnName;
            }

            public String getColumnId() {
                return columnId;
            }

            public void setColumnId(String columnId) {
                this.columnId = columnId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class TvBean {
                private String videoSource360;
                private String id;
                private String videoSource720;
                private String title;
                private String duration;
                private String videoSource;
                private long publishTime;
                private String youkuUrl;
                private String videoSource480;
                private String featureImg;
                private int durationLong;

                public String getVideoSource360() {
                    return videoSource360;
                }

                public void setVideoSource360(String videoSource360) {
                    this.videoSource360 = videoSource360;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getVideoSource720() {
                    return videoSource720;
                }

                public void setVideoSource720(String videoSource720) {
                    this.videoSource720 = videoSource720;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getVideoSource() {
                    return videoSource;
                }

                public void setVideoSource(String videoSource) {
                    this.videoSource = videoSource;
                }

                public long getPublishTime() {
                    return publishTime;
                }

                public void setPublishTime(long publishTime) {
                    this.publishTime = publishTime;
                }

                public String getYoukuUrl() {
                    return youkuUrl;
                }

                public void setYoukuUrl(String youkuUrl) {
                    this.youkuUrl = youkuUrl;
                }

                public String getVideoSource480() {
                    return videoSource480;
                }

                public void setVideoSource480(String videoSource480) {
                    this.videoSource480 = videoSource480;
                }

                public String getFeatureImg() {
                    return featureImg;
                }

                public void setFeatureImg(String featureImg) {
                    this.featureImg = featureImg;
                }

                public int getDurationLong() {
                    return durationLong;
                }

                public void setDurationLong(int durationLong) {
                    this.durationLong = durationLong;
                }
            }
        }
    }
}
