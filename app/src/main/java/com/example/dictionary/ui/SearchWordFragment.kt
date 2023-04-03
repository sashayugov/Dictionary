package com.example.dictionary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionary.App
import com.example.dictionary.databinding.FragmentSearchWordBinding
import com.example.dictionary.ui.adapter.WordListAdapter


class SearchWordFragment : Fragment(){

    private var _binding: FragmentSearchWordBinding? = null
    private val binding get() = _binding!!

    private val presenter = App.instance.mainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchWordButton.setOnClickListener {
            val word: String = binding.inputWordEditText.text.toString()
            presenter.onLoadDataByWord(word)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}