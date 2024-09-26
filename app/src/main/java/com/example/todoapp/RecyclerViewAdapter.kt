package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTodoDataCellBinding


class RecyclerViewAdapter(private val context: Context,): ListAdapter<TodoData, TodoDataViewHolder>(diffCallback) {
    private val _checkEvent = MutableLiveData(-1L)
    val checkEvent: LiveData<Long> get() = _checkEvent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoDataViewHolder {
        val view =
            ItemTodoDataCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoDataViewHolder(view)

    }

    override fun onBindViewHolder(holder: TodoDataViewHolder, position: Int) {
        val item = getItem(position)
        // メモを追加
        holder.binding.taskTextView.text = item.title

        // holder.binding.todoTextView2.text = item.title

        //セルがクリックされた時
        holder.binding.constraintLayout.setOnClickListener {

            // EditActivityに遷移する
            val intent = Intent(context, EditActivity::class.java).apply {
                // idを伝える
                putExtra("ID", item.id)
            }
            context.startActivity(intent)
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TodoData>() {
            override fun areItemsTheSame(oldItem: TodoData, newItem: TodoData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TodoData, newItem: TodoData) =
                oldItem == newItem
        }
    }
}


class TodoDataViewHolder(val binding: ItemTodoDataCellBinding) :
        RecyclerView.ViewHolder(binding.root)