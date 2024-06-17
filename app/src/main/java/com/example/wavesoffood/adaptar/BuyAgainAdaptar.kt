package com.example.wavesoffood.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.BuyAgainItemBinding
import com.example.wavesoffood.databinding.MenuItemBinding

class BuyAgainAdaptar(private val buyagainFoodName:ArrayList<String>, private val buyAgainFoodPrice:ArrayList<String>,
                      private val buyAgainFoodImage: ArrayList<Int>) :
    RecyclerView.Adapter<BuyAgainAdaptar.BuyAgainViewholder>() {


    override fun onBindViewHolder(holder: BuyAgainViewholder, position: Int) {
      holder.bind(buyagainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewholder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewholder(binding)
    }

    override fun getItemCount(): Int = buyagainFoodName.size

    class BuyAgainViewholder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: Int) {
            binding.buyAgainfoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
            binding.buyAgainFoodimage.setImageResource(foodImage)
        }

    }



}