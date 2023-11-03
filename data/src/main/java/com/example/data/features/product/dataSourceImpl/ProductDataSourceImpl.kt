package com.example.data.features.product.dataSourceImpl

import com.example.data.api.WebServices
import com.example.data.features.product.dataSourceContract.ProductDataSource
import com.example.data.model.BaseResponse
import com.example.data.safeAPiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.features.products.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private  val webServices: WebServices
) : ProductDataSource {
    override suspend fun getProducts(): Flow<ResultWrapper<List<Product?>?>> {
        return safeAPiCall { webServices.getProducts() }
    }

}