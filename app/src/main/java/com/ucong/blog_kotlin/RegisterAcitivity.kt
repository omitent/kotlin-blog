package com.ucong.blog_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ucong.blog_kotlin.apiHelper.APIServices
import com.ucong.blog_kotlin.apiHelper.ApiUtils
import com.ucong.blog_kotlin.model.SharedPreference
import com.ucong.blog_kotlin.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class RegisterAcitivity : AppCompatActivity() {
    private lateinit var btnLogin:TextView
    private lateinit var btnRegister:Button

    private lateinit var newname: EditText
    private lateinit var newemail: EditText
    private lateinit var newpassword: EditText
    private lateinit var newcpassword: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_acitivity)

        btnLogin = findViewById(R.id.textViewLogin)
        btnRegister = findViewById(R.id.buttonRegister)



        btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        newname = findViewById(R.id.editNewName)
        newemail = findViewById(R.id.editNewEmail)
        newpassword = findViewById(R.id.editNewPassword)
        newcpassword = findViewById(R.id.editNewCPassword)


        btnRegister.setOnClickListener {

            val name = newname.text.toString().trim()
            val email = newemail.text.toString().trim()
            val password = newpassword.text.toString().trim()
            val cpassword = newcpassword.text.toString().trim()

            val MySharedPreference:SharedPreference = SharedPreference(this)
            //Variable declaration
            val mAPIService: APIServices = ApiUtils.apiService

            mAPIService.registrationPost(name,email,password,cpassword).enqueue(object:Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

//                    Log.i("", "post submitted to API." + response.body()!!)

                    if (response.isSuccessful) {

                        Log.i("", "post registration to API" + response.body()!!.toString())
                        Log.i("", "post status to API" + response.body()!!.status)
                        Log.i("", "post msg to API" + response.body()!!.messages)

                        Toast.makeText(applicationContext, "Register New Account Successed", Toast.LENGTH_SHORT).show()

                        MySharedPreference.save("email",email);
                        MySharedPreference.save("password",password);

                        startActivity(Intent(this@RegisterAcitivity, MainActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(applicationContext, "Email Has Been Registerd", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }



    }
}
