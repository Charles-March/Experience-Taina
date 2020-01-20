package com.neoxia.charlesmarchand.experience

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recap.*
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.content.FileProvider
import java.io.File
import android.widget.Toast
import android.content.DialogInterface
import android.support.v7.app.AlertDialog




class RecapActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recap)

        Score.setText("Votre score final est de : ${Saves.getPointText()}")

        envoi.setOnClickListener {
            val emailIntent = Intent(android.content.Intent.ACTION_SEND);

            val f = File.createTempFile("resultats${Saves.numero}",".csv", getExternalStorageDirectory())
            f.writeText(Saves.getRecap())
            val path = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider", f);

            emailIntent .setType("vnd.android.cursor.dir/email");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, Array<String>(1,{"victortaina@gmail.com"}))
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Resultat sujet : ${Saves.numero}");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"");
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            startActivity(Intent.createChooser(emailIntent, "envoi de mail"));

        }

        retour.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                    //set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    //set title
                    .setTitle("Voulez-vous vraiment retourner au début ?")
                    //set message
                    .setMessage("Attention cette action est irreversible")
                    //set positive button
                    .setPositiveButton("Oui", DialogInterface.OnClickListener { dialogInterface, i ->
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    })
                    //set negative button
                    .setNegativeButton("Non", DialogInterface.OnClickListener { dialogInterface, i ->
                    })
                    .show()
        }
    }

    override fun onBackPressed() {

    }

}