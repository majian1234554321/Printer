package com.leyuan.printer.http.api;

import com.leyuan.printer.entry.BaseBean;
import com.leyuan.printer.entry.PrintResult;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2017/4/17.
 */

public interface PrintService {

    @GET("campaigns/{code}.json")
    Observable<BaseBean<PrintResult>> getPrintInfo(@Path("code") String code,
                                                   @Query("machineId") String machineId,
                                                   @Query("lessonType") String lessonType);

    @POST("campaigns/{code}.json")
    Observable<BaseBean<PrintResult>> printSuccess(@Path("code") String code,
                                                   @Query("machineId") String machineId,
                                                   @Query("lessonType") String lessonType);

    @GET("device/{machineId}.json")
    Observable<BaseBean<ArrayList<String>>> getBanners(@Path("machineId") String machineId);

    @FormUrlEncoded
    @POST("device/")
    Observable<BaseBean<PrintResult>> getPrintInfo(@Field("code") String code,
                                                   @Field("deviceId") String machineId);
    @FormUrlEncoded
    @POST("device/notify")
    Observable<BaseBean<PrintResult>> printSuccessNotify(@Field("code") String code,
                                                   @Field("deviceId") String machineId);



}
