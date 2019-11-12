package com.android.laundryservice.usecase;

import com.android.laundryservice.data.laundries.LaundriesRepository;
import com.android.laundryservice.data.regions.RegionRepository;

import java.util.ArrayList;

public class MapsUseCaseHandler {

    private final RegionRepository regionRepository;
    private final LaundriesRepository laundriesRepository;


    public MapsUseCaseHandler(RegionRepository regionRepository, LaundriesRepository laundriesRepository) {
        this.regionRepository = regionRepository;
        this.laundriesRepository = laundriesRepository;
    }

    public void getAvailableRegions(RegionRepository.RegionRepositoryCallback callback) {
        regionRepository.retrieveRegions(callback);
    }

    public void getLaundriesByRegions(String region, LaundriesRepository.LaundriesRepositoryCallback callback) {
        laundriesRepository.retrieveLaundriesByRegions(region, callback);
    }
}
