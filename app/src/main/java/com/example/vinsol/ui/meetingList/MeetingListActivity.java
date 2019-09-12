package com.example.vinsol.ui.meetingList;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vinsol.R;
import com.example.vinsol.model.Date;
import com.example.vinsol.ui.NewMeeting.NewMeetingActivity;
import com.example.vinsol.viewmodels.ViewModelProviderFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MeetingListActivity extends DaggerAppCompatActivity {

    @Inject ViewModelProviderFactory providerFactory;
    @Inject MeetingListAdapter adapter;
    @Inject Calendar calendar;
    @Inject DividerItemDecoration itemDecorator;

    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;
    private TextView title;
    private TextView next;
    private TextView prev;
    private Button butScheduleMeeting;

    private MeetingViewModel viewModel;
    private int year, month, day ;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list);
        inflateViews();
        viewModel = ViewModelProviders.of(this, providerFactory).get(MeetingViewModel.class);
        butScheduleMeeting = findViewById(R.id.butScheduleMeeting);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        init();
        showProgressBar(true);
        subscribeObservers();
        title.setText(String.format("%d/%d/%d", day, month+1, year));
        viewModel.authenticateWithId(String.format("%d/%d/%d", day, month+1, year));
    }



    @Override
    protected void onStart() {
        super.onStart();
        butScheduleMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingListActivity.this, NewMeetingActivity.class);
                intent.putExtra("calender", calendar);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day+1);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                title.setText(String.format("%d/%d/%d", day, month, year));
                viewModel.authenticateWithId(String.format("%d/%d/%d", day, month, year));

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day-1);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                title.setText(String.format("%d/%d/%d", day, month, year));
                viewModel.authenticateWithId(String.format("%d/%d/%d", day, month, year));
            }
        });
    }

    private void inflateViews(){
        recyclerView = findViewById(R.id.list);
        mProgressBar = findViewById(R.id.progress);
        title = findViewById(R.id.title);
        next = findViewById(R.id.next);
        prev =findViewById(R.id.prev);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);
    }


    private void subscribeObservers() {
        viewModel.getAuthUser().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> userSendMessageResource) {
                if (userSendMessageResource != null) {
                    adapter.setMeetingList(userSendMessageResource);
                    showProgressBar(false);
                }
            }
        });
    }

    private void showProgressBar(Boolean visible){
        if(visible){
            mProgressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            mProgressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }
}

