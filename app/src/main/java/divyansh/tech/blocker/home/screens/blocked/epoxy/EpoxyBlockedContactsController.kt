package divyansh.tech.blocker.home.screens.blocked.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.blocked.callbacks.BlockedContactCallbacks

class EpoxyBlockedContactsController(
    private val callbacks: BlockedContactCallbacks
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