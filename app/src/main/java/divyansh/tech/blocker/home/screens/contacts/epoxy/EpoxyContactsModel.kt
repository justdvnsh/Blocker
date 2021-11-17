package divyansh.tech.blocker.home.screens.contacts.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.blocker.BR
import divyansh.tech.blocker.R
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.contacts.callbacks.ContactsCallbacks

@EpoxyModelClass(layout = R.layout.item_contact)
abstract class EpoxyContactsModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var contact: ContactModel
    @EpoxyAttribute
    lateinit var callbacks: ContactsCallbacks

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.contact, contact)
        binding.setVariable(BR.callbacks, callbacks)
    }
}