package com.tianjin.beichentiyu.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * 银行卡列表
 */
public class MemberBankBean extends BaseRespBean{

    /**
     * code : 99999
     * list : [{"serialVersionUID":1,"bankId":1,"bankName":"建设银行","bankNo":"9558821402001759861","bankTel":"8754752","bankUser":"刘义力","mId":"1","state":"0"},{"serialVersionUID":1,"bankId":2,"bankName":"工商银行","bankNo":"9558821402547259861","bankTel":"548921","bankUser":"刘义力1","mId":"1","state":"0"}]
     */

    private List<ListBean> list;
    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * serialVersionUID : 1
         * bankId : 1
         * bankName : 建设银行
         * bankNo : 9558821402001759861
         * bankTel : 8754752
         * bankUser : 刘义力
         * mId : 1
         * state : 0
         */

        private int serialVersionUID;
        private int bankId;
        private String bankName;
        private String bankNo;
        private String bankTel;
        private String bankUser;
        private String mId;
        private String state;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public String getBankTel() {
            return bankTel;
        }

        public void setBankTel(String bankTel) {
            this.bankTel = bankTel;
        }

        public String getBankUser() {
            return bankUser;
        }

        public void setBankUser(String bankUser) {
            this.bankUser = bankUser;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @NonNull
        @Override
        public String toString() {
            return bankName + bankNo;
        }
    }
}
