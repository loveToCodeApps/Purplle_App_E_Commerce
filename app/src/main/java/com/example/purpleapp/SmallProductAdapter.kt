package com.example.purpleapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class SmallProductAdapter(val data : List<SmallProductData>):Adapter<SmallProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.small_product_layout,parent,false)
        return SmallProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmallProductViewHolder, position: Int) {
       val item = data[position]
//        holder.img.setImageResource(item.smallImg)
        Picasso.get().load(item.smallImg).into(holder.img)

       holder.img.setOnClickListener {
           Toast.makeText(it.context, "Pinch image to zoom-in or zoom-out", Toast.LENGTH_SHORT).show();
           val mBuilder: AlertDialog.Builder = AlertDialog.Builder(it.context)
           val mView: View =LayoutInflater.from(it.context).inflate(R.layout.dialog_custom_layout,null)
//            inflate(R.layout.dialog_custom_layout, null)
           val photoView: PhotoView = mView.findViewById(R.id.imageView)
         //  Picasso.get().load(item.smallImg).into(photoView)


           val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
           Glide.with(photoView).load(item.smallImg).thumbnail(0.05f)
               .apply(requestOptions).into(photoView)

           Glide.get(photoView.context).clearMemory()

           Thread(Runnable {
               // This method must be called on a background thread.
               Glide.get(photoView.context).clearDiskCache()
           }).start()




           mBuilder.setView(mView)
           val mDialog: AlertDialog = mBuilder.create()
           mDialog.show()

       }


    }

    override fun getItemCount() = data.size


}

class SmallProductViewHolder(itemView: View):ViewHolder(itemView)
{

    val img:ImageView = itemView.findViewById(R.id.smallProdImg)

}