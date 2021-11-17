package divyansh.tech.blocker.home.screens.blocked.source

import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.common.Result
import divyansh.tech.blocker.common.database.ContactDao
import javax.inject.Inject

class BlockedContactsLocalRepo @Inject constructor(
    private val dao: ContactDao
) {

    suspend fun getAllBlockedContacts(): Result<List<ContactModel>> {
        return try {
            val response = dao.getAllBlockedContacts()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun removeFromBlocked(contactModel: ContactModel): Result<Boolean> {
        return try {
            dao.removeContactFromBlocked(contactModel)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Success(false)
        }
    }
}