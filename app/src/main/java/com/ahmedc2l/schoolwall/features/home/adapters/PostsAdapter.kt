package com.ahmedc2l.schoolwall.features.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedc2l.schoolwall.databinding.ItemPostBinding
import com.ahmedc2l.schoolwall.features.home.entities.Post

class PostsAdapter(val clickListener: PostsClickListener, val shareClickListener: SharePostClickListener): ListAdapter<Post, PostsAdapter.ViewHolder>(PostDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, shareClickListener)
    }

    class ViewHolder private constructor(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Post,
            clickListener: PostsClickListener,
            shareClickListener: SharePostClickListener
        ) {
            binding.post = item
            binding.clickListener = clickListener
            binding.shareClickListener = shareClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class PostsClickListener(val clickListener: (post: Post) -> Unit) {
    fun onClick(post: Post) = clickListener(post)
}

class SharePostClickListener(val shareClickListener: (postSharingUrl: String?) -> Unit) {
    fun onClick(postSharingUrl: String?) = shareClickListener(postSharingUrl)
}

class PostDiffCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}