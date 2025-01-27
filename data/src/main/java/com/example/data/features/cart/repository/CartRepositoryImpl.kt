package com.example.data.features.cart.repository

import com.example.data.features.cart.dataSource.CartDataSource
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
import com.example.domain.features.cart.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
): CartRepository {
    override suspend fun addProductToCart(token:String,addToCartRequest: AddToCartRequest): CartResponse?  {
        return cartDataSource.addProductToCart(token,addToCartRequest)
    }

    override suspend fun updateCartProductQuantity(
        token: String,
        updateUserCartRequest: UpdateUserCartRequest,
        productId: String
    ): CartQuantityResponse? {
        return cartDataSource.updateCartProductQuantity(token, updateUserCartRequest, productId)
    }

    override suspend fun getLoggedUserCart(token: String): Flow<ResultWrapper<CartQuantity?>?> {
     return  cartDataSource.getLoggedUserCart(token)
    }

    override suspend fun deleteSpecificCartItem(token: String, productId: String) {
        cartDataSource.deleteSpecificCartItem(token,productId)
    }

    override suspend fun clearCart(token: String) {
        cartDataSource.clearCart(token)
    }

    override suspend fun checkOut(token: String, userId: String): Flow<ResultWrapper<Session>> {
        return cartDataSource.checkOut(token,userId)
    }


    override suspend fun createCashOrder(
        token: String,
        userId: String,
        shoppingAddingRequest: ShoppingAddingRequest
    ): Flow<ResultWrapper<CashOrderData>> {
        return  cartDataSource.createCashOrder(token,userId,shoppingAddingRequest)
    }

}