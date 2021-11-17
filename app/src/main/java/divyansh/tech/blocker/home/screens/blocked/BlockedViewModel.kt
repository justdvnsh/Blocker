package divyansh.tech.blocker.home.screens.blocked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.common.Event
import divyansh.tech.blocker.common.Result
import divyansh.tech.blocker.home.screens.blocked.source.BlockedContactsLocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockedViewModel @Inject constructor(
    private val repo: BlockedContactsLocalRepo
): ViewModel() {

    private val _blockedContactsLiveData = MutableLiveData<List<ContactModel>>()
    val blockedContactsLiveData get() = _blockedContactsLiveData

    private val _deletedItemLiveData = MutableLiveData<Event<Boolean>>()
    val deleteItemLiveData get() = _deletedItemLiveData

    init {
        getAllBlockedContacts()
    }

    fun getAllBlockedContacts() = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getAllBlockedContacts()
        if (response is Result.Success) _blockedContactsLiveData.postValue(response.data as List<ContactModel>)
    }

    fun deleteFromBlocked(contactModel: ContactModel) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.removeFromBlocked(contactModel)
        if (response is Result.Success) {
            if (response.data) _deletedItemLiveData.postValue(Event(true))
            else _deletedItemLiveData.postValue(Event(false))
        }
    }
}