package com.example.projectattendance.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projectattendance.Model.Attendance;
import com.example.projectattendance.R;

import java.util.List;

public class AttendanceListAdapter extends ArrayAdapter<Attendance> {

    private Context mContext;
    private List<Attendance> mAttendanceList;

    public AttendanceListAdapter(@NonNull Context context, @NonNull List<Attendance> attendanceList) {
        super(context, 0, attendanceList);
        mContext = context;
        mAttendanceList = attendanceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Attendance currentAttendance = mAttendanceList.get(position);

        TextView rollNoTextView = listItem.findViewById(R.id.text_roll_no);
        rollNoTextView.setText(currentAttendance.getRollNO());

        TextView subjectTextView = listItem.findViewById(R.id.text_subject);
        subjectTextView.setText(currentAttendance.getSubject());

        TextView dateTextView = listItem.findViewById(R.id.text_date);
        dateTextView.setText(currentAttendance.getDate());

//        TextView attendanceTextView = listItem.findViewById(R.id.text_attendance);
//        attendanceTextView.setText(currentAttendance.getAttendance());

        return listItem;
    }
}
