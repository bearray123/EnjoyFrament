package xyz.rh.enjoyfragment.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ThirdViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun testScope() {
        viewModelScope.launch {


        }
        viewModelScope.cancel()
    }
}