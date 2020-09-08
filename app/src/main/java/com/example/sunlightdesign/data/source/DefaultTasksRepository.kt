package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.di.AppModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Default implementation of [TasksRepository]. Single entry point for managing tasks' data.
 */
class DefaultTasksRepository @Inject constructor(
    @AppModule.TasksRemoteDataSource private val tasksRemoteDataSource: TasksDataSource,
    @AppModule.TasksLocalDataSource private val tasksLocalDataSource: TasksDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    override suspend fun getTasks(forceUpdate: Boolean): List<Task> {
        // Set app as busy while this function executes.
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (ex: Exception) {

            }
        }
        return tasksLocalDataSource.getTasks()
    }



    private suspend fun updateTasksFromRemoteDataSource() {
        tasksRemoteDataSource.getTasks()
    }


}
