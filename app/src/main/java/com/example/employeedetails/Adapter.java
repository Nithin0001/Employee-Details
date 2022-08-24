package com.example.employeedetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Employee> {
    public Adapter(Context context, ArrayList<Employee> employeeArrayList){
        super(context, R.layout.list_item, employeeArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Employee employee = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.profileImg);
        TextView empId = convertView.findViewById(R.id.empId);
        TextView firstName = convertView.findViewById(R.id.firstName);
        TextView lastName = convertView.findViewById(R.id.lastName);
        TextView empEmail = convertView.findViewById(R.id.email);

        imageView.setClipToOutline(true);

        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(employee.imgURL);
        empId.setText("Employee Id - " + employee.empId);
        firstName.setText("First Name - " + employee.firstName);
        lastName.setText("Last Name - " + employee.lastName);
        empEmail.setText("Email - " + employee.email);

        return convertView;
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
        }
    }
}
