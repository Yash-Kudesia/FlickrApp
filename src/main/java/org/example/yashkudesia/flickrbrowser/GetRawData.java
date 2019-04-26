package org.example.yashkudesia.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, NOT_INITIALIZED, PROCESSING, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    DownloadStatus downloadStatus;
    private OnDownloadCompelete onDownloadCompelete;

    public GetRawData(OnDownloadCompelete mainActivity) {
        this.downloadStatus = DownloadStatus.IDLE;
        this.onDownloadCompelete = mainActivity;
    }

    public void runOnSameThread(String s) {
        Log.d(TAG, "runOnSameThread: starts");
        onPostExecute(doInBackground(s));
        Log.d(TAG, "runOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {
        //Log.d(TAG, "onPostExecute: Parameter-:"+s);
        if (onDownloadCompelete != null) {
            onDownloadCompelete.OnDownloadCompelete(s, downloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if (strings == null) {
            downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }
        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response Code-:" + responseCode);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }

            downloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Execption-:" + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception ,Permissions?;-" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: IO Exception-:" + e.getMessage());
                }
            }
        }
        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

    interface OnDownloadCompelete {
        void OnDownloadCompelete(String s, DownloadStatus status);
    }
}
