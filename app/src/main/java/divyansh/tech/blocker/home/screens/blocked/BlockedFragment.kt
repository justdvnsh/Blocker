package divyansh.tech.blocker.home.screens.blocked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.R
import divyansh.tech.blocker.common.ContactModel
import divyansh.tech.blocker.common.EventObserver
import divyansh.tech.blocker.databinding.FragmentBlockedBinding
import divyansh.tech.blocker.home.screens.blocked.callbacks.BlockedContactFactory
import divyansh.tech.blocker.home.screens.blocked.epoxy.BlockedContactsControllerFactory
import divyansh.tech.blocker.home.screens.blocked.epoxy.EpoxyBlockedContactsController
import divyansh.tech.blocker.home.screens.contacts.ContactsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class BlockedFragment: Fragment() {

    companion object {
        fun getInstance() = BlockedFragment()
    }

    private lateinit var _binding: FragmentBlockedBinding
    private val viewModel by viewModels<BlockedViewModel>()
    private val contactViewModel by viewModels<ContactsViewModel>()

    @Inject
    lateinit var blockedContactFactory: BlockedContactFactory

    private val callbacks by lazy {
        blockedContactFactory.createBlockedContactFactory(
            requireActivity(),
            viewModel
        )
    }

    @Inject
    lateinit var blockedContactsControllerFactory: BlockedContactsControllerFactory

    private val blockedController by lazy {
        blockedContactsControllerFactory.create(callbacks)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlockedBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        _binding.blockedRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = blockedController.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllBlockedContacts()
    }

    private fun setupObservers() {
        viewModel.blockedContactsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                blockedController.setData(it)
            }
        )

        viewModel.deleteItemLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                if (it) {
                    Snackbar.make(
                        requireView(),
                        "Removed this contact from blocked",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.getAllBlockedContacts()
                }
            }
        )
    }

    private fun setupFab() {
        _binding.fab.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireActivity())
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_number_add_view, null)
            alertDialog.apply {
                setView(view)
                setPositiveButton("Block") {_, _ ->
                    val et = view.findViewById<EditText>(R.id.editText)
                    val value = et.text.toString().trim()
                    Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
                    if (value.isNotEmpty()) {
                        try {
                            val v = value.toInt()
                            contactViewModel.saveContactToBlocked(ContactModel(value, value))
                            viewModel.getAllBlockedContacts()
                        } catch (e: Exception) {
                            Snackbar.make(
                                requireView(),
                                "Enter a valid number to block",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else
                        Snackbar.make(
                            requireView(),
                            "Enter something please",
                            Snackbar.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Cancel", null)
                show()
            }
        }
    }
}