

package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList

/**
 * Interface to the data layer.
 */
interface AccountRepository {
    suspend fun getCountriesList() : CountriesList
    suspend fun getUsersList(): UsersList
}
