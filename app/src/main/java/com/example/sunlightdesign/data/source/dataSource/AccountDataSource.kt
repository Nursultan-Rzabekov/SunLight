
package com.example.sunlightdesign.data.source.dataSource

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin

interface AccountDataSource {
    suspend fun getCountriesList(): List<CountriesList>
}
