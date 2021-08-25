package com.example.quizapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.quizapp.Constants
import com.example.quizapp.Question
import com.example.quizapp.R

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var optionOne: TextView
    private lateinit var optionTwo: TextView
    private lateinit var optionThree: TextView
    private lateinit var optionFour: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgressBar: TextView

    private var questionsSize: Int = 0
    private var currentPosition: Int = 1
    private var questions: List<Question> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        questions = Constants.getQuestions()

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)
        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tv_progress)

        val question: Question = questions[currentPosition - 1]
        questionsSize = questions.size
        setProgress()
        setQuestionOption(question)
    }

    private fun setProgress() {
        progressBar.max = questionsSize
        updateProgress()
    }

    private fun updateProgress() {
        progressBar.progress = currentPosition
        tvProgressBar.text = "$currentPosition/${questionsSize}"
    }

    private fun setQuestionOption(question: Question) {
        tvQuestion.text = question.question
        ivImage.setImageResource(question.image)
        optionOne.text = question.optionOne
        optionTwo.text = question.optionTwo
        optionThree.text = question.optionThree
        optionFour.text = question.optionFour
    }
}