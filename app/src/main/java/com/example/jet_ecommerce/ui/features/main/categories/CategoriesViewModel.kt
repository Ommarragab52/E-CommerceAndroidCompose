package com.example.jet_ecommerce.ui.features.main.categories

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.features.category.model.Category
import com.example.domain.features.category.usecase.GetCategoriesUseCase
import com.example.domain.features.subCategories.model.SubCategory
import com.example.domain.features.subCategories.usecase.GetSubCategoriesOnCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesOnCategoryUseCase: GetSubCategoriesOnCategoryUseCase
) : ViewModel(), CategoriesContract.ViewModel {
    private val _states =
        MutableStateFlow<CategoriesContract.State>(CategoriesContract.State.Loading)
    private val _events = MutableStateFlow<CategoriesContract.Event>(CategoriesContract.Event.Idle)
    var subCategoriesList = mutableStateOf<List<SubCategory>>(listOf())
    override val states: StateFlow<CategoriesContract.State> = _states
    override val events: StateFlow<CategoriesContract.Event> = _events
    private val firstCategory = mutableStateOf(Category(id = "6439d5b90049ad0b52b90048"))

    init {
        invokeAction(CategoriesContract.Action.LoadCategories)
    }

    override fun invokeAction(action: CategoriesContract.Action) {
        when (action) {
            is CategoriesContract.Action.LoadCategories -> loadCategories()
            is CategoriesContract.Action.CategoryClick -> categoryClick(action.category)
            is CategoriesContract.Action.SubCategoryItemClick -> subCategoryClick(action.categoryId)
        }
    }

    private fun subCategoryClick(categoryId: String) {
        viewModelScope.launch {
            _events.emit(CategoriesContract.Event.NavigateToProductsList(categoryId))
        }
    }

    private fun categoryClick(category: Category) {
        Log.w("Category in ViewModel ", "$category")
        val categoryId = firstCategory.value.id
        viewModelScope.launch(Dispatchers.IO) {
            getSubCategoriesOnCategoryUseCase.invoke(category.id ?: categoryId!!).collect {
                when (it) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Loading -> {}
                    is ResultWrapper.ServerError -> {}
                    is ResultWrapper.Success -> subCategoriesList.value = it.data
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase.invoke().collect {
                when (it) {
                    is ResultWrapper.Loading -> {
                        _states.emit(CategoriesContract.State.Loading)
                    }

                    is ResultWrapper.Success -> {
                        _states.emit(CategoriesContract.State.Success(it.data))
                    }

                    is ResultWrapper.ServerError -> {
                        _states.emit(CategoriesContract.State.Error(it.error.message ?: "error"))
                    }

                    is ResultWrapper.Error -> {
                        _states.emit(CategoriesContract.State.Error(it.error.message ?: "error"))

                    }
                }
            }
        }
    }
}

