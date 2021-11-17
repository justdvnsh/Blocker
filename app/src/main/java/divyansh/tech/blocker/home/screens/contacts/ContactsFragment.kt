package divyansh.tech.blocker.home.screens.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.common.EventObserver
import divyansh.tech.blocker.databinding.FragmentContactsBinding
import divyansh.tech.blocker.home.screens.contacts.callbacks.ContactsCallbackFactory
import divyansh.tech.blocker.home.screens.contacts.callbacks.ContactsCallbacks
import divyansh.tech.blocker.home.screens.contacts.epoxy.ContactsControllerFactory
import divyansh.tech.blocker.home.screens.contacts.epoxy.EpoxyContactsController
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment: Fragment() {

    companion object {
        fun getInstance() = ContactsFragment()
    }

    private lateinit var _binding: FragmentContactsBinding
    private val viewModel by viewModels<ContactsViewModel>()

    @Inject
    lateinit var contactsCallbackFactory: ContactsCallbackFactory

    private val callbacks by lazy {
        contactsCallbackFactory.createCallback(
            requireActivity(),
            viewModel
        )
    }

    @Inject
    lateinit var contactsControllerFactory: ContactsControllerFactory

    private val contactController by lazy {
        contactsControllerFactory.createContactsControllerFactory(callbacks)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
        setupRecyclerView()
        setupObservers()
    }

    private fun checkPermissions() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    //TODO: GET CONTACTS
                    viewModel.getContacts(requireActivity().contentResolver)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) { /* ... */
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun setupRecyclerView() {
        _binding.contactsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactController.adapter
        }
    }

    private fun setupObservers() {
        viewModel.contactsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                contactController.setData(it)
            }
        )

        viewModel.blockContactLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                if (it)
                    Snackbar.make(
                        requireView(),
                        "Contact marked as blocked",
                        Snackbar.LENGTH_SHORT
                    ).show()
            }
        )
    }
}