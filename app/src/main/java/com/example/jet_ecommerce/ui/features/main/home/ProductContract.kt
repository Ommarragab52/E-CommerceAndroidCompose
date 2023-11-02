package com.example.jet_ecommerce.ui.features.main.home

import androidx.lifecycle.LiveData
import com.example.domain.features.category.model.Category
import com.example.domain.features.products.model.Product
import kotlinx.coroutines.flow.StateFlow

sealed class ProductContract {
    interface ViewModel{
        val productsStates : StateFlow<State>
        val productEvents : LiveData<Event>
        fun invokeProductsAction(action : Action)
    }

    sealed class State{
        object Idle : State()
        class Error(val message:String) : State()
        class ProductSuccess(val products:List<Product?>?) :State()
        object Loading : State()
    }

    sealed class  Action{
        object LoadProducts : Action()
        class CategoryClick(val product: Product) : Action()
    }

    sealed class Event{
        class NavigateToProductDetails(product : Product) : Event()
    }
}