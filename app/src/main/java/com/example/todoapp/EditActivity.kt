package com.example.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // データベースの初期化
        db = AppDatabase.getInstance(this.applicationContext)

        // idを受け取る
        val id: Int = intent.getIntExtra("ID",0)

        // idでデータベースからデータを取得
        val tappedTodo = db.todoDataDao().getDataById(id)

        // タイトルをテキストとして表示
        binding.titleEditText.setText(tappedTodo.title)

        // 変更ボタンがタップされたら
        binding.editButton.setOnClickListener{
            // 新しいタイトルを取得
            val newTitle = binding.titleEditText.text.toString()

            // 新しいタイトルとメモを更新
            tappedTodo.title = newTitle

            // Roomも更新
            db.todoDataDao().updata(tappedTodo)

            // MainActivityへ移動
            finish()
        }

        binding.deleteButton.setOnClickListener{
            // ①ここで消す
            db.todoDataDao().deleteDataById(id)


            // ②Roomも更新
            db.todoDataDao().updata(tappedTodo)

            // ③消してからMainActivityへ移動
            finish()
        }

    }
}