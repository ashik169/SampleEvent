package com.qinnovation.sample.webservice;

import com.qinnovation.sample.master.model.MenuDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface IMenuService {

    @GET("indexrs/index.php/app/menu_fetch/")
    Call<List<MenuDetail>> getMenuList();
}
