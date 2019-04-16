package com.example.crocoandroidapp.presentation.tasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.tasks.adapter.item.MonthItem

class MonthViewPagerAdapter(
    private val context: Context,
    private var months: List<MonthItem> = listOf()
) : PagerAdapter() {

    override fun getCount() = months.size

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val task = months[position]

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.fragment_tasks_view_pager_task_item, container, false)

        task.bind(layout)
        container.addView(layout)

        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    fun addMonths(months: List<MonthItem>) {
        this.months = months
        notifyDataSetChanged()
    }
}
