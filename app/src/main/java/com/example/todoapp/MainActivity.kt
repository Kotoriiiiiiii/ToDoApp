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
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // データベースの初期化
        db = AppDatabase.getInstance(this.applicationContext)

        // adapterの初期化
        adapter = RecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.checkEvent.observe(this) { id ->
            db.todoDataDao().deleteDataById(id)
            adapter.submitList(db.todoDataDao().getAll())
        }


    }

    override fun onResume() {
        super.onResume()
        // 起動時に前回の情報を読み込んで表示する
        adapter.submitList(db.todoDataDao().getAll())

        binding.addButton.setOnClickListener {
            val titleText = binding.taskEditText.text.toString()

            // 保存したいデータの変数を作る
            val todoData: TodoData = TodoData(title = titleText,)

            // Daoのinsertを呼び出して保存したいデータを渡す
            db.todoDataDao().insert(todoData)
            adapter.submitList(db.todoDataDao().getAll())
        }


    }
}


