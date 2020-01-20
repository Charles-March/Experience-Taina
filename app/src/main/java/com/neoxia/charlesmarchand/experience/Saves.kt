package com.neoxia.charlesmarchand.experience

import android.util.Log
import android.view.View
import android.widget.TextView
import java.util.*

object Saves {
    var numero : String = ""
    private var responseList = mutableListOf<Response>()
    private var wordList = mutableListOf<word>()
    var point : Int = 0

    fun getPointText() : String{
        return "$point Point${if(point>0)'s' else ' '}"
    }

    fun max():Int{
        return wordList.size
    }

    fun declareDone(boolean: Boolean){
        var s = ""
        for(i in 0 until 6) {
            try {
                s += current.word.initialSeq[current.currentCircleLetterIn[i]]
            }
            catch (e:Exception){
                s+=' '
            }
        }
        responseList.add(Response(s,current.word.initialSeq,current.getTime(),current.word.difficulty,if(boolean)1 else 0))
    }

    fun init(numero : String){
        this.numero=numero
        this.point=0
        this.responseList= mutableListOf()
        this.wordList= mutableListOf()
        wordList.add(word("GUERRE","RGUERE",1))
        wordList.add(word("LETTRE","EETTLR",1))
        wordList.add(word("BATEAU","AEUABT",1))
        wordList.add(word("CHEVAL","LEAVCH",1))
        wordList.add(word("JARDIN","ANIRDJ",1))
        wordList.add(word("AAAAAA","EMSEZR",3))
        wordList.add(word("SAISON","SONASI",2))
        wordList.add(word("AAAAAA","BRRIET",3))
        wordList.add(word("HOMARD","HRODAM",2))
        wordList.add(word("AAAAAA","NOFREG",3))
        wordList.add(word("COUSIN","OSUCNI",1))
        wordList.add(word("AAAAAA","ENFICE",3))
        wordList.add(word("SOLEIL","LSIEOL",1))
        wordList.add(word("FUMIER","UFMRIE",2))
        wordList.add(word("FAVEUR","UVAREF",2))
        wordList.add(word("SALADE","DESAAL",1))
        wordList.add(word("TUNNEL","NNETUL",1))
        wordList.add(word("RADEAU","AUADER",2))
        wordList.add(word("AAAAAA","NEIACS",3))
        wordList.add(word("DONJON","NNODJO",1))
        wordList.add(word("MAIRIE","AMIIER",2))
        wordList.add(word("AAAAAA","EFNENI",3))
        wordList.add(word("NOUNOU","OUOUNN",1))
        wordList.add(word("CHATON","HONATC",1))
        wordList.add(word("WHISKY","YSHIWK",1))
        wordList.add(word("VOYEUR","OVUREY",2))
        wordList.add(word("AAAAAA","EMMAOR",3))
        wordList.add(word("AAAAAA","CLSEEP",3))
        wordList.add(word("MOMENT","TNEMOM",1))
        wordList.add(word("AAAAAA","EBLGAL",3))
        wordList.add(word("BOURSE","EURBSO",2))
        wordList.add(word("GARAGE","GAGERA",1))
        wordList.add(word("TONTON","ONTNTO",1))
        wordList.add(word("AAAAAA","SONEUX",3))
        wordList.add(word("NIVEAU","VEUAIN",2))
        wordList.add(word("LIVRET","RVIELT",2))
        wordList.add(word("ALCOOL","OCOLAL",1))
        wordList.add(word("PROJET","OETRPJ",1))
        wordList.add(word("AAAAAA","EMPTAS",3))
        wordList.add(word("AAAAAA","LEICUF",3))
        wordList.add(word("MACHIN","NIHAMC",1))
        wordList.add(word("POGNON","PONOGN",1))
        wordList.add(word("AAAAAA","MEOTJN",3))
        wordList.add(word("PIMENT","MINTPE",2))
        wordList.add(word("AAAAAA","ITTMSE",3))
        wordList.add(word("EMPLOI","IPMEOL",2))
        wordList.add(word("PAROLE","PAOERL",1))
        wordList.add(word("PATATE","ATTPAE",1))
        wordList.add(word("AAAAAA","IDPSSE",3))
        wordList.add(word("AAAAAA","AOUQEN",3))
        wordList.add(word("MIROIR","RIORMI",1))
        wordList.add(word("OISEAU","EOASIU",1))
        wordList.add(word("HUMOUR","UURMOH",1))
        wordList.add(word("DISQUE","QDIESU",1))
        wordList.add(word("COGNAC","GANCOC",2))
        wordList.add(word("BOUTON","NBTOUO",1))
        wordList.add(word("FOUDRE","ODERFU",2))
        wordList.add(word("BILLET","LLTBIE",1))
        wordList.add(word("VISAGE","AVIEGS",1))
        wordList.add(word("ABBAYE","BEABYA",2))
    }

    fun getRecap():String{
        var s = "Mots;Difficulte;Temps(en sec);Reponse;Reussite;Points\n"
        for(i in 0 until responseList.size){
            val pts :Int
            if(responseList.get(i).failed==1){
                pts = 3
            }
            else{
                pts=-1
            }
            s+= "${wordList.get(i).initialSeq};${wordList[i].difficulty};${responseList.get(i).TimetoResp.toString().replace(',','.')};${responseList.get(i).word};${responseList.get(i).failed};${pts}\n"
        }
    return s
    }

