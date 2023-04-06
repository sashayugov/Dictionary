package com.example.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.dictionary.App
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.WordData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), DictionaryContract.View<WordData> {

    private lateinit var binding: ActivityMainBinding

    private val presenter = App.instance.mainPresenter
    private lateinit var word: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)
        initSearchButton()
        initKeyboardSearchButton()
        initBackPressedCallback()
    }

    override fun renderData(wordData: WordData?) {
        if (wordData != null) {
            when (wordData) {
                is WordData.Success -> {
                    openWordListFragment()
                }
                is WordData.Error -> {
                    showErrorMessage()
                }
                is WordData.Loading -> {
                    openLoadingFragment()
                }
            }
        }
    }

    private fun openWordListFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out
            )
            replace(R.id.container, WordListFragment())
        }
        moveSearchField()
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.container, "Error, something wrong", Snackbar.LENGTH_LONG).show()
    }

    private fun openLoadingFragment() {
        supportFragmentManager.commit {
            replace(R.id.container, LoadingFragment())
        }
        moveSearchField()
    }

    private fun initSearchButton() {
        binding.searchWordButton.setOnClickListener {
            word = binding.inputWordEditText.text.toString()
            presenter.onLoadDataByWord(word)
            closeKeyboard()
        }
    }

    private fun initKeyboardSearchButton() {
        binding.inputWordEditText.setOnEditorActionListener { _, _, _ ->
            word = binding.inputWordEditText.text.toString()
            presenter.onLoadDataByWord(word)
            closeKeyboard()
            true
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this) {
            supportFragmentManager.commit {
                supportFragmentManager.findFragmentById(R.id.container)?.let { remove(it) }
            }
            TransitionManager.beginDelayedTransition(binding.mainActivityContainer, ChangeBounds())
            when (binding.container.visibility) {
                GONE -> finish()
                VISIBLE -> binding.container.visibility = GONE
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
        if (binding.container.visibility == GONE) binding.container.visibility = VISIBLE
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}