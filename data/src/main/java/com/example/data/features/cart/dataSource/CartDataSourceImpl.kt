package com.example.data.features.cart.dataSource

import com.example.data.api.WebServices
import com.example.data.safeAPiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.features.cart.model.ShoppingAddingRequest
import com.example.domain.features.cart.model.addToCart.AddToCartRequest
import com.example.domain.features.cart.model.getLoggedUse.CartQuantity
import com.example.domain.features.cart.model.getLoggedUse.CartQuantityResponse
import com.example.domain.features.cart.model.addToCart.CartResponse
import com.example.domain.features.cart.model.check_out.CashOrderData
import com.example.domain.features.cart.model.check_out.Session
import com.example.domain.features.cart.model.updateUserCart.UpdateUserCartRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    val webServices: WebServices
):CartDataSource{
    override suspend fun addProductToCart(token:String,addToCartRequest: AddToCartRequest): CartResponse? {
        return webServices.addProductToCart(token,addToCartRequest)
    }

    override suspend fun updateCartProductQuantity(
        token: String,
        updateUserCartRequest: UpdateUserCartRequest,
        productId: String
    ): CartQuantityResponse{
        return   webServices.updateCartProductQuantity(token, updateUserCartRequest, productId)
    }

    override suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>?> {
        return safeAPiCall { webServices.getLoggedUserCart(token) }
    }

    override suspend fun deleteSpecificCartItem(token: String, productId: String) {
        webServices.deleteSpecificCartItem(token = token, productId = productId)
    }

    override suspend fun clearCart(token: String) {
        webServices.clearCart(token)
    }

    override suspend fun checkOut(token: String, userId: String): Flow<ResultWrapper<Session>> {
        return safeAPiCall { webServices.checkOutSession(token,userId) }
    }

    override suspend fun createCashOrder(
        token: String,
        userId: String,
        shoppingAddingRequest: ShoppingAddingRequest
    ): Flow<ResultWrapper<CashOrderData>> {
        return  safeAPiCall { webServices.createCastOrder(token,userId,shoppingAddingRequest) }
    }

}