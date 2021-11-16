package divyansh.tech.blocker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.databinding.FragmentHomeBinding
import divyansh.tech.blocker.home.utils.HomeViewPagerAdapter

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _binding: FragmentHomeBinding

    //TODO: Convert this to a shared view model
    //TODO: This viewModel would provide all the contacts.
    private val viewModel by viewModels<HomeViewModel>()

    lateinit var pagerAdapter: HomeViewPagerAdapter
    private var mediator: TabLayoutMediator? = null
    val tabNames= listOf("Contacts","Blocked")

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    //TODO: SETUP FAB -> it would allow user to type in a number to be added to blocked contacts


    //TODO: Setup tab layout with 2 tabs -> Contacts & Blocked
    //TODO: Contacts tab would provide all the contacts in the mobile device
    //TODO: Blocked would contain a list of blocked contacts
    private fun setupTabLayout() {
        pagerAdapter = HomeViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )

        _binding.viewPager2.isUserInputEnabled = true

        _binding.viewPager2.adapter = pagerAdapter
        _binding.viewPager2.registerOnPageChangeCallback(pageChangeCallback)

        if (mediator != null)
            mediator!!.detach()
        pagerAdapter.removeAllFragments()
        pagerAdapter.addFragments()
        _binding.viewPager2.offscreenPageLimit = pagerAdapter.itemCount

        val tabs = mutableListOf<String>().apply {
            addAll(tabNames)
        }

        val strategy =
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabs[position]
            }

        mediator = TabLayoutMediator(
            _binding.tabLayout,
            _binding.viewPager2,
            strategy
        )

        mediator?.attach()
    }

    override fun onStop() {
        _binding.viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onStop()
    }
}