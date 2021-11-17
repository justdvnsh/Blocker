package divyansh.tech.blocker.home.screens.blocked.callbacks

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.blocked.BlockedViewModel

@AssistedFactory
interface BlockedContactFactory {
    fun createBlockedContactFactory(
        activity: FragmentActivity,
        viewModel: BlockedViewModel
    ): BlockedContactCallbacks
}

class BlockedContactCallbacks @AssistedInject constructor(
    @Assisted private val activity: FragmentActivity,
    @Assisted private val viewModel: BlockedViewModel
){

    fun onBlockedContactClicked(contactModel: ContactModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("UnBlock Contact")
            .setMessage("Are you sure you want to remove this contact from blocklist ?")
            .setPositiveButton("Yes") { _, _ -> viewModel.deleteFromBlocked(contactModel) }
            .setNegativeButton("No", null)
            .show()
    }
}