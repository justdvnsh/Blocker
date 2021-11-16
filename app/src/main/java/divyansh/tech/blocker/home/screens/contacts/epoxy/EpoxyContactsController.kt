package divyansh.tech.blocker.home.screens.contacts.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.blocker.common.ContactModel

class EpoxyContactsController(): TypedEpoxyController<ArrayList<ContactModel>>() {
    override fun buildModels(data: ArrayList<ContactModel>?) {
        data?.let {
            it.forEach {
                epoxyContacts {
                    id(it.hashCode())
                    contact(it)
                }
            }
        }
    }
}