package com.example.todoapp

import TodoViewModel
import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity


import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todoapp.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var db: AppDatabase
    private val todoViewModel: TodoViewModel by viewModels()

    private lateinit var todoAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // データベースの初期化
        db = AppDatabase.getInstance(this.applicationContext)

        // adapterの初期化
        adapter = RecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // トグルボタンの機能を追加
        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                // 選択されたボタン以外の選択を解除
                group.checkOnlyButton(checkedId)
            } else {
                // すべてのボタンの選択が解除された場合、何もしない
                if (group.checkedButtonIds.isEmpty()) {
                    return@addOnButtonCheckedListener
                }
            }
            // ここで選択されたボタンに応じた処理を追加できます
            when (checkedId) {
                R.id.gone_button -> {
                    // gone_buttonが選択された時の処理
                    todoViewModel.showGoneItems()

                }

                R.id.never_gone_button -> {
                    // never_gone_buttonが選択された時の処理
                    todoViewModel.showNotGoneItems()

                }

                R.id.favorite_button -> {
                    // favorite_buttonが選択された時の処理
                    todoViewModel.showFavoriteItems()

                }
                // ViewModelのデータを監視してUIを更新
                todoViewModel.todoData.observe(this, Observer { todoData ->
                    todoAdapter.submitList(todoData)
                })

            }


        }
    }

    // MaterialButtonToggleGroupの拡張関数
    private fun MaterialButtonToggleGroup.checkOnlyButton(checkedId: Int) {
        val checkedButtonIds = checkedButtonIds
        for (buttonId in checkedButtonIds) {
            if (buttonId != checkedId) {
                uncheck(buttonId)
            }
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




