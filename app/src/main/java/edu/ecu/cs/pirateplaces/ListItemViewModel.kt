package edu.ecu.cs.pirateplaces

import android.content.Context

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.text.DateFormat
import java.util.*

private const val TAG = "ListItemViewModel"

class ListItemViewModel(private val dateFormat: DateFormat, private val timeFormat: DateFormat) {
    private var _place = PiratePlace()

    var place:PiratePlace
        set(place: PiratePlace) {
            _place = place
            _name.value = place.name
            _visitedWith.value = place.visitedWith
            _lastVisited.value = place.lastVisited
        }
        get() {
            return _place
        }

    private val _name = MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name

    private var _visitedWith = MutableLiveData<String>()
    val visitedWith : LiveData<String>
        get() = _visitedWith

    private var _lastVisited = MutableLiveData<Date>()
    private val lastVisited : LiveData<Date>
        get() = _lastVisited

    val dateString: String
        get() = dateFormat.format(lastVisited.value)

    val timeString: String
        get() = timeFormat.format(lastVisited.value)

}