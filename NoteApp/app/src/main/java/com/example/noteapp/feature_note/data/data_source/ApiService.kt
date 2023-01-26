package com.odlesinc.odles.remotedata.api

import com.google.gson.JsonObject
import com.odlesinc.odles.cartlist.model.AddressListModel
import com.odlesinc.odles.cartlist.model.ConfirmOrderModel
import com.odlesinc.odles.cartlist.model.WishListModel
import com.odlesinc.odles.home.model.BannerModel
import com.odlesinc.odles.home.model.FAQModel
import com.odlesinc.odles.home.model.IndustryBrandsModel
import com.odlesinc.odles.home.model.RFQSubmitModel
import com.odlesinc.odles.payment.model.PaymentRequests
import com.odlesinc.odles.payment.model.PaymentResponse
import com.odlesinc.odles.remotedata.constants.ApiConstants
import com.odlesinc.odles.remotedata.response.ResponseData
import com.odlesinc.odles.signup.model.CountryData
import com.odlesinc.odles.signup.model.RegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //======================================================================================================================================================
    //MARK : Auth API Call for login
    @FormUrlEncoded
    @POST(ApiConstants.loginApi)
    suspend fun userLogin(
        @Field("email") email: String, @Field("password") password: String,
        @Field("os") os: String, @Field("device_token") device_token: String,
        @Field("uuid") uuid: String
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : Auth API Call for registration
    @FormUrlEncoded
    @POST(ApiConstants.registerApi)
    suspend fun userRegistration(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("phone") phone: String,
        @Field("company_name") company_name: String,
        @Field("company_address") company_address: String,
        @Field("country_id") country_id: String,
        @Field("state_id") state_id: String,
        @Field("city_id") city_id: String,
        @Field("gst_number") gst_number: String,
        @Field("pan_number") pan_number: String,
        @Field("designation") designation: String,
        @Field("role") role: String,
        @Field("business_name") business_name: String,
        @Field("industry_id") industry_id: String,
        @Field("company_contact_person_name") company_contact_person_name: String,
        @Field("company_person_contact_number") company_person_contact_number: String,
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : Auth API Call for check e-mail
    @FormUrlEncoded
    @POST(ApiConstants.checkEmailApi)
    suspend fun checkEmail(
        @Field("email") email: String
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for forgot password
    @FormUrlEncoded
    @POST(ApiConstants.otpApi)
    suspend fun forgotPassword(@Field("contact_number") contact_number: String): ResponseData

    //======================================================================================================================================================
    //MARK : API Call for forgot password
    @FormUrlEncoded
    @POST(ApiConstants.logoutApi)
    suspend fun logout(@Field("device_token") device_token: String): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for GST Access Token
    /*   @FormUrlEncoded
       @POST(ApiConstants.gstTokenAPI)
       suspend fun gstTokenAPI(
           @Field("username") username: String,
           @Field("password") password: String,
           @Field("client_id") client_id: String,
           @Field("client_secret") client_secret: String,
           @Field("grant_type") grant_type: String,
       ): Response<GSTTokenModel>*/

    //======================================================================================================================================================
    //MARK : API Call for search GST API
    @GET(ApiConstants.searchGSTINAPI)
    suspend fun searchGSTINAPI(
        @Query("gstin") gstin: String
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for forgot password
    @FormUrlEncoded
    @POST(ApiConstants.resetPasswordApi)
    suspend fun resetPassword(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
    ): RegistrationResponse

    //======================================================================================================================================================
    //MARK : API Call for forgot password
    @Multipart
    @POST(ApiConstants.updateProfileApi)
    suspend fun updateProfile(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("designation") designation: RequestBody,
        @Part("company_contact_person_name") company_contact_person_name: RequestBody,
        @Part("company_person_contact_number") company_person_contact_number: RequestBody,
        @Part("company_address") company_address: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("state_id") state_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part profile_photo: MultipartBody.Part
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for forgot password
    @FormUrlEncoded
    @POST(ApiConstants.changePasswordApi)
    suspend fun changePassword(
        @Field("id") id: String,
        @Field("old_password") old_password: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
    ): Response<RegistrationResponse>

    //======================================================================================================================================================
    //MARK : API Call for country list
    @GET(ApiConstants.countryListApi)
    suspend fun getCountryList(): Response<CountryData>

    //======================================================================================================================================================
    //MARK : API Call for state list
    @FormUrlEncoded
    @POST(ApiConstants.stateListApi)
    suspend fun getStateList(@Field("country_id") country_id: String): Response<CountryData>

    //======================================================================================================================================================
    //MARK : API Call for city list
    @FormUrlEncoded
    @POST(ApiConstants.cityListApi)
    suspend fun getCityList(@Field("state_id") state_id: String): Response<CountryData>

    //======================================================================================================================================================
    //MARK : API Call for industries list
    @POST(ApiConstants.industriesListApi)
    suspend fun getIndustriesList(): Response<CountryData>

    //======================================================================================================================================================
    //MARK : API call for FAQ list
    @GET(ApiConstants.faqListApi)
    suspend fun getFAQList(): FAQModel

    //======================================================================================================================================================
    //MARK : API Call for address type
    @GET(ApiConstants.addressTypeApi)
    suspend fun getAddressType(): Response<CountryData>

    //======================================================================================================================================================
    //MARK : API Call for address List
    @FormUrlEncoded
    @POST(ApiConstants.addressListApi)
    suspend fun getUserAddressList(@Field("user_id") userId: String): Response<AddressListModel>

    //======================================================================================================================================================
    //MARK : API Call for add address
    @FormUrlEncoded
    @POST(ApiConstants.addAddressApi)
    suspend fun addAddress(
        @Field("name") name: String,
        @Field("contact_number") contact_number: String,
        @Field("pincode") pincode: String,
        @Field("state_id") state_id: String,
        @Field("city_id") city_id: String,
        @Field("user_id") userId: String,
        @Field("address") address: String,
        @Field("address_type_id") address_type_id: String
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for add address
    @FormUrlEncoded
    @POST(ApiConstants.deleteAddressApi)
    suspend fun deleteAddress(@Field("id") id: String): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for add address
    @FormUrlEncoded
    @POST(ApiConstants.industryBrands)
    suspend fun getBrandsList(@Field("industry_id") industry_id: String): IndustryBrandsModel

    //======================================================================================================================================================
    //MARK : API Call for add address
    @FormUrlEncoded
    @POST(ApiConstants.bannerApi)
    suspend fun getBannerList(@Field("title") title: String): Response<BannerModel>

    //======================================================================================================================================================
    //MARK:API call for get industries & product list
    @FormUrlEncoded
    @POST(ApiConstants.addAndRemoveFavourite)
    suspend fun likeAndUnlike(@Field("user_id") user_id: String, @Field("product_items_id") product_items_id: String, @Field("status") status: String): Response<JsonObject>

    //======================================================================================================================================================
    //MARK:API call for get industries & product list
    @FormUrlEncoded
    @POST(ApiConstants.favouriteList)
    suspend fun getWishLIst(@Field("user_id") user_id: String): Response<WishListModel>

    //======================================================================================================================================================
    //MARK: RFQ API Call
    @Multipart
    @POST(ApiConstants.rfqForm)
    suspend fun rfqFormSubmit(
        @Part("product_name") product_name: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("brand") brand: RequestBody,
        @Part("product_number") product_number: RequestBody,
        @Part profile_photo: MultipartBody.Part
    ): Response<RFQSubmitModel>

    @Multipart
    @POST(ApiConstants.rfqForm)
    suspend fun rfqFormSubmit(
        @Part("product_name") product_name: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("brand") brand: RequestBody,
        @Part("product_number") product_number: RequestBody
    ): Response<RFQSubmitModel>

    //======================================================================================================================================================
    //MARK: Bulk order query API Call
    @Multipart
    @POST(ApiConstants.bulkOrderQuery)
    suspend fun submitBulkOrderQuery(
        @Part("product_name") product_name: RequestBody,
        @Part("brand") product_brand: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("product_number") product_number: RequestBody,
        @Part("contact_name") contact_name: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("email") email: RequestBody,
        @Part("notes") notes: RequestBody,
        @Part profile_photo: MultipartBody.Part
    ): Response<JsonObject>

    @Multipart
    @POST(ApiConstants.bulkOrderQuery)
    suspend fun submitBulkOrderQuery(
        @Part("product_name") product_name: RequestBody,
        @Part("brand") product_brand: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("product_number") product_number: RequestBody,
        @Part("contact_name") contact_name: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("email") email: RequestBody,
        @Part("notes") notes: RequestBody,
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for add address
    @FormUrlEncoded
    @POST(ApiConstants.ratingApi)
    suspend fun ratingToOdles(
        @Field("rating") rating: String,
        @Field("feedback") feedback: String,
        @Field("user_id") user_id: String,
    ): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for add address
    @POST(ApiConstants.paymentSuccess)
    suspend fun getPaymentSuccess(@Body payment_details: PaymentRequests): Response<PaymentResponse>

    //======================================================================================================================================================
    //MARK : API Call for order list
    @FormUrlEncoded
    @POST(ApiConstants.confirmOrderList)
    suspend fun getConfirmOrderList(@Field("user_id") user_id: String): Response<ConfirmOrderModel>

    //======================================================================================================================================================
    //MARK : API Call for delete order
    @FormUrlEncoded
    @POST(ApiConstants.deleteOrder)
    suspend fun updateOrderStatus(@Field("id") id: String, @Field("status") status: String): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for delete order
    @FormUrlEncoded
    @POST(ApiConstants.notificationCount)
    suspend fun notificationCount(@Field("user_id") user_id: String): Response<JsonObject>

    //======================================================================================================================================================
    //MARK : API Call for delete order
    @FormUrlEncoded
    @POST(ApiConstants.startSellingAPI)
    suspend fun startSellingAPI(
        @Field("supplier_name") supplier_name: String,@Field("mobile_no") mobile_no : String,
        @Field("company_name") company_name: String,@Field("company_address") company_address: String,
        @Field("GST_No") gst_number: String,@Field("selling_product") selling_product: String,
    ): Response<JsonObject>
    //======================================================================================================================================================
}