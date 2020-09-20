

package com.example.sunlightdesign.data.source
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.remote.entity.LoginResponse

/**
 * Interface to the data layer.
 */
interface WalletRepository {

    suspend fun getTasks(forceUpdate: Boolean = false): List<LoginResponse>
}
