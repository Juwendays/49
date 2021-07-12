package com.example.pawpetshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpetshop.R
import com.example.pawpetshop.adapter.AdapterAlamat
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.Alamat
import com.example.pawpetshop.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar.*

class ListAlamatActivity: AppCompatActivity() {

    lateinit var myDb : MyDatabase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_list_alamat)
            Helper().setToolbar(this, toolbar,"Pilih Alamat")
            myDb = MyDatabase.getInstance(this)!!

            mainButton()
        }

    private fun displayAlamat() {
        val arrayList = myDb.daoAlamat().getAll() as ArrayList

        if (arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_alamat.adapter = AdapterAlamat(arrayList, object : AdapterAlamat.Listeners {
            override fun onClicked(data: Alamat) {
                if (myDb.daoAlamat().getByStatus(true) != null){
                    val alamatActive = myDb.daoAlamat().getByStatus(true)!!
                    alamatActive.isSelected = false
                    updateActive(alamatActive, data)
                }
            }
        })
        rv_alamat.layoutManager = layoutManager
    }

    private fun updateActive(dataActive: Alamat, dataNonActive: Alamat) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().update(dataActive) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateNonActive(dataNonActive)
                })
    }

    private fun updateNonActive(data: Alamat) {
        data.isSelected = true
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().update(data) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onBackPressed()
                })
    }

    override fun onResume() {
        displayAlamat()
        super.onResume()
    }


    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener{
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}