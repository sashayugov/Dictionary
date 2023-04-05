package com.example.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.dictionary.App
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.domain.DictionaryContract
import com.example.dictionary.domain.WordData
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), DictionaryContract.View<WordData> {

    private lateinit var binding: ActivityMainBinding
    private val presenter = App.instance.mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attach(this)

        binding.searchWordButton.setOnClickListener {
            val word: String = binding.inputWordEditText.text.toString()
            presenter.onLoadDataByWord(word)
            closeKeyboard()
        }

        onBackPressedDispatcher.addCallback(this) {
            supportFragmentManager.popBackStack()
            TransitionManager.beginDelayedTransition(binding.mainActivityContainer, ChangeBounds())
            when (binding.container.visibility) {
                GONE -> finish()
                VISIBLE -> binding.container.visibility = GONE
            }
        }
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
        moveSearchField()
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out
            )
            replace(R.id.container, WordListFragment())
        }
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.container, "Error, something wrong", Snackbar.LENGTH_LONG).show()
    }

    private fun openLoadingFragment() {
        moveSearchField()
        supportFragmentManager.commit {
            replace(R.id.container, LoadingFragment())
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