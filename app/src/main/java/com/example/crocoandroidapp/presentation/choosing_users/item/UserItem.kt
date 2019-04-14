package com.example.crocoandroidapp.presentation.choosing_users.item

import android.view.View
import com.example.crocoandroidapp.R
import com.example.domain.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_choosing_users_user_item.view.fragment_choosing_users_user_item_text_view_name as textViewName
import kotlinx.android.synthetic.main.fragment_choosing_users_user_item.view.fragment_choosing_users_user_item_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_choosing_users_user_item.view.fragment_choosing_users_user_image_view_selected as imageViewSelected

data class UserItem(
    private val user: User,
    private val onClick: (User) -> Unit
) : Item() {

    override fun getLayout(): Int = R.layout.fragment_choosing_users_user_item

    override fun getId(): Long = layout.toLong()

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.containerView) {
            textViewName.text = user.firstName

            content.setOnClickListener {
                onClick.invoke(user)

                if (imageViewSelected.visibility == View.VISIBLE) {
                    imageViewSelected.visibility = View.INVISIBLE
                } else {
                    imageViewSelected.visibility = View.VISIBLE
                }
            }
        }
    }
}
