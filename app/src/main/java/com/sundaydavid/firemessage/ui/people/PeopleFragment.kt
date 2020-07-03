package com.sundaydavid.firemessage.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.google.firebase.firestore.ListenerRegistration
import com.sundaydavid.firemessage.AppConstants
import com.sundaydavid.firemessage.R
import com.sundaydavid.firemessage.chat.ChatActivity
import com.sundaydavid.firemessage.recyclerView.item.PersonItem
import com.sundaydavid.firemessage.util.FireStoreUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_people.*
import org.jetbrains.anko.support.v4.startActivity

class PeopleFragment : Fragment() {

        private lateinit var userListenerRegistration: ListenerRegistration
        private var shouldInitRecyclerView = true

        private lateinit var peopleSection: Section

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        userListenerRegistration = FireStoreUtil.addUserListener(this.requireActivity(), this::updateRecyclerView)
        val root = inflater.inflate(R.layout.fragment_people, container, false)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FireStoreUtil.removeListener(userListenerRegistration)
        shouldInitRecyclerView = true
    }
    private fun updateRecyclerView(items: List<Item>) {

        fun init() {
            recycler_view_people.apply {
                layoutManager = LinearLayoutManager(this@PeopleFragment.context)
                adapter = GroupAdapter<GroupieViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }
        fun updateItems()  = peopleSection.update(items)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()
    }
    private val onItemClick = OnItemClickListener {item, view ->
        if (item is PersonItem) {
            startActivity<ChatActivity>(
                AppConstants.USER_NAME to item.person.name,
                AppConstants.USER_ID to item.userId
            )
        }
    }
}