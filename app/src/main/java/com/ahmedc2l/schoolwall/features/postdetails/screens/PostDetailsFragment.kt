package com.ahmedc2l.schoolwall.features.postdetails.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ahmedc2l.schoolwall.R
import com.ahmedc2l.schoolwall.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPostDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post_details,
            container,
            false)

        val arguments = PostDetailsFragmentArgs.fromBundle(arguments!!)

        binding.textViewDetails.text = arguments.post.toString()

        return binding.root
    }
}