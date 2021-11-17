package divyansh.tech.blocker.home.screens.blocked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.common.EventObserver
import divyansh.tech.blocker.databinding.FragmentBlockedBinding
import divyansh.tech.blocker.home.screens.blocked.callbacks.BlockedContactFactory
import divyansh.tech.blocker.home.screens.blocked.epoxy.EpoxyBlockedContactsController
import javax.inject.Inject

@AndroidEntryPoint
class BlockedFragment: Fragment() {

    companion object {
        fun getInstance() = BlockedFragment()
    }

    private lateinit var _binding: FragmentBlockedBinding
    private val viewModel by viewModels<BlockedViewModel>()

    @Inject
    lateinit var blockedContactFactory: BlockedContactFactory

    private val callbacks by lazy {
        blockedContactFactory.createBlockedContactFactory(
            requireActivity(),
            viewModel
        )
    }

    private val blockedController by lazy {
        EpoxyBlockedContactsController(callbacks)
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
                    Toast.makeText(requireContext(), "Removed this contact from blocked", Toast.LENGTH_SHORT).show()
                    viewModel.getAllBlockedContacts()
                }
            }
        )
    }
}