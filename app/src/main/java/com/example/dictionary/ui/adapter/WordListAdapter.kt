package com.example.dictionary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.WordItemBinding
import com.example.dictionary.domain.entity.WordDataModel

class WordListAdapter(
    private val wordItems: List<WordDataModel>,
    private val onWordClickListener: ((item: WordDataModel) -> Unit)
) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onWordClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wordItems[position])
    }

    override fun getItemCount(): Int = wordItems.size

    inner class ViewHolder(
        itemBinding: WordItemBinding,
        private val onWordClickListener: (item: WordDataModel) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val word = itemBinding.wordItemTv
        fun bind(wordDataModel: WordDataModel) {
            word.text = wordDataModel.text
            itemView.setOnClickListener { onWordClickListener(wordDataModel) }
        }
    }


}