package com.ahmedc2l.schoolwall.features.home.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedc2l.schoolwall.R
import com.ahmedc2l.schoolwall.databinding.FragmentHomeBinding
import com.ahmedc2l.schoolwall.features.home.adapters.PostsAdapter
import com.ahmedc2l.schoolwall.features.home.adapters.PostsClickListener
import com.ahmedc2l.schoolwall.features.home.adapters.SharePostClickListener
import com.ahmedc2l.schoolwall.features.home.entities.Post
import com.ahmedc2l.schoolwall.features.home.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, HomeViewModel.Factory(activity.application)).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.executePendingBindings()

        val postsAdapter = PostsAdapter(
            PostsClickListener {
                viewModel.onPostClicked(it)
            },
            SharePostClickListener {
                it?.let {
                    viewModel.clickSharePostUrl(it)
                }
            }
        )
        binding.recyclerViewPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }

        viewModel.navigateToPostDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(it))
                viewModel.onPostDetailsNavigated()
            }
        })

        viewModel.sharePostUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                startActivity(it)
                viewModel.onPostShared()
            }
        })

        viewModel.allFeed.observe(viewLifecycleOwner, Observer<List<Post>> {
            postsAdapter.submitList(it)
        })

        binding.swipeRefresh.setOnRefreshListener {
            if (binding.swipeRefresh.isRefreshing)
                binding.swipeRefresh.isRefreshing = false;
            viewModel.refreshPosts()
        }

        return binding.root;
    }
}