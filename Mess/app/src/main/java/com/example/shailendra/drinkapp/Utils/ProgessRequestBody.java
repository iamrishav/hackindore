package com.example.shailendra.drinkapp.Utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by shailendra on 6/17/2018.
 */

//For Avatar upload

public class ProgessRequestBody extends RequestBody {

    private File file;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private UploadCallBack listener;


    public ProgessRequestBody(File file, UploadCallBack listener) {
        this.file = file;
        this.listener = listener;
    }

    //click generate -> overide methods -> choose 2nd option
    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;
        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());  //import os.handler
            while ((read = in.read(buffer)) != -1) {
                handler.post(new ProgressUpdater(uploaded, fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            {
                in.close();
            }
        }

    }

    //create inner class of ProgressUpdater
    private class ProgressUpdater implements Runnable {
        private long uploaded , fileLength;
        public ProgressUpdater(long uploaded, long fileLength) {
            this.fileLength = fileLength;
            this.uploaded = uploaded;

        }
//implement method ProgressUpdater
        @Override
        public void run() {

            listener.onProgressUpdate((int)(100*uploaded/fileLength));
        }
    }
}
