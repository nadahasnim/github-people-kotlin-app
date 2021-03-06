package com.dicoding.nadahasnim.mygithubpeoplelist.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nadahasnim.mygithubpeoplelist.DetailActivity
import com.dicoding.nadahasnim.mygithubpeoplelist.adapter.ListPeopleAdapter
import com.dicoding.nadahasnim.mygithubpeoplelist.databinding.FragmentFollowingBinding
import com.dicoding.nadahasnim.mygithubpeoplelist.model.People
import com.dicoding.nadahasnim.mygithubpeoplelist.model.ResponseListUsersItem
import com.dicoding.nadahasnim.mygithubpeoplelist.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()

    private var binding: FragmentFollowingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollowing?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFollowing?.setHasFixedSize(true)

        detailViewModel.following.observe(viewLifecycleOwner) { following ->
            val listPeopleAdapter = ListPeopleAdapter(following)
            binding?.rvFollowing?.adapter = listPeopleAdapter
            listPeopleAdapter.setOnItemClickCallback(object :
                ListPeopleAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ResponseListUsersItem) {
                    openDetail(data)
                }
            })
        }
    }

    fun openDetail(data: ResponseListUsersItem) {
        Log.d(TAG, data.toString())

        val moveToDetailIntent = Intent(activity, DetailActivity::class.java)
        moveToDetailIntent.putExtra(
            DetailActivity.EXTRA_PEOPLE, People(
                data.login,
                data.htmlUrl,
            )
        )
        startActivity(moveToDetailIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding != null) binding = null
    }

    companion object {
        const val TAG = "FollowingFragment"
    }

}