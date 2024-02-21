package com.apkglobal.taekook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.MyHolder>{

    Context context;
    List<ModelComment> commentList;

    public AdapterComments(Context context, List<ModelComment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //bind the row_comments.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_comments, viewGroup, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        //get data
        String uid = commentList.get(i).getUid();
        String name = commentList.get(i).getName();
        String email = commentList.get(i).getEmail();
        String image = commentList.get(i).getDp();
        String cid = commentList.get(i).getcId();
        String comment = commentList.get(i).getComment();
        String timeStamp = commentList.get(i).getTimeStamp();

        //convert time stamp to dd/mm/yy hh:mm:ss
       // Calendar calendar = Calendar.getInstance(Locale.getDefault());
       // calendar.setTimeInMillis(Long.parseLong(timeStamp));
       // String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        //set data
        myHolder.nameTv.setText(name);
        myHolder.commentTv.setText(comment);
       // myHolder.timeTv.setText(dateTime);
        myHolder.timeTv.setVisibility(View.GONE);

        //user dp
        try{
            Picasso.get().load(image).placeholder(R.drawable.avatar).into(myHolder.avatarIv);
        }
        catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        //declare views from row_comments.xml
        ImageView avatarIv;
        TextView nameTv, commentTv, timeTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            commentTv = itemView.findViewById(R.id.commentTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
