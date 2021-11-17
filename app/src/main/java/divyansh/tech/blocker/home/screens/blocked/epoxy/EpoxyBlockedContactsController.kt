package divyansh.tech.blocker.home.screens.blocked.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.blocked.callbacks.BlockedContactCallbacks

@AssistedFactory
interface BlockedContactsControllerFactory {
    fun create(
        callbacks: BlockedContactCallbacks
    ): EpoxyBlockedContactsController
}

class EpoxyBlockedContactsController @AssistedInject constructor(
    @Assisted private val callbacks: BlockedContactCallbacks
): TypedEpoxyController<List<ContactModel>>() {
    override fun buildModels(data: List<ContactModel>?) {
        data?.let {
            it.forEach {
                epoxyBlockedContacts {
                    id(it.hashCode())
                    contactModel(it)
                    callback(callbacks)
                }
            }
        }
    }
}