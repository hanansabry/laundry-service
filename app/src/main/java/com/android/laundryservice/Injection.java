package com.android.laundryservice;



import android.content.SharedPreferences;

import com.android.laundryservice.data.authentication.AuthenticationRepository;
import com.android.laundryservice.data.authentication.AuthenticationRepositoryImpl;
import com.android.laundryservice.data.invoice.InvoiceRepository;
import com.android.laundryservice.data.invoice.InvoiceRepositoryImpl;
import com.android.laundryservice.data.serviceItems.ServiceItemsRepository;
import com.android.laundryservice.data.serviceItems.ServiceItemsRepositoryImpl;
import com.android.laundryservice.data.services.ServicesRepository;
import com.android.laundryservice.data.services.ServicesRepositoryImpl;
import com.android.laundryservice.usecase.AuthenticationUseCaseHandler;

public class Injection {

    public static AuthenticationRepository provideAuthenticationRepository() {
        return new AuthenticationRepositoryImpl();
    }

    public static AuthenticationUseCaseHandler provideAuthenticationUseCaseHandler() {
        return new AuthenticationUseCaseHandler(provideAuthenticationRepository());
    }

    public static ServicesRepository provideServicesRepository() {
        return new ServicesRepositoryImpl();
    }

    public static ServiceItemsRepository provideServiceItemsRepository() {
        return new ServiceItemsRepositoryImpl();
    }

    public static InvoiceRepository provideInvoiceRepository(SharedPreferences sharedPreferences) {
        return new InvoiceRepositoryImpl(sharedPreferences);
    }
}
