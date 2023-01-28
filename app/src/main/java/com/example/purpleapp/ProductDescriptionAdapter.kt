package com.example.purpleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class ProductDescriptionAdapter(val data : List<ProductImageData>):Adapter<ProductDescriptionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.prod_img_item_view,parent,false)
        return ProductDescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductDescriptionViewHolder, position: Int) {
       val item = data[position]
//       if (item.producImage == null || item.producImage == "" || item.producImage == "null")
//       {
//           holder.img.setImageResource(R.drawable.not_available_picture)
//       }
//        else {
//           Picasso.get().load(item.producImage).into(holder.img)
//       }


        holder.img.setOnClickListener {
            Toast.makeText(it.context, "Pinch image to zoom-in or zoom-out", Toast.LENGTH_SHORT).show();
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(it.context)
            val mView: View =LayoutInflater.from(it.context).inflate(R.layout.dialog_custom_layout,null)
//            inflate(R.layout.dialog_custom_layout, null)
            val photoView: PhotoView = mView.findViewById(R.id.imageView)
            Picasso.get().load(item.producImage).into(photoView)
            mBuilder.setView(mView)
            val mDialog: AlertDialog = mBuilder.create()
            mDialog.show()
        }
    }

    override fun getItemCount() = data.size


}

class ProductDescriptionViewHolder(itemView: View):ViewHolder(itemView)
{
    val img:ImageView = itemView.findViewById(R.id.prodDescriptionImg)
}

