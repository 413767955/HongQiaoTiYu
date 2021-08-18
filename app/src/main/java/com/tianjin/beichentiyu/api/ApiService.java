package com.tianjin.beichentiyu.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by 黑瞳 on 2019/10/17 21:17
 * E-Mail Address：673236072@qq.com
 */
public interface ApiService {
    @FormUrlEncoded
    @POST()
    Call<String> postIconList(@Url String url, @Field("lang") String lang);
}
