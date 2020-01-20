package com.neoxia.charlesmarchand.experience

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pager.*
import java.util.*

class PagerActivity : AppCompatActivity() {

    lateinit var pager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        pager = viewPager
        viewPager.adapter=PagerAdapter(this,this,Saves.max())
        current.init(Saves.getWord(0))
        findViewById<TextView>(R.id.score).setText(Saves.getPointText())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                current.init(Saves.getWord(position))
                findViewById<TextView>(R.id.score).setText(Saves.getPointText())
            }

        })


        object : Thread(){
            override fun run() {
                super.run()
                val end = Calendar.getInstance()
                end.add(Calendar.MINUTE,15)
                while (Calendar.getInstance().before(end)){
                    sleep(15000)
                }
                isOver=true
            }
        }.start()
    }

    var isOver = false

    fun goNext(){
        if(pager.currentItem>=Saves.max()-1 || isOver){
            startActivity(Intent(this,RecapActivity::class.java))
            finish()
            return
        }
        current.reset(Saves.getWord(pager.currentItem+1))
        pager.setCurrentItem(pager.currentItem+1,true)
    }

    override fun onBackPressed() {

    }
}
