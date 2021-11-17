package divyansh.tech.blocker.home.screens.contacts.callbacks

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.contacts.ContactsViewModel

@AssistedFactory
interface ContactsCallbackFactory {
    fun createCallback(
        activity: FragmentActivity,
        viewModel: ContactsViewModel
    ): ContactsCallbacks
}

class ContactsCallbacks @AssistedInject constructor(
    @Assisted private val activity: FragmentActivity,
    @Assisted private val viewModel: ContactsViewModel
) {
    
    fun onContactClick(contact: ContactModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Block Contact")
            .setMessage("Are you sure you want to block this contact ?")
            .setPositiveButton("Yes") { _, _ -> viewModel.saveContactToBlocked(contact) }
            .setNegativeButton("No", null)
            .show()
    }
}