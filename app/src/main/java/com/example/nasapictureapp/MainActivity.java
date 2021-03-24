package com.example.nasapictureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private List<ImagesResponse> imagesResponseList = new ArrayList<>();

  GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        getAllImages();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Name :"+imagesResponseList.get(position).getTitle();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                startActivity(new Intent( MainActivity.this,DetailedActivity.class).putExtra("data",imagesResponseList.get(position)));

            }
        });
    }


    public void getAllImages(){
        Call<List<ImagesResponse>> imagesResponse = ApiClient.getInterface().getAllImages();

        imagesResponse.enqueue(new Callback<List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<List<ImagesResponse>> call, Response<List<ImagesResponse>> response) {

                if(response.isSuccessful()){

                    String message = "Request successfull";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                    imagesResponseList = response.body();
                    CustomAdapter customAdapter = new CustomAdapter(imagesResponseList,MainActivity.this);
                    gridView.setAdapter(customAdapter);
                }else
                {
                    String message = "An error occured try again later";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ImagesResponse>> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }

    public class CustomAdapter extends BaseAdapter{

        private  List<ImagesResponse> imagesResponseList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<ImagesResponse> imagesResponseList, Context context) {
            this.imagesResponseList = imagesResponseList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesResponseList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.row_grid_items,parent,false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView=convertView.findViewById(R.id.textView);

            textView.setText(imagesResponseList.get(position).getTitle());
            GlideApp.with(context).load(imagesResponseList.get(position).getUrl()).into(imageView);

            return convertView;
        }
    }
}