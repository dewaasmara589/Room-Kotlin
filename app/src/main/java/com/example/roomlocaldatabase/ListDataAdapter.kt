package com.example.roomlocaldatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlocaldatabase.ForeignKeys.UserWithReport

class ListDataAdapter(private val dataList: List<UserWithReport>):
    RecyclerView.Adapter<ListDataAdapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.tvUId.text = currentItem.uid.toString()
        holder.tvFirstName.text = currentItem.first_name.toString()
        holder.tvLastName.text = currentItem.last_name.toString()
        holder.tvRole.text = currentItem.role.toString()
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvUId: TextView = itemView.findViewById(R.id.tvUId)
        val tvFirstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val tvLastName: TextView = itemView.findViewById(R.id.tvLastName)
        val tvRole: TextView = itemView.findViewById(R.id.tvRole)
    }
}