    fun getWord(position :Int):word{
        return wordList[position]
    }
}

data class word(
        val word : String,
        val initialSeq: String,
        val difficulty : Int
)

data class Response(
        val word : String,
        val initialSeq : String,
        val TimetoResp : Double,
        val wordDifficulty : Int,
        val failed : Int
)

object current{
    lateinit var word : word

    var currentCircle = Array<Char>(6,{' '})
    var currentCircleLetterIn = Array<Int>(6,{-1})
    var currentLetterInPosition = Array<Int>(6,{-1})

    private var start  = Calendar.getInstance()

    fun init(word:word){
        this.word=word
        start = Calendar.getInstance()
        currentCircle = Array(6,{' '})
        currentCircleLetterIn = Array(6,{-1})
        currentLetterInPosition = Array(6,{-1})
    }

    fun reset(word: word){
        init(word)
    }

    fun set(position: Int,letter:String){
        set(position,word.initialSeq.indexOf(letter[0]))
    }

    fun set(position: Int,positionLetter : Int){
        if(currentLetterInPosition[positionLetter]!=-1) return

        currentCircle[position] = word.initialSeq[positionLetter]
        if(currentCircleLetterIn[position]!=-1){
            currentLetterInPosition[currentCircleLetterIn[position]]=-1
        }
        currentCircleLetterIn[position] = positionLetter
        currentLetterInPosition[positionLetter]=position
    }

    fun updateView(root : View){
        val letter1 = root.findViewById<TextView>(R.id.letter1)
        val letter2 = root.findViewById<TextView>(R.id.letter2)
        val letter3 = root.findViewById<TextView>(R.id.letter3)
        val letter4 = root.findViewById<TextView>(R.id.letter4)
        val letter5 = root.findViewById<TextView>(R.id.letter5)
        val letter6 = root.findViewById<TextView>(R.id.letter6)

        if(currentLetterInPosition[0]!=-1) letter1.setText("")
        else letter1.setText(word.initialSeq[0].toString())

        if(currentLetterInPosition[1]!=-1) letter2.setText("")
        else letter2.setText(word.initialSeq[1].toString())

        if(currentLetterInPosition[2]!=-1) letter3.setText("")
        else letter3.setText(word.initialSeq[2].toString())

        if(currentLetterInPosition[3]!=-1) letter4.setText("")
        else letter4.setText(word.initialSeq[3].toString())

        if(currentLetterInPosition[4]!=-1) letter5.setText("")
        else letter5.setText(word.initialSeq[4].toString())

        if(currentLetterInPosition[5]!=-1) letter6.setText("")
        else letter6.setText(word.initialSeq[5].toString())
    }

    fun isCurrentOk() : Boolean{
        for(i in 0 until 6){
            if(currentCircleLetterIn[i]==-1 || word.difficulty==3) return false
            if(!word.initialSeq[currentCircleLetterIn[i]].equals(word.word[i],true)){
                Log.d("TAG","$i : letter:${word.initialSeq[currentCircleLetterIn[i]]} in place of ${word.word[i]}")
                return false
            }
            else{
                Log.d("TAG","$i : letter:${word.initialSeq[currentCircleLetterIn[i]]}=${word.word[i]}")
            }
        }

        return true
    }

    fun pop(position : Int,root:View){
        for(i in 0 until 6){
            try{

            if(word.initialSeq[i].equals(word.initialSeq[currentCircleLetterIn[position]])){
                if(currentLetterInPosition[i]!=-1){
                    currentLetterInPosition[i]=-1
                    currentCircleLetterIn[position]=-1
                    getLetter(i,root).setText(word.initialSeq[i].toString())
                    getLetterPosition(position,root).setText("")

                }
            }

            }
            catch (e:Exception){
                Log.d("TAG","letter $i empty")
            }
        }
    }


    fun getLetterPosition(position: Int,root: View):TextView{
        return when(position){
            0 -> root.findViewById(R.id.letterPosition1)
            1 -> root.findViewById(R.id.letterPosition2)
            2 -> root.findViewById(R.id.letterPosition3)
            3 -> root.findViewById(R.id.letterPosition4)
            4 -> root.findViewById(R.id.letterPosition5)
            5 -> root.findViewById(R.id.letterPosition6)
            else -> root.findViewById(R.id.letterPosition1)
        }
    }

    fun getTime() : Double{
        val now = Calendar.getInstance()
        val dist = now.timeInMillis.toDouble() - start.timeInMillis.toDouble()
        return (dist/1000)

    }

    fun getLetter(position: Int,root: View):TextView{
        return when(position){
            0 -> root.findViewById(R.id.letter1)
            1 -> root.findViewById(R.id.letter2)
            2 -> root.findViewById(R.id.letter3)
            3 -> root.findViewById(R.id.letter4)
            4 -> root.findViewById(R.id.letter5)
            5 -> root.findViewById(R.id.letter6)
            else -> root.findViewById(R.id.letter1)
        }
    }

    fun put(positionLetter :Int,root: View){
        for(i in 0 until 6){
            if(currentCircleLetterIn[i]==-1){
                set(i,positionLetter)
                getLetter(positionLetter,root).setText("")
                getLetterPosition(i,root).setText(word.initialSeq[positionLetter].toString())
                return
            }
        }
    }
}
