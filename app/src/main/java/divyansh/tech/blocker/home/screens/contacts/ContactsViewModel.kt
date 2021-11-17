package divyansh.tech.blocker.home.screens.contacts

import android.content.ContentResolver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.common.Event
import divyansh.tech.blocker.common.Result
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

    private val _blockContactLiveData = MutableLiveData<Event<Boolean>>()
    val blockContactLiveData get() = _blockContactLiveData

    fun getContacts(cr: ContentResolver) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getContacts(cr)
        if (!response.isNullOrEmpty()) _contactsLiveData.postValue(response)
    }

    fun saveContactToBlocked(contactModel: ContactModel) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.addContactToBlocked(contactModel)
        if (response is Result.Success) {
            if (response.data) _blockContactLiveData.postValue(Event(true))
            else _blockContactLiveData.postValue(Event(false))
        }
    }
}