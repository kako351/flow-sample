package com.kako351.android.flowsample

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(private val repository: FlowSampleRepository): ViewModel() {
    class Factory(
        private val repository : FlowSampleRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }

    fun onClickFab(v: View) {
        repository.flowCountUp().debounce(5000).onEach {
            Snackbar.make(v, "カウント: ${it}", Snackbar.LENGTH_SHORT).show()
        }.launchIn(viewModelScope)
    }
}
