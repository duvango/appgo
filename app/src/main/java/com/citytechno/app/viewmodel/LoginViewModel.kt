package com.citytechno.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citytechno.app.network.service.RetrofitHelper
import com.citytechno.app.network.service.dto.LoginDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val loginRequestResultLiveData=MutableLiveData<Boolean>()
    fun login(email:String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authService = RetrofitHelper.getAuthService()
                val response= authService.login(LoginDto(email,password))
                //val response= authService.login(LoginDto("santiago@mail.com","passw0rd"))
                if (response.isSuccessful){
                    val tokenDto=response.body()!!
                    Log.d("Developer", "Response tokenDto: $tokenDto")
                }
                loginRequestResultLiveData.postValue(response.isSuccessful)
            }catch (e:Exception){
                Log.e("Developer","Error", e)
            }

        }
    }
}