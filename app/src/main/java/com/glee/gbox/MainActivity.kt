package com.glee.gbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glee.gbox.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.apply {
            val transaction = beginTransaction()
            val mainFragment = findFragmentByTag(MainFragment.TAG)
            if (mainFragment == null || !mainFragment.isAdded)
                transaction.add(
                    R.id.content,
                    MainFragment.newInstance(),
                    MainFragment.TAG
                )
            else transaction.show(mainFragment)
            transaction.commitNow()
        }
    }
}
