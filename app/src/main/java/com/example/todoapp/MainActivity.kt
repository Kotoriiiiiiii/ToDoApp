package com.example.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: RecyclerViewAdapter

    private val list: MutableList<TodoData> = mutableListOf()
/*
    private val list: List<TodoData> = listOf(
        TodoData(0L, "みかん"),
        TodoData(1L, "りんご"),
        TodoData(2L, "いちご"),
        TodoData(3L, "レモン"),
        TodoData(4L, "メロン"),
        TodoData(5L, "バナナ"),
        TodoData(6L, "スイカ"),
        TodoData(7L, "ぶどう"),
    )

 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {setContentView(this.root)}

        adapter = RecyclerViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val title = binding.taskEditText.text.toString()
            list.add(TodoData(0L,false, title, false))
            adapter.submitList(list.toMutableList())
        }

    /*
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

     */
    }
}