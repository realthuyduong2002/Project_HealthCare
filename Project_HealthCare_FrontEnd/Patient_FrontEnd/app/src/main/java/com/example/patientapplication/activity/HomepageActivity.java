package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.patientapplication.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    String url1 = "https://wiomc.org/wp-content/uploads/2023/04/2bgqjhmw_0iifqf_p8twtq.jpg";
    String url2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTI_jCThVQoY8TwxhxSQ3BTm_aDxXDs9h-5S_99-ad70g&s";
    String url3 = "https://online.hbs.edu/Style%20Library/api/resize.aspx?imgpath=/online/PublishingImages/blog/health-care-economics.jpg&w=1200&h=630";

    ImageView ivAppointment, ivUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ivAppointment = findViewById(R.id.ivAppointment);
        ivUser = findViewById(R.id.ivUser); // Assuming ivUser represents the "User" button

        ivAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, PatientFunctionPage.class));
            }
        });

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        SliderView sliderView = findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);


        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();
    }
}
