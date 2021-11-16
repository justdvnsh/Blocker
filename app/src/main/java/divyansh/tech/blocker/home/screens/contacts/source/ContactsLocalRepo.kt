package divyansh.tech.blocker.home.screens.contacts.source

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import divyansh.tech.blocker.common.ContactModel
import timber.log.Timber
import javax.inject.Inject

class ContactsLocalRepo @Inject constructor() {

    @SuppressLint("Range")
    suspend fun getContacts(cr: ContentResolver): ArrayList<ContactModel> {
        val list = arrayListOf<ContactModel>()
        Timber.e("INSIDE GET ALL CONTACTS METHOD")
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        Timber.e("CURSOR -> $cur")
        cur?.let {
            Timber.e("INSIDE CURSOR NOT NULL")
            if (it.count > 0) {
                while(it.moveToNext()) {
                    val id = it.getString(
                        it.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name = it.getString(
                        it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    )
                    if (it.getInt(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        pCur?.let {
                            while (it.moveToNext()) {
                                val phoneNo = pCur.getString(
                                    pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                                )
                                list.add(
                                    ContactModel(
                                        name = name,
                                        phone = phoneNo
                                    ))
                            }
                            it.close()
                        }
                    }
                }
            }
            it.close()
        }
        Timber.e("CONTACTS -> $list")
        return list
    }
}