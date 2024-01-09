package ru.steelwave.steelwave.data.database.dao.project

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(projectDbModel: ProjectDbModel)

    @Query("DELETE FROM projects WHERE id = :projectId")
    suspend fun deleteProject(projectId: Int)

    @Query("SELECT * FROM projects WHERE id = :id LIMIT 1")
    suspend fun getProject(id: Int): ProjectDbModel

    @Query("SELECT * FROM projects")
    fun getAllProjects(): Flow<List<ProjectDbModel>>

}