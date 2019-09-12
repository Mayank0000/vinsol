package com.example.vinsol.ui.NewMeeting;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vinsol.R;
import com.example.vinsol.model.Date;
import com.example.vinsol.viewmodels.ViewModelProviderFactory;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NewMeetingActivity extends DaggerAppCompatActivity {

    @Inject ViewModelProviderFactory providerFactory;

    private Calendar calendar;
    private TextView tvDate,startTime,endTime;
    private EditText etDescription;
    private Button button;
    private TextView tvBack;
    private NewMeetingViewModel viewModel;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        inflateViews();
        getDataFromPrevious();
        viewModel = ViewModelProviders.of(this, providerFactory).get(NewMeetingViewModel.class);
        subscribeObservers();
        tvDate.setText(String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.YEAR)));
    }

    private void getDataFromPrevious(){
        calendar = (Calendar) getIntent().getSerializableExtra("calender");
    }
    private void inflateViews(){
        tvBack = findViewById(R.id.back);
        tvDate = findViewById(R.id.meetingDate);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        button = findViewById(R.id.butSubmit);
        etDescription = findViewById(R.id.description);
    }

    private void subscribeObservers() {
        viewModel.getAuthUser().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> userSendMessageResource) {
                if (userSendMessageResource != null) {
                    boolean bool = false;
                    double actualStart = Double.valueOf(startTime.getText().toString().replace(":","."));
                    double actualEnd = Double.valueOf(endTime.getText().toString().replace(":","."));
                    for(Date date: userSendMessageResource){
                        double start = Double.valueOf(date.getEndTime().replace(":","."));
                        double end = Double.valueOf(date.getStartTime().replace(":","."));
                        if((start > actualStart  && start < actualEnd)||(end>actualStart&& end<actualEnd)){
                            bool = true;
                            break;
                        }
                    }
                    if(bool)
                        Toast.makeText(NewMeetingActivity.this,"Slot not Available",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(NewMeetingActivity.this,"Slot Available",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(NewMeetingActivity.this, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1,
                                calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(String.format("%d:%d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                String description =  etDescription.getText().toString();
                if (startTime.getText().toString().isEmpty()) {
                    Toast.makeText(NewMeetingActivity.this, "Please enter start time", Toast.LENGTH_SHORT).show();
                } else if (endTime.getText().toString().isEmpty()) {

                    Toast.makeText(NewMeetingActivity.this, "Please enter end time", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.authenticateWithId(String.format("%d/%d/%d", calendar
                                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)));
                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tvDate.setText(String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.YEAR)));
        }

    };
}
