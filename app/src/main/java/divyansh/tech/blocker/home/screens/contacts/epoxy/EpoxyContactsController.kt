package divyansh.tech.blocker.home.screens.contacts.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.contacts.callbacks.ContactsCallbacks

class EpoxyContactsController(
    private val callbacks: ContactsCallbacks
): TypedEpoxyController<ArrayList<ContactModel>>() {
    override fun buildModels(data: ArrayList<ContactModel>?) {
        data?.let {
            it.forEach {
                epoxyContacts {
                    id(it.hashCode())
                    contact(it)
                    callbacks(callbacks)
                }
            }
        }
    }
}