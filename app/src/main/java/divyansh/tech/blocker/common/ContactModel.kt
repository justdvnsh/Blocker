package divyansh.tech.blocker.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactsTable")
data class ContactModel(
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val phone: String
)