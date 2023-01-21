package com.varadasainikhil.quizzer

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition : Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectedOptionPosition :Int = 0
    private var btnSubmit :Button? = null
    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var ivImage : ImageView? = null
    private var tvoption1 : TextView? = null
    private var tvoption2 : TextView? = null
    private var tvoption3 : TextView? = null
    private var tvoption4 : TextView? = null
    private var mUserName :String? = null
    private var score : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        progressBar = findViewById(R.id.progressbar)
        tvQuestion =  findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvQuestion =  findViewById(R.id.tv_question)
        tvProgress = findViewById(R.id.tv_progress)
        tvoption1 = findViewById(R.id.option1)
        tvoption2 = findViewById(R.id.option2)
        tvoption3 = findViewById(R.id.option3)
        tvoption4 = findViewById(R.id.option4)
        btnSubmit = findViewById(R.id.btn_submit)
        mQuestionsList = Constants.getQuestions()
        setQuestion()


    }

    private fun setQuestion() {
        defaultOptionsView()
        val question = mQuestionsList!![mCurrentPosition - 1]  //Since mQuestionsList is a nullable, we have to add a check in the form of !!.
        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "${mCurrentPosition}/${mQuestionsList!!.size} "
        tvoption1?.text = question.option1
        tvoption2?.text = question.option2
        tvoption3?.text = question.option3
        tvoption4?.text = question.option4
        tvoption1?.setOnClickListener(this)
        tvoption2?.setOnClickListener(this)
        tvoption3?.setOnClickListener(this)
        tvoption4?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvoption1?.let { options.add(0,it) }
        tvoption2?.let { options.add(1,it) }
        tvoption3?.let { options.add(2,it) }
        tvoption4?.let { options.add(3,it) }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv : TextView, selectedOptionNum : Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selecter_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.option1 -> {
                tvoption1?.let { selectedOptionView(it,1) }
            }
            R.id.option2 -> {
                tvoption2?.let { selectedOptionView(it,2) }
            }
            R.id.option3 -> {
                tvoption3?.let { selectedOptionView(it,3) }
            }
            R.id.option4 -> {
                tvoption4?.let { selectedOptionView(it,4) }
            }
            R.id.btn_submit ->{
                if (mSelectedOptionPosition==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <=  mQuestionsList!!.size ->{
                            setQuestion()
                        }
                        else->{
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS,score)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){

                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }else{
                        score++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit?.text = "FINISH"
                    }
                    else{
                        btnSubmit?.text ="GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition=0


                }
            }
        }
    }

    private fun answerView(answer : Int, drawableView:Int){
        when(answer ){
            1 -> {
                tvoption1?.setTypeface(tvoption1?.typeface,Typeface.BOLD)
                tvoption1?.setTextColor(Color.parseColor("#FFFFFF"))
                tvoption1?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvoption2?.setTypeface(tvoption2?.typeface,Typeface.BOLD)
                tvoption2?.setTextColor(Color.parseColor("#FFFFFF"))
                tvoption2?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvoption3?.setTypeface(tvoption3?.typeface,Typeface.BOLD)
                tvoption3?.setTextColor(Color.parseColor("#FFFFFF"))
                tvoption3?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvoption4?.setTypeface(tvoption4?.typeface,Typeface.BOLD)
                tvoption4?.setTextColor(Color.parseColor("#FFFFFF"))
                tvoption4?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

}