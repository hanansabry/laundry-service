package com.android.laundryservice.data.serviceItems;

import com.android.laundryservice.data.serviceItems.ServiceItemsRepository;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;

public class ServiceItemsRepositoryImpl implements ServiceItemsRepository {

    @Override
    public void retrieveServiceItemsByService(String serviceId, RetrieveServiceItemsCallback callback) {
        Service dryingService = new Service();
        dryingService.setId("1");
        dryingService.setName("Drying");
        dryingService.setImage("https://i.dailymail.co.uk/i/pix/2017/06/13/09/416224F800000578-4598916-image-a-1_1497342326305.jpg");

        Service dryCleaningService = new Service();
        dryCleaningService.setId("2");
        dryCleaningService.setName("Dry Cleaning ");
        dryCleaningService.setImage("https://highlandercleaners.com/wp-content/uploads/2018/01/laundry-baskey.jpg");

        Service ironingService = new Service();
        ironingService.setId("3");
        ironingService.setName("Ironing Clothing");
        ironingService.setImage("https://irp-cdn.multiscreensite.com/03d3b082/dms3rep/multi/mobile/laundry-and-ironing-hero-image.jpg");

        ////////////////////////////////////////////////////////////
        ServiceItem s1 = new ServiceItem();
        s1.setId("1");
        s1.setName("Service Item 1");
        s1.setUnitPrice(5.5);
        s1.setService(dryingService);

        ServiceItem s2 = new ServiceItem();
        s2.setId("2");
        s2.setName("Service Item 2");
        s2.setUnitPrice(6.5);
        s2.setService(dryingService);

        ServiceItem s3 = new ServiceItem();
        s3.setId("3");
        s3.setName("Service Item 3");
        s3.setUnitPrice(7.5);
        s3.setService(dryingService);

        ServiceItem s10 = new ServiceItem();
        s10.setId("10");
        s10.setName("Service Item 1");
        s10.setUnitPrice(5.5);
        s10.setService(dryingService);

        ServiceItem s11 = new ServiceItem();
        s11.setId("11");
        s11.setName("Service Item 2");
        s11.setUnitPrice(6.5);
        s11.setService(dryingService);

        ServiceItem s12 = new ServiceItem();
        s12.setId("12");
        s12.setName("Service Item 3");
        s12.setUnitPrice(7.5);
        s12.setService(dryingService);


        ///////////////////////////////////////////////////////////////////
        ServiceItem s4 = new ServiceItem();
        s4.setId("4");
        s4.setName("Service Item 4");
        s4.setUnitPrice(5.5);
        s4.setService(dryCleaningService);

        ServiceItem s5 = new ServiceItem();
        s5.setId("5");
        s5.setName("Service Item 5");
        s5.setUnitPrice(6.5);
        s5.setService(dryCleaningService);

        ServiceItem s6 = new ServiceItem();
        s6.setId("6");
        s6.setName("Service Item 6");
        s6.setUnitPrice(7.5);
        s6.setService(dryCleaningService);

        /////////////////////////////////////////////////////////////////
        ServiceItem s7 = new ServiceItem();
        s7.setId("7");
        s7.setName("Service Item 7");
        s7.setUnitPrice(5.5);
        s7.setService(ironingService);


        ServiceItem s8 = new ServiceItem();
        s8.setId("8");
        s8.setName("Service Item 8");
        s8.setUnitPrice(6.5);
        s8.setService(ironingService);

        ServiceItem s9 = new ServiceItem();
        s9.setId("9");
        s9.setName("Service Item 9");
        s9.setUnitPrice(7.5);
        s9.setService(ironingService);

        ArrayList<ServiceItem> serviceItems1 = new ArrayList<>();
        serviceItems1.add(s1);
        serviceItems1.add(s2);
        serviceItems1.add(s3);
        serviceItems1.add(s10);
        serviceItems1.add(s11);
        serviceItems1.add(s12);

        ArrayList<ServiceItem> serviceItems2 = new ArrayList<>();
        serviceItems2.add(s4);
        serviceItems2.add(s5);
        serviceItems2.add(s6);

        ArrayList<ServiceItem> serviceItems3 = new ArrayList<>();
        serviceItems3.add(s7);
        serviceItems3.add(s8);
        serviceItems3.add(s9);

        if (serviceId.equals("1")) {
            callback.onServiceItemsRetrievedSuccessfully(serviceItems1);
        } else if (serviceId.equals("2")) {
            callback.onServiceItemsRetrievedSuccessfully(serviceItems2);
        } else if (serviceId.equals("3")) {
            callback.onServiceItemsRetrievedSuccessfully(serviceItems3);
        }

    }
}
