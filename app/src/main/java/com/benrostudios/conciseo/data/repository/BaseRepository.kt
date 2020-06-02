package com.benrostudios.conciseo.data.repository

import android.util.Log
import com.benrostudios.conciseo.data.models.NetworkResult
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call : suspend()-> Response<T>, error : String) :  T?{
        val result = apiOutput(call, error)
        var output : T? = null
        when(result){
            is NetworkResult.Success ->
                output = result.output
            is NetworkResult.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
        return output

    }
    private suspend fun<T : Any> apiOutput(call: suspend()-> Response<T>, error: String) : NetworkResult<T>{
        val response = call.invoke()
        Log.d("Network called",response.toString())
        return if (response.isSuccessful)
            NetworkResult.Success(response.body()!!)
        else
            NetworkResult.Error(IOException("Something went" +
                    " wrong due to  $error"))
    }
}