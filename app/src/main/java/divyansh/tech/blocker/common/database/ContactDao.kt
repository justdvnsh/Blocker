package divyansh.tech.blocker.common.database

import androidx.room.*
import divyansh.tech.blocker.common.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM contactsTable")
    suspend fun getAllBlockedContacts(): List<ContactModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContactToBlocked(contactModel: ContactModel)

    @Delete
    suspend fun removeContactFromBlocked(contactModel: ContactModel)
}