package com.tianjin.beichentiyu.bean;

import java.util.List;

public class ArticleListBean extends BaseRespBean {


    /**
     * code : 99999
     * pageNo : 1
     * totalPage : 1
     * list : [{"imgUrl":"http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg","seeNum":"85","title":"测试文章4","genTime":"2019-06-04 15:29:20.0","type":"1","aId":"4"},{"imgUrl":"http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg","seeNum":"45","title":"测试文章3","genTime":"2019-06-04 15:29:20.0","type":"1","aId":"3"},{"imgUrl":"http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg","seeNum":"3","title":"测试文章2","genTime":"2019-06-04 15:29:20.0","type":"1","aId":"2"},{"imgUrl":"http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg","seeNum":"2","title":"测试文章1","genTime":"2019-06-04 15:29:20.0","type":"1","aId":"1"}]
     */


    private int pageNo;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * imgUrl : http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg
         * seeNum : 85
         * title : 测试文章4
         * genTime : 2019-06-04 15:29:20.0
         * type : 1
         * aId : 4
         */

        private String imgUrl;
        private String seeNum;
        private String title;
        private String genTime;
        private String type;
        private String aId;
        private String content;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSeeNum() {
            return seeNum;
        }

        public void setSeeNum(String seeNum) {
            this.seeNum = seeNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAId() {
            return aId;
        }

        public void setAId(String aId) {
            this.aId = aId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
