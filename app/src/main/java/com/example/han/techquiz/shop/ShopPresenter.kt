package com.example.han.techquiz.shop

import com.example.han.techquiz.api.city.MainApiClient
import com.example.han.techquiz.api.city.ManiApiResponse
import com.example.han.techquiz.api.city.ShopApi
import com.example.han.techquiz.common.datamodel.BasicItem
import com.example.han.techquiz.common.datamodel.ImageItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ShopPresenter {
    var shopView: ShopView? = null
    var apiClient = MainApiClient()

    fun loadData() {
        val apiCall = apiClient.getMainApi(ShopApi::class.java).shops()
        apiCall.enqueue(object: Callback<List<ManiApiResponse>> {
            override fun onFailure(call: Call<List<ManiApiResponse>>, t: Throwable) {
                //TODO
            }

            override fun onResponse(call: Call<List<ManiApiResponse>>, response: Response<List<ManiApiResponse>>) {
                val data = ArrayList<Any>()
                response.body()?.forEach {
                    when (it.itemType) {
                        "basic" -> data.add(BasicItem(it.title, it.imageUrl, it.subTitle))
                        "single_image" -> data.add(ImageItem(it.imageUrl))
                    }
                }
                shopView?.onLoadDataSuccess(data)
            }
        })
    }
}