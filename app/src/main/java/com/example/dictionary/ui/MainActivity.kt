package com.example.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.App
import com.example.dictionary.DictionaryContract
import com.example.dictionary.app
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.domain.WordData
import com.example.dictionary.ui.adapter.WordListAdapter
import com.example.dictionary.ui.viewmodel.Factory
import com.example.dictionary.ui.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity(), DictionaryContract.View {

    private lateinit var binding: ActivityMainBinding

    private lateinit var word: String

    @Inject
    lateinit var factory: Factory
    private val viewModel by viewModels<MainActivityViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.app.appComponent.inject(this)
        renderData()
        initSearchButton()
        initKeyboardSearchButton()
        initBackPressedCallback()
    }

    override fun renderData() {
        viewModel.liveWordData.observe(this) { wordData ->
            if (wordData != null) {
                when (wordData) {
                    is WordData.Success -> {
                        moveSearchField()
                        binding.wordList.visibility = VISIBLE
                        binding.progressBar.visibility = GONE
                        setRecyclerView(wordData)
                    }
                    is WordData.Error -> {
                        showErrorMessage()
                    }
                    is WordData.Loading -> {
                        moveSearchField()
                        binding.wordList.visibility = GONE
                        binding.progressBar.visibility = VISIBLE
                    }
                }
            }
        }
    }

    private fun setRecyclerView(wordData: WordData.Success) {
        binding.wordList.hasFixedSize()
        binding.wordList.adapter = WordListAdapter(
            wordItems = wordData.listDataModel,
            onWordClickListener = {
                TransitionManager.beginDelayedTransition(binding.wordList)
            }
        )
        binding.wordList.layoutManager = LinearLayoutManager(this)
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.mainActivityContainer, "Error, something wrong", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun initSearchButton() {
        binding.searchWordButton.setOnClickListener {
            word = binding.inputWordEditText.text.toString()
            viewModel.onLoadDataByWord(word)
            closeKeyboard()
        }
    }

    private fun initKeyboardSearchButton() {
        binding.inputWordEditText.setOnEditorActionListener { _, _, _ ->
            word = binding.inputWordEditText.text.toString()
            viewModel.onLoadDataByWord(word)
            closeKeyboard()
            true
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this) {
            TransitionManager.beginDelayedTransition(binding.mainActivityContainer, ChangeBounds())
            when (binding.wordListContainer.visibility) {
                GONE -> finish()
                VISIBLE -> binding.wordListContainer.visibility = GONE
            }
        }
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun moveSearchField() {
        TransitionManager.beginDelayedTransition(binding.mainActivityContainer, ChangeBounds())
        binding.wordList.scheduleLayoutAnimation()
        if (binding.wordListContainer.visibility == GONE) binding.wordListContainer.visibility =
            VISIBLE
    }
}