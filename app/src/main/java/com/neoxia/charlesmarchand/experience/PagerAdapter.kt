package com.neoxia.charlesmarchand.experience

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.DragShadowBuilder
import android.content.ClipData
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.DragEvent
import android.R.attr.shape
import android.graphics.drawable.Drawable
import android.view.View.OnDragListener
import android.widget.*


class PagerAdapter(private val mContext: Context, val activity : PagerActivity,val MaxCount : Int) : PagerAdapter() {

    lateinit var username : String

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.fragment_word, collection, false) as ViewGroup

        val word = Saves.getWord(position)

        val letter1 = layout.findViewById<TextView>(R.id.letter1)
        val letter2 = layout.findViewById<TextView>(R.id.letter2)
        val letter3 = layout.findViewById<TextView>(R.id.letter3)
        val letter4 = layout.findViewById<TextView>(R.id.letter4)
        val letter5 = layout.findViewById<TextView>(R.id.letter5)
        val letter6 = layout.findViewById<TextView>(R.id.letter6)

        val placeletter1 = layout.findViewById<TextView>(R.id.letterPosition1)
        val placeletter2 = layout.findViewById<TextView>(R.id.letterPosition2)
        val placeletter3 = layout.findViewById<TextView>(R.id.letterPosition3)
        val placeletter4 = layout.findViewById<TextView>(R.id.letterPosition4)
        val placeletter5 = layout.findViewById<TextView>(R.id.letterPosition5)
        val placeletter6 = layout.findViewById<TextView>(R.id.letterPosition6)

        letter1.setText(word.initialSeq[0].toString())
        letter2.setText(word.initialSeq[1].toString())
        letter3.setText(word.initialSeq[2].toString())
        letter4.setText(word.initialSeq[3].toString())
        letter5.setText(word.initialSeq[4].toString())
        letter6.setText(word.initialSeq[5].toString())

        /*
        letter1.setOnTouchListener(MyTouchListener())
        letter2.setOnTouchListener(MyTouchListener())
        letter3.setOnTouchListener(MyTouchListener())
        letter4.setOnTouchListener(MyTouchListener())
        letter5.setOnTouchListener(MyTouchListener())
        letter6.setOnTouchListener(MyTouchListener())
        */
        placeletter1.setOnDragListener(MyDragListener(activity,layout))
        placeletter2.setOnDragListener(MyDragListener(activity,layout))
        placeletter3.setOnDragListener(MyDragListener(activity,layout))
        placeletter4.setOnDragListener(MyDragListener(activity,layout))
        placeletter5.setOnDragListener(MyDragListener(activity,layout))
        placeletter6.setOnDragListener(MyDragListener(activity,layout))


        letter1.setOnClickListener {
            if(!letter1.text.toString().equals("",true))
            current.put(0,layout)
        }

        letter2.setOnClickListener {
            if(!letter2.text.toString().equals("",true))
                current.put(1,layout)
        }

        letter3.setOnClickListener {
            if(!letter3.text.toString().equals("",true))
                current.put(2,layout)
        }

        letter4.setOnClickListener {
            if(!letter4.text.toString().equals("",true))
                current.put(3,layout)
        }


        letter5.setOnClickListener {
            if(!letter5.text.toString().equals("",true))
                current.put(4,layout)
        }


        letter6.setOnClickListener {
            if(!letter6.text.toString().equals("",true))
                current.put(5,layout)
        }

        placeletter1.setOnClickListener {
            if(!placeletter1.text.toString().equals("")){
                current.pop(0,layout)
            }
        }

        placeletter2.setOnClickListener {
            if(!placeletter2.text.toString().equals("")){
                current.pop(1,layout)
            }
        }

        placeletter3.setOnClickListener {
            if(!placeletter3.text.toString().equals("")){
                current.pop(2,layout)
            }
        }

        placeletter4.setOnClickListener {
            if(!placeletter4.text.toString().equals("")){
                current.pop(3,layout)
            }
        }

        placeletter5.setOnClickListener {
            if(!placeletter5.text.toString().equals("")){
                current.pop(4,layout)
            }
        }

        placeletter6.setOnClickListener {
            if(!placeletter1.text.toString().equals("")){
                current.pop(5,layout)
            }
        }

        layout.findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            if(current.isCurrentOk()){
                Saves.point+=3
                Saves.declareDone(true)
                activity.goNext()
            }
            else{
                Saves.point+=-1
                Saves.declareDone(false)
                activity.goNext()
            }
        }

        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return MaxCount
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}

private class MyDragListener(activity: PagerActivity,val root:View) : OnDragListener {
    var enterShape = activity.getResources().getDrawable(
            R.drawable.letter_back)
    var normalShape = activity.getResources().getDrawable(R.drawable.letter_back)

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_ENTERED -> v.setBackgroundDrawable(enterShape)
            DragEvent.ACTION_DRAG_EXITED -> v.setBackgroundDrawable(normalShape)
            DragEvent.ACTION_DROP -> {
                val view = event.localState as TextView
                if(view.text.toString().equals("") || view.text.toString().equals(" ")) return true
                var pos = -1
                when(view.id){
                    R.id.letter1 -> pos = 0
                    R.id.letter2 -> pos = 1
                    R.id.letter3 -> pos = 2
                    R.id.letter4 -> pos = 3
                    R.id.letter5 -> pos = 4
                    R.id.letter6 -> pos = 5
                }
                val container = v as TextView
                container.setText(view.text)
                when(container.id){
                    R.id.letterPosition1 -> current.set(0,pos)
                    R.id.letterPosition2 -> current.set(1,pos)
                    R.id.letterPosition3 -> current.set(2,pos)
                    R.id.letterPosition4 -> current.set(3,pos)
                    R.id.letterPosition5 -> current.set(4,pos)
                    R.id.letterPosition6 -> current.set(5,pos)
                }

                current.updateView(root)
            }
            DragEvent.ACTION_DRAG_ENDED -> v.setBackgroundDrawable(normalShape)
            else -> {
            }
        }// do nothing
        return true
    }
}

private class MyTouchListener : OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(
                    view)
            view.startDrag(data, shadowBuilder, view, 0)
            //view.visibility = View.INVISIBLE
            return true
        } else {
            return false
        }
    }
}

