package divyansh.tech.blocker.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.databinding.FragmentHomeBinding
import divyansh.tech.blocker.home.utils.HomeViewPagerAdapter
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    
    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var pagerAdapter: HomeViewPagerAdapter
    private var mediator: TabLayoutMediator? = null
    private val tabNames= listOf("Contacts","Blocked")

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {}

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

        Timber.e("CALLING HOME FRAGMENT")
        setupTabLayout()
    }

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