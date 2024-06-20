package com.example.domain.features.wishlist.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.features.products.model.Product
import com.example.domain.features.wishlist.repository.WishListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveProductFromWishListUseCase @Inject constructor(val repository: WishListRepository) {
     suspend  operator fun invoke(token : String,productId : String): Flow<ResultWrapper<String?>?> {
       return repository.removeProductFromWishlist(token,productId)
    }
}