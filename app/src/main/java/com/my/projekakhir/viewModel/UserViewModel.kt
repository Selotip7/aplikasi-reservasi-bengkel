// ViewModel Untuk LiveData, saat terjadi perubahan data maka UI otomatis menggunakan data yang baru.
package com.my.projekakhir.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val nama = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val noHp = MutableLiveData<String>()


}
