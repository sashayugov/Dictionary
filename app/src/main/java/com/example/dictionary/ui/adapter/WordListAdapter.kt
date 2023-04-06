package com.example.dictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dictionary.databinding.WordItemBinding
import com.example.dictionary.domain.entity.WordDataModel

class WordListAdapter(
    private val wordItems: List<WordDataModel>,
    private val onWordClickListener: ((item: WordDataModel) -> Unit)
) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    private var selectedItem: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onWordClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wordItems[position])
        checkHolder(holder, position)
        selectedItem = null
    }

    override fun getItemCount(): Int = wordItems.size

    private fun checkHolder(holder: ViewHolder, position: Int) {
        if (selectedItem == position) {
            holder.changeItemView(wordItems[position])
        } else {
            holder.notChangeItemView()
        }
    }

    inner class ViewHolder(
        itemBinding: WordItemBinding,
        private val onWordClickListener: ((item: WordDataModel) -> Unit)
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val word = itemBinding.wordItemTv
        private val wordTranslate = itemBinding.wordTranslateItemTv
        private val wordNote = itemBinding.wordNote
        private val imageMeaning = itemBinding.imageMeaning

        fun bind(wordDataModel: WordDataModel) {
            word.text = wordDataModel.text
            wordTranslate.text = wordDataModel.meanings[0].translation.text
            wordNote.text = wordDataModel.meanings[0].translation.note
            imageMeaning.load("https:" + wordDataModel.meanings[0].imageUrl) {
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }

            itemView.setOnClickListener {
                selectedItem = if (selectedItem == null) adapterPosition
                else null
                changeItemView(wordDataModel)
            }
        }

        fun changeItemView(wordDataModel: WordDataModel) {
            if (imageMeaning.visibility == GONE) imageMeaning.visibility = VISIBLE
            else imageMeaning.visibility = GONE

            if (wordNote.visibility == GONE) wordNote.visibility = VISIBLE
            else wordNote.visibility = GONE
            onWordClickListener(wordDataModel)
        }

        fun notChangeItemView() {
            imageMeaning.visibility = GONE
            wordNote.visibility = GONE
        }
    }
}