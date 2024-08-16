package com.example.jobtracker.Utilities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StorageManager {
    private static Context context;
    private static volatile StorageManager instance;
    private final String JOB_TABLE = "Jobs";
    private final String USERS_TABLE = "Users";
    private final String APPLICATION_TABLE = "Apps";
    private StorageReference storageReference;
    private DatabaseReference databaseReference;


    private StorageManager(Context context) {
        this.context = context;
        this.storageReference = FirebaseStorage.getInstance().getReference();
        this.databaseReference = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public static StorageManager getInstance() {
        return instance;
    }


    public static void init(Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new StorageManager(context);
                }
            }
        }
    }

    public void uploadPdfCVToFB(Uri data, GetNewUrlStringCallback callback) {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MyDbManager.getInstance().getPdfCVName(filename -> {
            String pdfFileName = filename;
            StorageReference reference = storageReference.child(userUid + "/" + getFileName(data));
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri uri = uriTask.getResult();
                    databaseReference.child(userUid).child("pdfCV").setValue(uri.toString());
                    if (callback != null)
                        callback.res(uri.toString());
                    if (pdfFileName != null) { //if the user has already uploaded a pdf file, delete it from the storage
                        StorageReference ref = storageReference.child(userUid + "/" + pdfFileName);
                        ref.delete();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Failed to upload pdf file to storage", Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });
    }

    public void uploadPdfCVToFB(Uri data) {
        uploadPdfCVToFB(data, null);
    }

    public void uploadWordCVToFB(Uri data) {
        uploadWordCVToFB(data, null);
    }

    public void uploadWordCVToFB(Uri data, GetNewUrlStringCallback callback) {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MyDbManager.getInstance().getWordCVName(filename -> {
            String wordFileName = filename;
            StorageReference reference = storageReference.child(userUid + "/" + getFileName(data));
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri uri = uriTask.getResult();
                    databaseReference.child(userUid).child("wordCV").setValue(uri.toString());
                    if (callback != null)
                        callback.res(uri.toString());
                    if (wordFileName != null) { //if the user has already uploaded a word file, delete it from the storage
                        StorageReference ref = storageReference.child(userUid + "/" + wordFileName);
                        ref.delete();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Ensure Toast is shown on the main thread
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Failed to upload word file to storage", Toast.LENGTH_SHORT).show()
                    );
                }
            });
        });
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) { //get the name of the file from the uri
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public interface FileDownloadCallback {
        void onSuccess(Uri fileUri);
    }

    public interface GetNewUrlStringCallback {
        void res(String url);
    }

    public void downloadFile(String fileUrl, FileDownloadCallback callback) { //download the file from the remote url
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fileUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    File file = new File(context.getExternalFilesDir(null), getFileName(Uri.parse(fileUrl)));
                    try (InputStream inputStream = response.body().byteStream();
                         OutputStream outputStream = new FileOutputStream(file)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        if (callback != null) {
                            Uri fileUri = FileProvider.getUriForFile(context, "com.example.jobtracker.fileprovider", file);
                            callback.onSuccess(fileUri);
                        }
                    }
                } else {
                    callback.onSuccess(null);
                }
            }
        });
    }

    public void uploadWordCV(ActivityResultLauncher<Intent> launcher) { //choose which word file to upload
        Intent intent = new Intent();
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Select WORD"));
    }


    public void uploadPdfCV(ActivityResultLauncher<Intent> launcher) { //choose which pdf file to upload
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Select PDF"));
    }

}


