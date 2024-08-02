package com.example.jobtracker.Utilities;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;



public class FBmanager {
    private DatabaseReference ref;
    private FirebaseDatabase firebaseRTDatabase;
    private FirebaseUser user;
    //private ArrayList<Kid> kids;

//    public FBmanager(FirebaseUser user) {
//        this.firebaseRTDatabase = FirebaseDatabase.getInstance();
//        this.user = user;
//        this.kids = new ArrayList<>();
//        ref = firebaseRTDatabase.getReference(this.user.getUid());
//    }
//
//    public ArrayList<Kid> getKids() {
//        return kids;
//    }
//
//    public void LoadDataFromFBRTDB(DataLoadCallback callback) {
//        DatabaseReference myRef = firebaseRTDatabase.getReference(this.user.getUid());
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot kidSnapshot : dataSnapshot.getChildren()) {
//                    Kid myKid = buildKidBasicInfo(kidSnapshot);
//                    Map<String, ImmunizationRecord> myIR = (Map<String, ImmunizationRecord>) kidSnapshot.child("Immunizations").getValue();
//                    myKid.setImmunizationRecords(myKid.convertIRtoArrayList(myIR));
//                    Map<String, KidEvent> myKE = (Map<String, KidEvent>) kidSnapshot.child("Events").getValue();
//                    myKid.setEvents(myKid.convertKEtoArrayList(myKE));
//                    Map<String, String> photosUri = (Map<String, String>) kidSnapshot.child("photosUri").getValue();
//                    myKid.setPhotosUri(myKid.convertMapToMyPhotoArrayList(photosUri));
//                    kids.add(myKid);
//
//                }
//                callback.onDataLoaded();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle errors
//                Log.w("RecipeData", "Failed to read value.", databaseError.toException());
//            }
//        });
//    }
//
//    private Kid buildKidBasicInfo(DataSnapshot kidSnapshot) {
//        String kidId = kidSnapshot.getKey();
//        String fName = kidSnapshot.child("name").child("fName").getValue(String.class);
//        String lName = kidSnapshot.child("name").child("lName").getValue(String.class);
//        int day = kidSnapshot.child("birthDate").child("day").getValue(Integer.class);
//        int month = kidSnapshot.child("birthDate").child("month").getValue(Integer.class);
//        int year = kidSnapshot.child("birthDate").child("year").getValue(Integer.class);
//        int age = kidSnapshot.child("age").getValue(Integer.class);
//        String profilePhotoUri = kidSnapshot.child("profilePhotoUri").getValue(String.class);
//        if (profilePhotoUri == null)
//            profilePhotoUri = "";
//        return new Kid(kidId).setAge(age).setBirthDate(day, month, year).setfName(fName).setlName(lName).setProfilePhotoUri(Uri.parse(profilePhotoUri));
//    }
//
//    public void addKidToDB(Kid kid) {
//        addKidBaseInfoToDB(kid);
//        addKidIRsToDB(kid);
//        addKidEventsToDB(kid);
//    }
//
//    public void addImmunizationRecordToDB(ImmunizationRecord iR, Kid kid) {
//        ref.child(kid.getkId() + "").child("Immunizations").child(iR.getIrID()).setValue(iR);
//    }
//
//    public void addKidEventToDB(KidEvent kEvent, Kid kid) {
//        ref.child(kid.getkId() + "").child("Events").child(kEvent.geteId()).setValue(kEvent);
//    }
//
//    public void addPhotoUriToDB(Uri uri, Kid kid) {
//        ref.child(kid.getkId() + "").child("photosUri").child(UUID.randomUUID().toString()).setValue(uri.toString());
//        int x=10;
//    }
//
//
//    private void addKidIRsToDB(Kid kid) {
//        ref.child(kid.getkId() + "").child("Immunizations").setValue(kid.getIRMap());
//    }
//
//    private void addKidEventsToDB(Kid kid) {
//        ref.child(kid.getkId() + "").child("Events").setValue(kid.getKEMap());
//    }
//    private void addKidBaseInfoToDB(Kid kid) {
//        ref.child(kid.getkId() + "").child("name").child("fName").setValue(kid.getfName());
//        ref.child(kid.getkId() + "").child("name").child("lName").setValue(kid.getlName());
//        ref.child(kid.getkId() + "").child("birthDate").setValue(kid.getBirthDate());
//        ref.child(kid.getkId() + "").child("age").setValue(kid.getAge());
//        if (kid.getPhotosUri() != null)
//            ref.child(kid.getkId() + "").child("photosUri").setValue(kid.getPhotosUriMap());
//        int x = 10;
//        if (kid.getProfilePhotoUri() != null)
//            ref.child(kid.getkId() + "").child("profilePhotoUri").setValue(kid.getProfilePhotoUri().toString());
//    }
//    public void removeImmunizationRecordFB(ImmunizationRecord iR, Kid kid) {
//        ref.child(kid.getkId()).child("Immunizations").child(iR.getIrID()).removeValue();
//    }
//    public void removeKidEventFB(KidEvent kEvent, Kid kid) {
//        ref.child(kid.getkId() + "").child("Events").child(kEvent.geteId()).removeValue();
//    }
//    public void removeKidFromDB(String kId) {
//        ref.child(kId).removeValue();
//    }
//
//    public void setProfilePhotoUriToDB(Uri uri, Kid kid) {
//        ref.child(kid.getkId() + "").child("profilePhotoUri").setValue(uri.toString());
//    }
//    public void removePhotoUriFB(MyPhoto photo, Kid myKid) {
//        ref.child(myKid.getkId() + "").child("photosUri").child(photo.getpId()).removeValue();
//    }
//    public void deleteUser() {
//        ref.removeValue();
//    }
}