package com.example.manuel.baseproject.ui

import android.content.Intent
import android.os.Bundle
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.ui.BaseActivity
import com.example.manuel.baseproject.commons.utils.enums.MealsType
import com.example.manuel.baseproject.ui.mapper.MealsButtonMapper
import kotlinx.android.synthetic.main.activity_meals_selector.*

class MealsSelectorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals_selector)

        setListeners()
    }

    private fun setListeners() {
        activity_meals_selector_search_button.setOnClickListener { onSearchButtonPressed() }
    }

    private fun onSearchButtonPressed() {
        val selectedMealType = getMealType()

        val intent = Intent(this, BeersResultsActivity::class.java)
        intent.putExtra(IntentConstants.INTENT_MEAL_TYPE, selectedMealType)

        startActivity(intent)
    }

    private fun getMealType(): MealsType {
        val mealType: MealsType

        val checkedRadioButtonId = activity_meals_selector_radio_group_meals.checkedRadioButtonId

        mealType = MealsButtonMapper.IdToEnum.mapFrom(checkedRadioButtonId)

        return mealType
    }
}