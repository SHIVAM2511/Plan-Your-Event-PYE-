package com.example.pye.Callback;

import com.example.pye.Model.TimeSlot;

import java.util.List;

public interface ITimeSlotLoadListener {
    void onTimeLoadSuccess(List<TimeSlot> timeSlotList);
    void onTimeLoadFailed(String message);
    void onTimeLoadEmpty();
}
