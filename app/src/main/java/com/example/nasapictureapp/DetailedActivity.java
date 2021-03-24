package com.example.nasapictureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedActivity extends Activity {

    ImagesResponse imagesResponse;
    TextView tvName,tvExplanation,date;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        tvName = findViewById(R.id.selectedName);
        tvExplanation=findViewById(R.id.selectedExplanation);
        date = findViewById(R.id.date);
        imageView = findViewById(R.id.selectedImage);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            imagesResponse =  (ImagesResponse) intent.getSerializableExtra("data");
            tvName.setText(imagesResponse.getTitle());
            tvExplanation.setText(imagesResponse.getExplanation());
            date.setText(imagesResponse.getDate());
            GlideApp.with(this).load(imagesResponse.getUrl()).into(imageView);
        }
    }
}