package com.dicoding.picodiploma.githubuserapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuserapp.UserAdapter.RecyclerViewHolder
import com.github.siyamed.shapeimageview.CircularImageView
import java.util.*

class UserAdapter (private val users: ArrayList<User>) : RecyclerView.Adapter<RecyclerViewHolder>(), Filterable {

    private lateinit var onItemClickDetail: OnItemClickCallBack
    private lateinit var onItemClickShare: OnItemClickCallBack
    private var filterUsers: ArrayList<User> = users

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickDetail = onItemClickCallBack
        this.onItemClickShare = onItemClickCallBack
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val itemSearch = constraint.toString()
                filterUsers = if (itemSearch.isEmpty()) users else {
                    val itemList = ArrayList<User>()
                    for (item in users) {
                        val name = item.name?.lowercase(Locale.ROOT)?.contains(
                            itemSearch.lowercase(
                                Locale.ROOT
                            )
                        )
                        val userName = item.username?.lowercase(Locale.ROOT)?.contains(
                            itemSearch.lowercase(
                                Locale.ROOT
                            )
                        )
                        if (name!! || userName!!) {
                            itemList.add(item)
                        }
                    }
                    itemList
                }
                val filterResults = FilterResults()
                filterResults.values = filterUsers
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterUsers = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = filterUsers.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val user = filterUsers[position]
        Glide.with(holder.itemView)
            .load(user.avatar)
            .apply(RequestOptions().override(300, 300))
            .into(holder.ivAvatar)
        with(holder) {
            tvName.text = user.name
            tvUsername.text = user.username
            tvCompany.text = user.company
            itemView.setOnClickListener { onItemClickDetail.onItemClicked(filterUsers[holder.adapterPosition]) }
        }
    }

    inner class RecyclerViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivAvatar: CircularImageView = itemView.findViewById(R.id.iv_item_avatar)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
        fun onItemShared(data: User)
    }
}