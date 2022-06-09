 package com.example.stayfit

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stayfit.databinding.ActivityExerciseBinding
import com.example.stayfit.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer : CountDownTimer ?= null
    private var restProgress =  0
    private var restTimerDuration:Long = 1

    // Adding a variables for the 30 seconds Exercise timer

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration:Long = 30
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

//    TEXT TO SPEECH

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter:ExerciseStatusAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()
        setRecyclerVew()

    }

    override fun onBackPressed() {
        customDialogForBackButton()
        /*super.onBackPressed()*/
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        var dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setRecyclerVew(){
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun setUpRestView(){

        try {
            val soundURL = Uri.parse("Stay Fit" +R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundURL)
            player?.isLooping = false
            player?.start()

        }catch (e:Exception){
            e.printStackTrace()
        }


        binding.flRestView.visibility= View.VISIBLE
        binding.tvTitle.visibility= View.VISIBLE
        binding.tvUpcomingExerciseName.visibility= View.VISIBLE
        binding.upcomingLabel.visibility= View.VISIBLE
        binding.tvExerciseName.visibility= View.INVISIBLE
        binding.flExerciseView.visibility= View.INVISIBLE
        binding.ivImage.visibility= View.INVISIBLE

        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {

        binding.flRestView.visibility= View.INVISIBLE
        binding.tvTitle.visibility= View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility= View.INVISIBLE
        binding.upcomingLabel.visibility= View.INVISIBLE
        binding.tvExerciseName.visibility= View.VISIBLE
        binding.flExerciseView.visibility= View.VISIBLE
        binding.ivImage.visibility= View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()

        speakOut(exerciseList?.get(currentExercisePosition)!!.getName())

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

    }

    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress= exerciseProgress

        exerciseTimer = object : CountDownTimer( 30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress= exerciseTimerDuration.toInt() - exerciseProgress
                binding.tvTimerExercise.text= (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {



                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val i = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(i)
                }
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player != null){
            player!!.stop()
        }
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS){
            val result =tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language is not supported")
            }
        }else{
            Log.e("TTS","Language is Failed")
        }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}