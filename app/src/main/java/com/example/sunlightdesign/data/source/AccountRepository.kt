

package com.example.sunlightdesign.data.source
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse

/**
 * Interface to the data layer.
 */
interface AccountRepository {

    suspend fun getTasks(forceUpdate: Boolean = false): List<LoginResponse>
}
