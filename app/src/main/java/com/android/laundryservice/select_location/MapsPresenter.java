package com.android.laundryservice.select_location;

import com.android.laundryservice.data.laundries.LaundriesRepository;
import com.android.laundryservice.data.regions.RegionRepository;
import com.android.laundryservice.usecase.MapsUseCaseHandler;

public class MapsPresenter {

    private final MapsUseCaseHandler useCaseHandler;

    public MapsPresenter(MapsUseCaseHandler useCaseHandler) {
        this.useCaseHandler = useCaseHandler;
    }

    public void retrieveRegions(RegionRepository.RegionRepositoryCallback callback) {
        useCaseHandler.getAvailableRegions(callback);
    }

    public void retrieveLaundriesByRegion(String region, LaundriesRepository.LaundriesRepositoryCallback callback) {
        useCaseHandler.getLaundriesByRegions(region, callback);
    }
}
