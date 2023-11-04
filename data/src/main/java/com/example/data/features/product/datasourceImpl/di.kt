package com.example.data.features.product.dataSourceImpl

import com.example.data.features.product.dataSourceContract.ProductDataSource
import com.example.data.features.product.dataSourceImpl.ProductDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class di {
    @Binds
    abstract fun bindProductDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource

}