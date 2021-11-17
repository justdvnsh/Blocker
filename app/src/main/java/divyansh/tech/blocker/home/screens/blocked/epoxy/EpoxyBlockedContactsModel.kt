package divyansh.tech.blocker.home.screens.blocked.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.blocker.BR
import divyansh.tech.blocker.R
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.blocked.callbacks.BlockedContactCallbacks

@EpoxyModelClass(layout = R.layout.item_blocked)
abstract class EpoxyBlockedContactsModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var contactModel: ContactModel
    @EpoxyAttribute
    lateinit var callback: BlockedContactCallbacks

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.contact, contactModel)
        binding.setVariable(BR.callbacks, callback)
    }
}