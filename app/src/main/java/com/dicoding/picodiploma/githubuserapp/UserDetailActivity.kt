package com.dicoding.picodiploma.githubuserapp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var nameUser: String
    private lateinit var content: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val image = user.avatar
        nameUser = user.name.toString()
        content = "${user.username.toString()}\n" +
                "${user.company.toString()}\n" +
                "${user.location.toString()}\n" +
                "${user.repository.toString()}\n" +
                "${user.followers.toString()}\n" +
                user.following.toString()
        Glide.with(this).load(image).into(iv_avatar_received)
        tv_name_received.text = nameUser
        tv_object_received.text = content
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right)
    }
}
