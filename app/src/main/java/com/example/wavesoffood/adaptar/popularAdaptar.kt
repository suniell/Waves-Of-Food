package com.example.wavesoffood.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.PopularItemBinding

class popularAdaptar(private val items:List<String>, private val image : List<Int>, private val price :List<String>, private val requireContext: Context) :
    RecyclerView.Adapter<popularAdaptar.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }



    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
       val item = items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item,images,price)


        holder.itemView.setOnClickListener {
            // seton click listener to open food details
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            requireContext.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder (private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(item: String, images: Int, price: String) {

            binding.FoodNamePopular.text = item
            binding.PricePopular.text = price
            binding.ImageViewPopular.setImageResource(images)
        }


    }

    }





