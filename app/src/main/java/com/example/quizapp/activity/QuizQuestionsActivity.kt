package com.example.quizapp.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.quizapp.Constants
import com.example.quizapp.Question
import com.example.quizapp.R

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var optionOne: TextView
    private lateinit var optionTwo: TextView
    private lateinit var optionThree: TextView
    private lateinit var optionFour: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgressBar: TextView
    private lateinit var btnSubmit: Button

    private var questionsSize: Int = 0
    private var currentPosition: Int = 1
    private var questions: List<Question>? = null
    private var mSelectedPosition: Int = 0
    private var mUserName: String? = null

    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        questions = Constants.getQuestions()

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)
        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tv_progress)
        btnSubmit = findViewById(R.id.btn_submit)

        if (!questions.isNullOrEmpty()) {
            questionsSize = questions!!.size
            setProgress()
            setQuestionOption()
        }

        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(optionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(optionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(optionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(optionFour, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedPosition == 0) {
                    currentPosition += 1

                    when {
                        currentPosition <= questionsSize -> {
                            updateProgress()
                            setQuestionOption()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsSize)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = questions!![currentPosition - 1]
                    if(question.correctAnswer != mSelectedPosition) {
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (currentPosition == questionsSize) {
                        btnSubmit.text = "FINISH"
                    } else {
                        btnSubmit.text = "GO TO THE NEXT QUESTION"
                    }
                    mSelectedPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                optionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                optionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                optionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                optionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionsView()
        mSelectedPosition = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#263A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun setProgress() {
        progressBar.max = questionsSize
        updateProgress()
    }

    private fun updateProgress() {
        progressBar.progress = currentPosition
        tvProgressBar.text = "$currentPosition/${questionsSize}"
    }

    private fun setQuestionOption() {
        val question: Question = questions!![currentPosition - 1]
        defaultOptionsView()
        tvQuestion.text = question.question
        ivImage.setImageResource(question.image)
        optionOne.text = question.optionOne
        optionTwo.text = question.optionTwo
        optionThree.text = question.optionThree
        optionFour.text = question.optionFour


        if (currentPosition == questionsSize) {
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options: MutableList<TextView> = mutableListOf()
        options.add(0, optionOne)
        options.add(1, optionTwo)
        options.add(2, optionThree)
        options.add(3, optionFour)

        options.map { option ->
            option.setTextColor(Color.parseColor("#718089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }
}