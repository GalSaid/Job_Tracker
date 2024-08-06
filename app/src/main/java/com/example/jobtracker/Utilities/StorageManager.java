package com.example.jobtracker.Utilities;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

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
        this.storageReference= FirebaseStorage.getInstance().getReference();
        this.databaseReference= FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public static StorageManager getInstance() {
        return instance;
    }


    public static void init(Context context){
        if (instance == null){
            synchronized (ImageLoader.class){
                if (instance == null){
                    instance = new StorageManager(context);
                }
            }
        }
    }

    public void uploadPdfCVToFB(Uri data){
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MyDbManager.getInstance().getPdfCVName(filename->{
           String pdfFileName=filename;
            StorageReference reference= storageReference.child(userUid+"/"+getFileName(data));
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri uri= uriTask.getResult();
                    databaseReference.child(userUid).child("pdfCV").setValue(uri.toString());
                    if(pdfFileName!=null){
                        StorageReference ref= storageReference.child(userUid+"/"+pdfFileName);
                        ref.delete();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed to upload pdf file to storage",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void uploadWordCVToFB(Uri data){
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MyDbManager.getInstance().getWordCVName(filename->{
            String wordFileName=filename;
            StorageReference reference= storageReference.child(userUid+"/"+getFileName(data));
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri uri= uriTask.getResult();
                    databaseReference.child(userUid).child("wordCV").setValue(uri.toString());
                    if(wordFileName!=null){
                        StorageReference ref= storageReference.child(userUid+"/"+wordFileName);
                        ref.delete();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed to upload word file to storage",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
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

    public void downloadFile(String fileUrl, FileDownloadCallback callback) {
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
                    if (callback != null) {
                        Toast.makeText(context, "Failed to download the file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}


