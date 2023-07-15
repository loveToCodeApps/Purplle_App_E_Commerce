package com.example.purpleapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.purpleapp.databinding.FragmentProductDescriptionBinding

class ColorAdapter(var color: List<colorData>,var prod_id: String,var binding: FragmentProductDescriptionBinding) : Adapter<ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.color_item_view, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {

        val item = color[position]

        // hide selected color tick if there is only one color
        if(color.size==1)
        {
            holder.img.visibility = View.GONE

        }


         if (item.color != "null" && item.color.length != 0)
         {
             try {
                 holder.smallcolor.setBackgroundColor(Color.parseColor(item.color))
             } catch (e: IllegalArgumentException) {
                 //holder.smallcolor.setBackgroundColor(Color.parseColor("#fcba03"))
                 binding.textView145.visibility = View.GONE
                 binding.colorList.visibility = View.GONE
             }
         }

        if (position==0)
        {
            holder.img.visibility = View.VISIBLE
        }


        holder.smallcolor.setOnClickListener {
            it.findNavController().navigate(ProductDescriptionFragmentDirections.actionProductDescriptionFragmentSelf(item.prod_id))
        }

    }

    override fun getItemCount(): Int = color.size
}


class ColorViewHolder(itemView: View) : ViewHolder(itemView) {
    val smallcolor: View = itemView.findViewById(R.id.view11)
    val img: ImageView = itemView.findViewById(R.id.imageView11)

}


