package com.example.choreapplication.fragments;

// In the WeekStatistics class, I have used ChatGPT to help in fetchData()-method. Lines 79-103.

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choreapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class WeekStatistics extends Fragment {
    private RecyclerView weekRV;
    private ArrayList<Statistics> statistics;
    private StatisticsAdapter adapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String familyCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_statistics, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        weekRV = view.findViewById(R.id.weekRV);
        weekRV.setLayoutManager(new LinearLayoutManager(getContext()));
        statistics = new ArrayList<>();
        adapter = new StatisticsAdapter(statistics);
        weekRV.setAdapter(adapter);

        fetchFamilyCode();

        return view;
    }

    public void fetchFamilyCode() {
        String uid = auth.getCurrentUser().getUid();

        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    familyCode = documentSnapshot.getString("familyCode");
                    fetchData();
                });
    }

    public void fetchData() {

        TimeZone helsinki = TimeZone.getTimeZone("Europe/Helsinki");
        Calendar calendar = Calendar.getInstance(helsinki);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date weekAgo = calendar.getTime();

        firestore.collection("documentedChores")
                .whereEqualTo("familyCode", familyCode)
                .whereGreaterThanOrEqualTo("timestamp", weekAgo)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    HashMap<String, int[]> userData = new HashMap<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String user = document.getString("user");
                        Long longScore = document.getLong("score");
                        Long longMinutes = document.getLong("time");

                        int scores = longScore.intValue();
                        int minutes = longMinutes.intValue();

                        if(!userData.containsKey(user)){
                            userData.put(user, new int[]{0,0});
                        }

                        int[] data = userData.get(user);
                        data[0] += scores;
                        data[1] += minutes;
                    }

                    statistics.clear();
                    for(Map.Entry<String, int[]>entry : userData.entrySet()) {
                        statistics.add(new Statistics(
                                entry.getKey(),
                                entry.getValue()[0],
                                entry.getValue()[1]
                        ));
                    }
                    Collections.sort(statistics, (a, b) -> Integer.compare(b.getTotalScore(), a.getTotalScore()));

                    adapter.notifyDataSetChanged();
                });
    }
}
