package com.example.sunlightdesign.data.source.dataSource.remote.auth


import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin


class AuthRemoteDataSource(private val apiServices: AuthServices) :
    AuthDataSource {

    override suspend fun getTasks(model: SetLogin): Login {
        // Simulate network by delaying the execution.
        return apiServices.getLoginAuth(model.phone, model.password).await()
    }

//    private fun apiJsonMap(phone: String, password: String): JsonObject {
//        var gsonObject = JsonObject()
//        try {
//            val jsonObj = JSONObject()
//            jsonObj.put("phone",phone)
//            jsonObj.put("password",password)
//            val jsonParser = JsonParser()
//            gsonObject = jsonParser.parse(jsonObj.toString()) as JsonObject
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return gsonObject
//    }

}
