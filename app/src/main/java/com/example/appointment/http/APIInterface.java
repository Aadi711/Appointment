package com.example.appointment.http;

import com.example.appointment.model.ResponseModel;
import com.example.appointment.utils.Constants;

import java.util.HashMap;
import java.util.SortedMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    @Headers({
            Constants.CLIENT_SERVICE_HEADER + Constants.CLIENT_SERVICE,
            Constants.AUTH_KEY_HEADER + Constants.AUTH_KEY,
    })

    @FormUrlEncoded
    @POST(Constants.LOGIN_API)
    Call<ResponseModel> login(@Field(Constants.EMAIL) String email,
                              @Field(Constants.PASSWORD) String password);

    @Headers({
            Constants.CLIENT_SERVICE_HEADER + Constants.CLIENT_SERVICE,
            Constants.AUTH_KEY_HEADER + Constants.AUTH_KEY,
    })
    @FormUrlEncoded
    @POST(Constants.SIGNUP_API)
    Call<ResponseModel> signup(@Field(Constants.USERNAME) String name,
                               @Field(Constants.EMAIL) String email,
                               @Field(Constants.PHONE) String phone,
                               @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Constants.SEND_EMAIL_CODE_API)
    Call<ResponseModel> sendEmailCode(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                      @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                      @Field(Constants.EMAIL) String email);

    @FormUrlEncoded
    @POST(Constants.SEND_OTP_CODE_API)
    Call<ResponseModel> sendOtpCode(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                    @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                    @Field(Constants.EMAIL) String email,
                                    @Field(Constants.OTP) String otp);

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD_API)
    Call<ResponseModel> resetPassword(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                      @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                      @Field(Constants.EMAIL) String email,
                                      @Field(Constants.NEW_PASSWORD) String password);

    @FormUrlEncoded
    @POST(Constants.STORES_API)
    Call<ResponseModel> stores(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                               @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                               @Field(Constants.EMAIL) String email);

    @FormUrlEncoded
    @POST(Constants.STORES_WITH_EMPLOYEE_API)
    Call<ResponseModel> storesWithEmployees(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                            @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                            @Field(Constants.STORE_ID) String storeId);

    @FormUrlEncoded
    @POST(Constants.USER_PROFILE_API)
    Call<ResponseModel> userProfile(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                    @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                    @Field(Constants.ID) String id);

    @FormUrlEncoded
    @POST(Constants.UPDATE_USER_PROFILE_API)
    Call<ResponseModel> updateUserProfile(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                          @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                          @Field(Constants.ID) String id,
                                          @Field(Constants.USERNAME) String name,
                                          @Field(Constants.PHONE) String phone,
                                          @Field(Constants.EMAIL) String email);

    @FormUrlEncoded
    @POST(Constants.UPDATE_USER_PROFILE_IMAGE_API)
    Call<ResponseModel> updateUserProfileImage(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                          @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                          @Field(Constants.ID) String id,
                                          @Field(Constants.IMAGE_SMALL) String image);

    @FormUrlEncoded
    @POST(Constants.EMPLOYEE_PROFILE_API)
    Call<ResponseModel> employeeProfile(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                          @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                          @Field(Constants.EMPLOYEE_ID) String id,
                                        @Field(Constants.DATE) String date);

    @FormUrlEncoded
    @POST(Constants.APPOINTMENT_API)
    Call<ResponseModel> getAppointments(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                        @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                        @Field(Constants.USER_ID) String id,
                                        @Field(Constants.APPOINTMENT_DETAIL) String appointment);

    @FormUrlEncoded
    @POST(Constants.ORDER_API)
    Call<ResponseModel> order(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                                        @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                                        @Field(Constants.USER_ID) String userId,
                                        @Field(Constants.SHOP_NAME) String shopName,
                                        @Field(Constants.EMPLOYEE_NAME) String employeeName,
                                        @Field(Constants.DATE) String date,
                                        @Field(Constants.TIME) String time,
                                        @Field(Constants.DESCRIPTION) String description);

    @FormUrlEncoded
    @POST(Constants.HELP_API)
    Call<ResponseModel> help(@Header(Constants.CLIENT_SERVICE_HEADER_FOR_FAN) String clientservice,
                              @Header(Constants.AUTH_KEY_HEADER_FOR_FAN) String authkey,
                              @Field(Constants.EMAIL) String email,
                              @Field(Constants.DESCRIPTION) String description);

}