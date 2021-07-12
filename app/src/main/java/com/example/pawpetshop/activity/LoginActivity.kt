package com.example.pawpetshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pawpetshop.MainActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.app.ApiConfig
import com.example.pawpetshop.helper.SharedPref
import com.example.pawpetshop.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        btn_login.setOnClickListener {
            login()
        }
    }

    fun login() {
        if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email tidak boleh kosong"
            edt_email.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password tidak boleh kosong"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(),edt_password.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon = response.body()!!
                if (respon.success == 1) {
                    //jika berhasil login
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
//                    s.setString(s.nama, respon.user.name)
//                    s.setString(s.phone, respon.user.phone)
//                    s.setString(s.email, respon.user.email)

                    //Kembali ke menu home setelah login
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    // Mendestroy agar setelah berhasil login tidak kembali ke halaman login
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity, "Selamat datang " + respon.user.name, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error:" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
