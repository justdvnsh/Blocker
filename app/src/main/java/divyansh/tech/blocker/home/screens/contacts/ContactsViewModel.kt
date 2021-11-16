package divyansh.tech.blocker.home.screens.contacts

import android.content.ContentResolver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.home.screens.contacts.source.ContactsLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repo: ContactsLocalRepo
): ViewModel() {

    private val _contactsLiveData = MutableLiveData<ArrayList<ContactModel>>()
    val contactsLiveData get() = _contactsLiveData

    fun getContacts(cr: ContentResolver) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getContacts(cr)
        if (!response.isNullOrEmpty()) _contactsLiveData.postValue(response)
    }
}