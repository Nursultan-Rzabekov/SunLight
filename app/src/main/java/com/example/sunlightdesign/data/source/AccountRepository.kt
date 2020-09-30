

package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*

/**
 * Interface to the data layer.
 */
interface AccountRepository {
    suspend fun getCountriesList() : CountriesList
    suspend fun getUsersList(): UsersList
    suspend fun addPartner(addPartner: AddPartner): Login
    suspend fun setPackage(package_id:Int, user_id:Int): User
    suspend fun getPackagesList(): PackagesList
    suspend fun getOfficesList(): OfficesList
    suspend fun createOrder(createOrderPartner: CreateOrderPartner): AddPartnerResponse
}
