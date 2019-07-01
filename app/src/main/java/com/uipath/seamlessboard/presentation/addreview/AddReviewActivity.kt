package com.uipath.seamlessboard.presentation.addreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.databinding.ActivityAddReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddReviewActivity : AppCompatActivity() {

    lateinit var addReviewBinding: ActivityAddReviewBinding
    private val addReviewViewModel: AddReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_review)
        addReviewBinding.lifecycleOwner = this

        addReviewBinding.addReviewViewModel = addReviewViewModel

        setupLiveDataObservables()
    }

    private fun setupLiveDataObservables() {
        addReviewViewModel.restaurantNameError.observe(this, Observer {
            addReviewBinding.nameInputLayout.error = if (it == null) null else getString(it)
        })

        addReviewViewModel.reviewError.observe(this, Observer {
            addReviewBinding.reviewInputLayout.error = if (it == null) null else getString(it)
        })

        addReviewViewModel.navigationLiveData().observe(this, Observer {
            when (it) {
                is AddReviewNavigationCommand.FinishActivity -> finish()
            }
        })
    }
}