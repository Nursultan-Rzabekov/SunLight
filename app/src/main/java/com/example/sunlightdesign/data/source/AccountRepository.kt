

package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList

/**
 * Interface to the data layer.
 */
interface AccountRepository {
    suspend fun getCountriesList() : List<CountriesList>
}
