package com.example.projectattendance.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectattendance.MainActivity;
import com.example.projectattendance.MarkAttendanceByStudent;
import com.example.projectattendance.Model.SubjectModel;
import com.example.projectattendance.R;
import com.example.projectattendance.SignInActivity;
import com.example.projectattendance.SignUpActivity;

import java.util.ArrayList;

public class ChooseSubjectAdapter extends RecyclerView.Adapter<ChooseSubjectAdapter.viewHolder> {
    ArrayList<SubjectModel> list;
    Context context;

    public ChooseSubjectAdapter(ArrayList<SubjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_recycler_view,parent,false);
       // view.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SubjectModel model=list.get(position);
        holder.imageView.setImageResource(model.getPic());
        holder.textView.setText(model.getText());

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Item is Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedItemText = model.getText();
                Intent intent = new Intent(context, MarkAttendanceByStudent.class);
                intent.putExtra("SELECTED_ITEM_TEXT", selectedItemText);
                // Intent intent=new Intent(context,MarkAttendanceByStudent.class);
                context.startActivity(intent);
                //Toast.makeText(context, "Text clicked is "+selectedItemText, Toast.LENGTH_SHORT).show();
            }
        });





        /* switch(position){
            case 0:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(context,MarkAttendanceByStudent.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "Image one is clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String selectedItemText = model.getText();
                        Intent intent = new Intent(context, MarkAttendanceByStudent.class);
                        intent.putExtra("SELECTED_ITEM_TEXT", selectedItemText);
                       // Intent intent=new Intent(context,MarkAttendanceByStudent.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "Text clicked is "+selectedItemText, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Image two is clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Text two is clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 2:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Image three is clicked", Toast.LENGTH_SHORT).show();

                    }
                });
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Text three is clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
        }

        */
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_choice1);
            textView =itemView.findViewById(R.id.txt_choice1);


        }
    }

}
