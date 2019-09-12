package com.example.vinsol.ui.meetingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vinsol.R;
import com.example.vinsol.model.Date;

import java.util.ArrayList;
import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {
    private List<Date> dateList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_card,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(dateList.get(position));
    }

    public void setMeetingList(List<Date> dateList){
        this.dateList = dateList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;   // TextView to set contact name
        private TextView tvTime;  // TextView to set contact Number

        ViewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
            tvTime = (TextView) itemView.findViewById(R.id.time);
        }

        void bind(Date date){
            tvDescription.setText(date.getDescription()); // full name of contact
            tvTime.setText(String.format("%s - %s", date.getStartTime(), date.getEndTime())); // contact number
        }

    }
}
