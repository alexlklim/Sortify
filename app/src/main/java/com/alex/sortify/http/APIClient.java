package com.alex.sortify.http;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIClient {

    @GET(Endpoints.GET_PRODUCT)
    Call<ProductDTO> getProduct(@Path("code") String code);

    @POST(Endpoints.CREATE_PRODUCT)
    Call<Void> createProduct(@Body ProductDTO credentials);

}
