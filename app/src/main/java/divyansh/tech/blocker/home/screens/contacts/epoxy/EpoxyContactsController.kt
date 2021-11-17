package divyansh.tech.blocker.home.screens.contacts.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.contacts.callbacks.ContactsCallbacks

@AssistedFactory
interface ContactsControllerFactory {
    fun createContactsControllerFactory(
        callbacks: ContactsCallbacks
    ): EpoxyContactsController
}

class EpoxyContactsController @AssistedInject constructor(
    @Assisted private val callbacks: ContactsCallbacks
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