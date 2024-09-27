package com.example.todoapp

import TodoViewModel
import android.content.Context
import android.content.SharedPreferences
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
                group.checkOnlyButton(checkedId)
            } else if (group.checkedButtonIds.isEmpty()) {
                return@addOnButtonCheckedListener
            }

            val allListData = db.todoDataDao().getAll()
            val filteredList = when (checkedId) {
                R.id.gone_button -> allListData.filter { it.gone == true }
                R.id.never_gone_button -> allListData.filter { it.gone == false }
                R.id.favorite_button -> allListData.filter { it.favorite == true }
                else -> allListData
            }

            adapter.submitList(filteredList)
        }

        adapter.checkGoneEvent.observe(this) {id ->
            val selectedData = db.todoDataDao().getDataById(id)

            // nullチェックを行い、データが存在する場合のみ更新を行う
            selectedData?.let { data ->
                // goneの値を反転させる
                data.gone = !data.gone

                // データベースを更新
                db.todoDataDao().updata(data)

                // RecyclerViewの表示を更新
                adapter.submitList(db.todoDataDao().getAll())
            }
        }

        adapter.checkFavoriteEvent.observe(this) {id ->
            val selectedData = db.todoDataDao().getDataById(id)

            // nullチェックを行い、データが存在する場合のみ更新を行う
            selectedData?.let { data ->
                // goneの値を反転させる
                data.favorite = !data.favorite

                // データベースを更新
                db.todoDataDao().updata(data)

                // RecyclerViewの表示を更新
                adapter.submitList(db.todoDataDao().getAll())
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

        binding.showAllButton.setOnClickListener{
            // 一覧ボタンを押すと全データ表示
            adapter.submitList(db.todoDataDao().getAll())
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
}
