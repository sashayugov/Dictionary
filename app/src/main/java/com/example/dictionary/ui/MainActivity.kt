package com.example.dictionary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.dictionary.App
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.domain.DictionaryContract

class MainActivity : AppCompatActivity(), DictionaryContract.View {

    private lateinit var binding: ActivityMainBinding
    private val presenter = App.instance.mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)
    }

    override fun openSearchFragment() {
        supportFragmentManager.commit {
            add(R.id.container, SearchWordFragment())
        }
    }

    override fun openWordListFragment() {
        if (supportFragmentManager.findFragmentById(R.id.container) is WordListFragment) {
            supportFragmentManager.popBackStack()
        }
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container, WordListFragment())
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}