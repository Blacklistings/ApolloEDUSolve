package com.example.apolloedusolve.account_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.apolloedusolve.R;

public class HomeFragment extends Fragment {
    private TextView nameText;
    private String name;

    public HomeFragment(String name){
        this.name = name;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        nameText = root.findViewById(R.id.home_name);
        nameText.setText(name);
        return root;
    }
}