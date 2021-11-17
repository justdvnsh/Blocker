package divyansh.tech.blocker.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import divyansh.tech.blocker.common.ContactModel

@Database(entities = [ContactModel::class], version = 1)
abstract class BlockedContactDatabase(): RoomDatabase() {
    abstract fun getContactDao(): ContactDao
}