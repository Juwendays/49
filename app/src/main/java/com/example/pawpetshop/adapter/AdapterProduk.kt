package com.example.pawpetshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpetshop.R
import com.example.pawpetshop.activity.DetailProdukActivity
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.Produk
import com.example.pawpetshop.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterProduk(var activity: Context, var data:ArrayList<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].name

        //Membuat harga rupiah dan dengan format titik yang benar
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)

//      holder.imgProduk.setImageResource(data[position].gambar)
        val image = Config.productUrl + data[position].image
        //libary picasso
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val activiti = Intent(activity, DetailProdukActivity::class.java)

            //membawa informasi dari satu activity ke acticity lain dengan lebih efektif
            val str = Gson().toJson(data[position], Produk::class.java)
            activiti.putExtra("extra", str )

            //membawa informasi dari satu activity ke acticity lain
//            activiti.putExtra("nama", data[position].name )
//            activiti.putExtra("harga", data[position].harga )

            activity.startActivity(activiti)
        }
    }

}