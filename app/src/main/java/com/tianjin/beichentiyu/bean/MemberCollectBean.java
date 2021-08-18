package com.tianjin.beichentiyu.bean;

import java.util.List;

public class MemberCollectBean extends BaseRespBean {

    /**
     * pageNo : 1
     * totalPage : 1
     * list : [{"serialVersionUID":1,"logId":"157565107423330","mId":"1","relId":"157375374232954","type":"4","state":"1","showName":"老人信息","showImg":"http://47.111.177.130/img/six/p_15737536985064.jpg","genTime":"2019-12-0700:51:14"},{"serialVersionUID":1,"logId":"157565104456296","mId":"1","relId":"1","type":"3","state":"1","showName":"测试文章1","showImg":"http://p.store.itangyuan.com/p/chapter/attachment/EgAuEtEwe_M/Eg6SE_2U4B-WetEwetbUe0umHUyLHuDQG6js9Vq4ENeOhm1OG_aX4hb.jpg","genTime":"2019-12-0700:50:44"},{"serialVersionUID":1,"logId":"157565102410851","mId":"1","relId":"1","type":"2","state":"1","showName":"人民医院","showImg":"http://lin1.csttyg.com:1805/img/field/f_15317505738585.jpg","genTime":"2019-12-0700:50:24"},{"serialVersionUID":1,"logId":"157565101005191","mId":"1","relId":"1","type":"1","state":"1","showName":"荣溪园","showImg":"http://47.111.154.197/img/six/p_15744757279408.jpg","genTime":"2019-12-0700:50:10"}]
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
         * serialVersionUID : 1
         * logId : 157565107423330
         * mId : 1
         * relId : 157375374232954
         * type : 4
         * state : 1
         * showName : 老人信息
         * showImg : http://47.111.177.130/img/six/p_15737536985064.jpg
         * genTime : 2019-12-0700:51:14
         */

        private int serialVersionUID;
        private String logId;
        private String mId;
        private String relId;
        private String type;
        private String state;
        private String showName;
        private String showImg;
        private String genTime;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getRelId() {
            return relId;
        }

        public void setRelId(String relId) {
            this.relId = relId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

    }
}
