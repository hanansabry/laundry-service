package com.android.laundryservice.data.services;

import com.android.laundryservice.model.Service;

import java.util.ArrayList;

public class ServicesRepositoryImpl implements ServicesRepository {
    @Override
    public void retrieveServices(RetrieveServicesCallback callback) {
        ArrayList<Service> services = new ArrayList<>();

        Service s1 = new Service();
        s1.setId("1");
        s1.setName("Drying");
        s1.setImage("https://i.dailymail.co.uk/i/pix/2017/06/13/09/416224F800000578-4598916-image-a-1_1497342326305.jpg");

        Service s2 = new Service();
        s2.setId("2");
        s2.setName("Dry Cleaning ");
        s2.setImage("https://highlandercleaners.com/wp-content/uploads/2018/01/laundry-baskey.jpg");

        Service s3 = new Service();
        s3.setId("3");
        s3.setName("Ironing Clothing");
        s3.setImage("https://irp-cdn.multiscreensite.com/03d3b082/dms3rep/multi/mobile/laundry-and-ironing-hero-image.jpg");

        services.add(s1);
        services.add(s2);
        services.add(s3);
        callback.onServiceRetrievedSuccessfully(services);
    }
}
