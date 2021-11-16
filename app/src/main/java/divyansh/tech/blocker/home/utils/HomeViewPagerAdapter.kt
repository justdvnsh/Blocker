package divyansh.tech.blocker.home.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import divyansh.tech.blocker.home.screens.blocked.BlockedFragment
import divyansh.tech.blocker.home.screens.contacts.ContactsFragment

class HomeViewPagerAdapter(
    private val list: MutableList<Fragment> = mutableListOf(),
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    private val pageIds= mutableListOf<Long>()

    fun addFragments(){
        pageIds.clear()
        list.add(ContactsFragment.getInstance())
        list.add(BlockedFragment.getInstance())
        pageIds.addAll(list.map { it.hashCode().toLong() })
        notifyDataSetChanged()
    }

    fun removeAllFragments(){
        list.clear()
        pageIds.clear()
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return pageIds[position]
    }

    override fun containsItem(itemId: Long): Boolean {
        return pageIds.contains(itemId)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}