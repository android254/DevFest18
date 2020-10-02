package droiddevelopers254.devfestnairobi.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import droiddevelopers254.devfestnairobi.models.StarredSessionModel

@Dao
interface StarredSessionDao {

    @get:Query("SELECT * FROM starredSessions")
    val starredSessions: LiveData<List<StarredSessionModel>>

    @Insert(onConflict = REPLACE)
    fun starSession(starredSessionModel: StarredSessionModel)

    @Query("UPDATE  starredSessions SET isStarred=:starred WHERE documentId =:documentId")
    fun unStarSession(starred: Boolean, documentId: String)

    @Query("SELECT isStarred FROM starredSessions WHERE documentId LIKE :documentId ")
    fun isSessionStarred(documentId: String): Boolean

}
