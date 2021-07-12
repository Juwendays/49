package com.example.pawpetshop.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.pawpetshop.R
import com.example.pawpetshop.adapter.AdapterProduk
import com.example.pawpetshop.adapter.AdapterSlider
import com.example.pawpetshop.app.ApiConfig
import com.example.pawpetshop.model.Produk
import com.example.pawpetshop.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(){

    lateinit var vpSlider : ViewPager
    lateinit var rvProduk : RecyclerView
    lateinit var rvProdukTerlaris : RecyclerView
    lateinit var rvFood :RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home,container, false)
        init(view)
        getProduk()

        return view
    }

    //menampilkan data product
    fun displayProduk(){
        Log.d("cekini", "size:" + listProduk.size)
        // menampilkan produk horizontal
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.petshop)
        arrSlider.add(R.drawable.logopetshop)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager
//
        rvProdukTerlaris.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProdukTerlaris.layoutManager = layoutManager2

        rvFood.adapter = AdapterProduk(requireActivity(), listProduk)
        rvFood.layoutManager = layoutManager3

    }

    //Membuat array baru
    private var listProduk: ArrayList<Produk> = ArrayList()

    //Menampilkan alert
    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object :
                Callback<ResponModel> {

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                if (respon.success == 1) {
                    listProduk = respon.produks
                    displayProduk()
                }
            }
        })
    }

    //Pemanggilan agar tidak tidak oncreate semua
    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlaris = view.findViewById(R.id.rv_produkTerlasir)
        rvFood = view.findViewById(R.id.rv_food)
    }


//    val arrProduk: ArrayList<Produk>get(){
//            val arr = ArrayList<Produk>()
//            val p1 = Produk()
//            p1.nama = "Whiskas Food"
//            p1.harga = "Rp.60.000"
//            p1.gambar = R.drawable.food
//
//            val p2 = Produk()
//            p2.nama = "Royal Canin"
//            p2.harga = "Rp.50.000"
//            p2.gambar = R.drawable.food2
//
//            val p3 = Produk()
//            p3.nama = "Royal Canin Extra"
//            p3.harga = "Rp.50.000"
//            p3.gambar = R.drawable.food
//
//            val p4 = Produk()
//            p4.nama = "Whiskas food"
//            p4.harga = "Rp.40.000"
//            p4.gambar = R.drawable.food2
//
//            arr.add(p1)
//            arr.add(p2)
//            arr.add(p3)
//            arr.add(p4)
//
//            return arr
//        }
//
//    val arrProdukTerlaris: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "Whiskas Food"
//        p1.harga = "Rp.60.000"
//        p1.gambar = R.drawable.food
//
//        val p2 = Produk()
//        p2.nama = "Royal Canin"
//        p2.harga = "Rp.50.000"
//        p2.gambar = R.drawable.food2
//
//        val p3 = Produk()
//        p3.nama = "Royal Canin Extra"
//        p3.harga = "Rp.50.000"
//        p3.gambar = R.drawable.food
//
//        val p4 = Produk()
//        p4.nama = "Whiskas food"
//        p4.harga = "Rp.40.000"
//        p4.gambar = R.drawable.food2
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//
//        return arr
//    }
//
//    val arrFood: ArrayList<Produk>get(){
//        val arr = ArrayList<Produk>()
//        val p1 = Produk()
//        p1.nama = "Whiskas Food"
//        p1.harga = "Rp.60.000"
//        p1.gambar = R.drawable.food
//
//        val p2 = Produk()
//        p2.nama = "Royal Canin"
//        p2.harga = "Rp.50.000"
//        p2.gambar = R.drawable.food2
//
//        val p3 = Produk()
//        p3.nama = "Royal Canin Extra"
//        p3.harga = "Rp.50.000"
//        p3.gambar = R.drawable.food
//
//        val p4 = Produk()
//        p4.nama = "Whiskas food"
//        p4.harga = "Rp.40.000"
//        p4.gambar = R.drawable.food2
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//
//        return arr
//    }

}