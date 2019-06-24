package com.ucong.blog_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.*
import com.ucong.blog_kotlin.apiHelper.APIServices
import com.ucong.blog_kotlin.apiHelper.ApiUtils
import com.ucong.blog_kotlin.model.SharedPreference
import com.ucong.blog_kotlin.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin:Button
    private lateinit var btnRegister:TextView
    private lateinit var btnShowPassword:CheckBox

    private lateinit var email: EditText
    private lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val MySharedPreference:SharedPreference= SharedPreference(this)

        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.textViewRegister)
        btnShowPassword = findViewById(R.id.showPassword)

        email = findViewById(R.id.et_user_name)
        password = findViewById(R.id.et_password)

        if (MySharedPreference.getValueString("email")!=null) {
            email.hint = MySharedPreference.getValueString("email")!!

        }


        btnShowPassword.setOnClickListener {
            if (btnShowPassword.isChecked) {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btnLogin.setOnClickListener {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()


            //Variable declaration
            val mAPIService: APIServices = ApiUtils.apiService
            mAPIService.registrationPost(email,password).enqueue(object: Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

//                    Log.i("", "post submitted to API." + response.body()!!)

                    if (response.isSuccessful) {

//                        Log.i("", "post registration to API" + response.body()!!.toString())
//                        Log.i("", "post status to API" + response.body()!!.status)
//                        Log.i("", "post msg to API" + response.body()!!.messages)

                        Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()

                        MySharedPreference.save("email",email);

                        startActivity(Intent(this@MainActivity, HomeAcitivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterAcitivity::class.java))
            finish()
        }





    }

}


