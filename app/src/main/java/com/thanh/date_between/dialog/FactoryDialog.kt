package com.thanh.date_between.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.thanh.date_between.R
import com.thanh.date_between.common.base.BaseDialog
import com.thanh.date_between.extension.onClick
import kotlinx.android.synthetic.main.fragment_factory.*

class FactoryDialog: BaseDialog(){
    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_factory, null)
    }

    companion object{
        fun getInstance(): FactoryDialog{
            return FactoryDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvNegative?.onClick {
            onNegativeClick?.invoke(this)
        }
        tvPositive?.onClick {
            onPositiveClick?.invoke(this)
        }

        contentPositive?.apply {
            tvPositive?.visibility = View.VISIBLE
            tvPositive?.text = this
        }

        contentNegative?.apply {
            tvNegative?.visibility = View.VISIBLE
            tvNegative?.text = this
        }

        title.apply {
            tv_title?.text = this
            tv_title?.visibility = View.VISIBLE
        }

        content.apply {
            tv_content?.text = this
            tv_content?.visibility = View.VISIBLE
        }

        checkbox?.setOnCheckedChangeListener { _, b ->
            onCheckBoxChangedEvent.invoke(b)
        }
    }

    private lateinit var title: String
    private lateinit var content: String
    private lateinit var contentPositive: String
    private lateinit var contentNegative: String
    private lateinit var onPositiveClick: (f: FactoryDialog) -> Unit
    private lateinit var onNegativeClick: (f: FactoryDialog) -> Unit
    private lateinit var onCheckBoxChangedEvent: (b: Boolean) -> Unit

    fun setTitle(title: String): FactoryDialog{
        this.title = title
        return this
    }

    fun setContent(content: String): FactoryDialog{
        this.content = content
        return this
    }

    fun setPositiveClick(content: String, f: (FactoryDialog) -> Unit): FactoryDialog{
        this.onPositiveClick = f
        this.contentPositive = content
        return this
    }

    fun setNegativeClick(content: String, f: (FactoryDialog) -> Unit): FactoryDialog{
        this.onNegativeClick = f
        this.contentNegative = content
        return this
    }

    fun setOnCheckBoxListener(f: (Boolean) -> Unit): FactoryDialog{
        this.onCheckBoxChangedEvent = f
        return this
    }

    fun show(fragmentManager: FragmentManager){
        show(fragmentManager, this.tag)
    }

}