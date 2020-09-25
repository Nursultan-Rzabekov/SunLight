
package com.example.sunlightdesign.data.source.dataSource.remote.account

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList


class AccountRemoteDataSource(private val apiServices: AccountServices) : AccountDataSource
{

    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getCountriesList(): List<CountriesList> {
        return apiServices.getListCountriesRegionsCities().await()
    }

}