package com.netfricks.wafflepirate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by blainefricks on 11/17/16.
 */

public class DrivingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflator.inflate(R.layout.fragment_driving_settings, container, false);
    }

}
