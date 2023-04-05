package com.example.dictionary.ui

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.App
import com.example.dictionary.databinding.FragmentWordListBinding
import com.example.dictionary.domain.WordData
import com.example.dictionary.ui.adapter.WordListAdapter

class WordListFragment : Fragment() {

    private var _binding: FragmentWordListBinding? = null
    private val binding get() = _binding!!

    private val presenter = App.instance.mainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.wordList.hasFixedSize()
        binding.wordList.adapter = WordListAdapter(
            wordItems = (presenter.wordData as WordData.Success).listDataModel,
            onWordClickListener = {
                TransitionManager.beginDelayedTransition(binding.wordList)
            }
        )
        binding.wordList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}