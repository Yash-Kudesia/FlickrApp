package org.example.yashkudesia.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadCompelete {
    private static final String TAG = "GetFlickrJsonData";
    private final OnDataAvailable onDataAvailable;
    private List<Photo> photoList = null;
    private String baseUrl;
    private String language;
    private boolean matchAll;
    private boolean runningOnSameThread = false;

    public GetFlickrJsonData(String baseUrl, String language, boolean matchAll, OnDataAvailable onDataAvailable) {
        Log.d(TAG, "GetFlickrJsonData called");
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.onDataAvailable = onDataAvailable;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {

        Log.d(TAG, "onPostExecute: starts");
        if (onDataAvailable != null) {
            onDataAvailable.onDataAvailable(photoList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected List<Photo> doInBackground(String... param) {
        Log.d(TAG, "doInBackground: starts");
        String destinationURL = createUrl(param[0], language, matchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runOnSameThread(destinationURL);
        Log.d(TAG, "doInBackground: ends");
        return null;
    }

    void executeOnsameThread(String searchCriteria) {
        Log.d(TAG, "executeOnsameThread starts");
        runningOnSameThread = true;
        String destinationURL = createUrl(searchCriteria, language, matchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationURL);
        Log.d(TAG, "executeOnsameThread: Ends");
    }

    String createUrl(String searchCriteria, String lang, boolean matchAll) {
        Log.d(TAG, "createUrl: starts");
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void OnDownload(String s, DownloadStatus status) {
        Log.d(TAG, "OnDownloadCompelete: Status-:" + status);
        if (status == DownloadStatus.OK) {
            photoList = new ArrayList<>();
            try {
                JSONObject jsonData = new JSONObject(s);
                JSONArray itemarray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemarray.length(); i++) {
                    JSONObject jsonObject = itemarray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String author = jsonObject.getString("author");
                    String author_id = jsonObject.getString("author_id");
                    String tags = jsonObject.getString("tags");

                    JSONObject jsonMedia = jsonObject.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b."); //b->large and m->small

                    Photo photoObject = new Photo(title, author_id, author, link, tags, photoUrl);
                    photoList.add(photoObject);
                    Log.d(TAG, "OnDownloadCompelete: " + photoObject.toString());
                }

            } catch (JSONException e) {
                Log.e(TAG, "OnDownloadCompelete: Error-:" + e.getMessage());
                e.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runningOnSameThread && onDataAvailable != null) {
            onDataAvailable.onDataAvailable(photoList, status);
        }
        Log.d(TAG, "OnDownloadCompelete: ends");
    }

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }
}
