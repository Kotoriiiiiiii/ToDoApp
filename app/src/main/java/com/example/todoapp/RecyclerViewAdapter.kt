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
    private val _checkGoneEvent = MutableLiveData(-1)
    private val _checkFavoriteEvent = MutableLiveData(-1)
    val checkGoneEvent: LiveData<Int> get() = _checkGoneEvent
    val checkFavoriteEvent: LiveData<Int> get() = _checkFavoriteEvent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoDataViewHolder {
        val view =
            ItemTodoDataCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoDataViewHolder, position: Int) {
        val item = getItem(position)
        // メモを追加
        holder.binding.taskTextView.text = item.title

        //セルがクリックされた時
        holder.binding.constraintLayout.setOnClickListener {
            // EditActivityに遷移する
            val intent = Intent(context, EditActivity::class.java).apply {
                // idを伝える
                putExtra("ID", item.id)
            }
            context.startActivity(intent)
        }

        // チェックボックス（Gone）の状態を設定
        holder.binding.checkBox.isChecked = item.gone

        // チェックボックス（Gone）がクリックされたとき
        holder.binding.checkBox.setOnClickListener {
            _checkGoneEvent.value = item.id
        }

        // お気に入りボタンの状態を設定
        holder.binding.favorite.isChecked = item.favorite

        // お気に入りボタンがクリックされたとき
        holder.binding.favorite.setOnClickListener {
            _checkFavoriteEvent.value = item.id
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
