package doit.yvanigorahishakiye.FinalProjectApplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import doit.yvanigorahishakiye.FinalProjectApplication.AddNewTask;
import doit.yvanigorahishakiye.FinalProjectApplication.MainActivity;
import doit.yvanigorahishakiye.FinalProjectApplication.Model.ToDoModel;
import doit.yvanigorahishakiye.FinalProjectApplication.R;
import doit.yvanigorahishakiye.FinalProjectApplication.Utils.DataBaseHelper;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {


    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDb;

    public ToDoAdapter(DataBaseHelper myDb, MainActivity activity){
        this.activity = activity;
        this.myDb = myDb;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = mList.get(position);
        holder.mChekBox.setText(item.getTask());
        holder.mChekBox.setChecked(toBoolean(item.getStatus()));
        holder.mChekBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    myDb.updateStatus(item.getId(), 1);
                }else{
                    myDb.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }


    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel>mList){
        this.mList=mList;
        notifyDataSetChanged();
    }

    public void deletTask(int position){
        ToDoModel item = mList.get(position);
        myDb.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public void editItem(int position){
        ToDoModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(), task.getTag());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox mChekBox;

         public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mChekBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
