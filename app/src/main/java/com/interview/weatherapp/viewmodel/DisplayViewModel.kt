package com.interview.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.interview.weatherapp.model.Display
import io.reactivex.rxjava3.subjects.BehaviorSubject

class DisplayViewModel : ViewModel() {

    private var _display = BehaviorSubject.create<Display>()
    val display get() = _display

    private var _showDefaultCity = true
    val showDefaultCity get() = _showDefaultCity

    fun shouldNotShowDefaultCity() {
        _showDefaultCity = false
    }

    fun updateDisplay(display: Display) {
        _display.onNext(display)
    }
}