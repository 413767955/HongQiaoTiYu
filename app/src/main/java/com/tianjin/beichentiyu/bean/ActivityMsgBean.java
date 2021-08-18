package com.tianjin.beichentiyu.bean;

public class ActivityMsgBean extends BaseRespBean {


    /**
     * sa : {"aId":"1","address":"福州市鼓楼区古乐路11号","beginTime":"2019-06-16T00:29:22.000","cityId":115,"content":"这个是一个测试的项目","endTime":"2019-06-30T00:29:25.000","genTime":"2019-06-16T00:31:00.000","mId":"1","nowMem":0,"price":20,"proId":13,"seeNum":2,"showImg":"http://47.111.177.130/img/six/123.jpg","state":"1","tel":"123456789","title":"测试活动","type":"3"}
     */

    private SaBean sa;

    public SaBean getSa() {
        return sa;
    }

    public void setSa(SaBean sa) {
        this.sa = sa;
    }

    public static class SaBean {
        /**
         * aId : 1
         * address : 福州市鼓楼区古乐路11号
         * beginTime : 2019-06-16T00:29:22.000
         * cityId : 115
         * content : 这个是一个测试的项目
         * endTime : 2019-06-30T00:29:25.000
         * genTime : 2019-06-16T00:31:00.000
         * mId : 1
         * nowMem : 0
         * price : 20.0
         * proId : 13
         * seeNum : 2
         * showImg : http://47.111.177.130/img/six/123.jpg
         * state : 1
         * tel : 123456789
         * title : 测试活动
         * type : 3
         */

        private String aId;
        private String address;
        private String beginTime;
        private String cityId;
        private String content;
        private String endTime;
        private String genTime;
        private String mId;
        private String nowMem;
        private String price;
        private int proId;
        private int seeNum;
        private String showImg;
        private String state;
        private String tel;
        private String title;
        private String type;

        public String getAId() {
            return aId;
        }

        public void setAId(String aId) {
            this.aId = aId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getNowMem() {
            return nowMem;
        }

        public void setNowMem(String nowMem) {
            this.nowMem = nowMem;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getSeeNum() {
            return seeNum;
        }

        public void setSeeNum(int seeNum) {
            this.seeNum = seeNum;
        }

        public String getShowImg() {
            return showImg;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
