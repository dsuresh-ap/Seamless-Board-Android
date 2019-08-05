package com.uipath.seamlessboard.presentation.addreview

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.databinding.ActivityAddReviewBinding
import kotlinx.android.synthetic.main.activity_add_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddReviewActivity : AppCompatActivity() {

    private lateinit var addReviewBinding: ActivityAddReviewBinding
    private lateinit var restaurantsAdapter: ArrayAdapter<String>
    private val addReviewViewModel: AddReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_review)
        addReviewBinding.lifecycleOwner = this

        addReviewBinding.addReviewViewModel = addReviewViewModel

        toolbar.title = getString(R.string.add_review)
        setSupportActionBar(toolbar)

        setupLiveDataObservables()
        restaurantsAdapter = ArrayAdapter(this, R.layout.select_dialog_item_material)
        nameAutoCompleteTextView.setAdapter(restaurantsAdapter)
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

        addReviewViewModel.restaurantLiveData().observe(this, Observer { restaurants ->
            restaurants?.let {
                restaurantsAdapter.setNotifyOnChange(false)
                restaurantsAdapter.clear()
                restaurantsAdapter.addAll(restaurants)
                restaurantsAdapter.notifyDataSetChanged()
            }
        })
    }
}