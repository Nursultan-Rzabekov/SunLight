
package com.example.sunlightdesign.data.source.local

import androidx.lifecycle.LiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.TasksDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class TasksLocalDataSource internal constructor(
    private val tasksDao: TasksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksDataSource {


    override suspend fun getTasks(): List<Task> = withContext(ioDispatcher) {
        return@withContext tasksDao.getTasks()
    }
}
