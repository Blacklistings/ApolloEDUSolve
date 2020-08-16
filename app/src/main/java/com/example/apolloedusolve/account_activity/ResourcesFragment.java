package com.example.apolloedusolve.account_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apolloedusolve.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ResourcesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resources, container, false);
        return root;
    }
}