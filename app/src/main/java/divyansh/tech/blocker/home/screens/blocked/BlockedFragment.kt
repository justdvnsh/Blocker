package divyansh.tech.blocker.home.screens.blocked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.databinding.FragmentBlockedBinding

@AndroidEntryPoint
class BlockedFragment: Fragment() {

    companion object {
        fun getInstance() = BlockedFragment()
    }

    private lateinit var _binding: FragmentBlockedBinding

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
    }
}