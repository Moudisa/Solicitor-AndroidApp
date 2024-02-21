package com.apkglobal.taekook;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterArticles extends RecyclerView.Adapter<AdapterArticles.MyHolder> {

    Context context;
    List<Articles> articlesList;


    //constructor
    public AdapterArticles(Context context, List<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

        @NonNull
        @Override
        public AdapterArticles.MyHolder onCreateViewHolder (@NonNull ViewGroup viewGroup,int i){
            //inflate layout (articles_single_layout.xml)
            View view = LayoutInflater.from(context).inflate(R.layout.articles_single_layout, viewGroup, false);
            return new MyHolder(view);

        }

        @Override
        public void onBindViewHolder (@NonNull AdapterArticles.MyHolder myHolder,int i){

            //get data
            final String pId = articlesList.get(i).getpId();
            final String uid = articlesList.get(i).getUid();
            String name = articlesList.get(i).getName();
            String dp = articlesList.get(i).getDp();
            String pTimeStamp = articlesList.get(i).getpTime();
            String pTitle = articlesList.get(i).getpTitle();
            String pCategory = articlesList.get(i).getpCategory();
            String pArticle = articlesList.get(i).getpDescription();

            //convert timestamp
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
            String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

            //set data
            myHolder.uNameTv.setText(name);
            myHolder.pTimeTv.setText(pTime);
            myHolder.pTitleTv.setText(pTitle);
            myHolder.pCategoryTv.setText(pCategory);

            //set user dp
            try{
                Picasso.get().load(dp).placeholder(R.drawable.avatar).into(myHolder.uPictureIv);
            }
            catch(Exception e){

            }

            myHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                }
            });

            //handle item check
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowArticleActivity.class);
                    intent.putExtra("postId", pId);
                    context.startActivity(intent);
                }
            });

        }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //views from row_post.xml
        CircleImageView uPictureIv;
        TextView uNameTv, pTimeTv, pTitleTv, pCategoryTv;
        Button shareBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pCategoryTv = itemView.findViewById(R.id.pCategoryTv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            shareBtn = itemView.findViewById(R.id.shareBtn);


        }
    }
}
