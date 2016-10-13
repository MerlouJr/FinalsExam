package com.usjr.finalsexam.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.usjr.finalsexam.R;
import com.usjr.finalsexam.adapters.VideoListAdapter;
import com.usjr.finalsexam.controller.VideosController;
import com.usjr.finalsexam.db.VideoTable;
import com.usjr.finalsexam.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private VideoListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    private ListView listView;
    private VideosController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mController = new VideosController();
        mAdapter = new VideoListAdapter(this, new ArrayList<Video>());
        listView.setAdapter(mAdapter);

        prepareData();
        displayListOfVideos();
        new DelayTask().execute();

    }
    private void prepareData() {
        // In order to avoid duplication of data, I remove all video list item from the database
        // first and then add new items into the database. Sample test data are present in
        // VideosController.getVideos() method.
        VideoTable.getAllVideos(getApplicationContext());
        List<Video> videos = mController.getVideos();

        for (Video vid : videos) {
            VideoTable.insertVideo(this, vid);
        }
    }

    public class DelayTask extends AsyncTask<Void, Integer, String> {
        int count = 0;

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            while (count < 5) {
                SystemClock.sleep(1000);
                count++;
                publishProgress(count * 20);
            }
            return "(^_^)";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }
    }

    private void displayListOfVideos() {
        // TODO: Implement this method
        VideoListAdapter adapter = new VideoListAdapter(getApplicationContext(), mController.getVideos());
        listView.setAdapter(adapter);
    }

   public void showProgressBar(){
       // TODO: Implement this method
            mProgressBar.setVisibility(View.VISIBLE);
   }

    public void hideProgressBar() {
        // TODO: Implement this method
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PlayVideoActivity.class);
        startActivity(intent);
    }
}
