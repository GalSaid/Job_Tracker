package com.example.jobtracker.Utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.Model.Job;
import com.example.jobtracker.Model.User;
import com.example.jobtracker.R;
import com.example.jobtracker.Views.ActivityRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class MyDbManager {

    private static Context context;
    private static volatile MyDbManager instance;
    private final String JOB_TABLE = "Jobs";
    private final String USERS_TABLE = "Users";


    private MyDbManager(Context context) {
        this.context = context;
    }

    public static MyDbManager getInstance() {
        return instance;
    }


    public static void init(Context context){
        if (instance == null){
            synchronized (ImageLoader.class){
                if (instance == null){
                    instance = new MyDbManager(context);
                }
            }
        }
    }

    public void createJobsAndLoadToDB(){
        HashMap<String,Job> jobs = new HashMap<>();
        jobs.put("j1",new Job(
                "Software Developer",
             "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FTech%20Innovations.png?alt=media&token=9a42a9c0-e266-43b7-bfbc-0a9acd28390a",
                "Tech Innovations",
                "galsaid123@gmail.com",
                true,
                false,
                "As a Software Developer at Tech Innovations, you'll be responsible for designing, developing, and maintaining robust and scalable web applications. Your role involves writing clean, efficient code, collaborating with cross-functional teams to deliver high-quality software, and troubleshooting issues as they arise. You'll work closely with product managers and designers to create user-friendly solutions that meet our client's needs and business objectives.",
                "Tech Innovations is a forward-thinking technology company dedicated to delivering cutting-edge software solutions. We pride ourselves on fostering an innovative environment where our team can grow and excel. Our mission is to enhance business operations through our advanced technology and exceptional service.",
                "To be successful in this role, you should have a strong proficiency in Java and experience with the Spring Boot framework. We are looking for candidates with excellent problem-solving skills, the ability to work in a fast-paced environment, and a solid understanding of software development best practices. A bachelor’s degree in Computer Science or a related field is preferred.",
                "Holon, Israel",
               "25/07/2024").setId("j1"));
        jobs.put("j2", new Job(
                "Digital Marketing Specialist",
                "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FCreative%20Marketing.jpg?alt=media&token=c1cd698e-2739-4baf-895b-9bc5e2f7c670",
                "Creative Marketing",
                "galisaid123@gmail.com",
                true,
                false,
                "In the role of Digital Marketing Specialist at Creative Marketing, you will be tasked with developing and executing comprehensive online marketing strategies to boost brand visibility and drive lead generation. You will manage digital campaigns, analyze performance metrics, and optimize strategies to achieve our marketing goals. Your responsibilities will also include creating engaging content, managing social media accounts, and coordinating with other marketing professionals.",
                "Creative Marketing excels in crafting innovative digital strategies to elevate brand presence and growth. Our team is passionate about creativity and driven by results, always aiming to push the boundaries of digital marketing to deliver exceptional outcomes for our clients.",
                "We require candidates to have a solid understanding of SEO, Google Ads, and content creation. You should possess at least 3 years of experience in digital marketing, with a proven track record of managing successful campaigns. Strong analytical skills, creativity, and the ability to work independently are essential.",
                "Tel Aviv , Israel",
               "15/07/2024")
        .setId("j2"));

        jobs.put("j3", new Job(
                "Data Analyst",
           "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FIntel.png?alt=media&token=aa1d5fa3-ba2a-4489-9e07-cd4954c3ecb3",
                "Intel",
                "galisaid123@gmail.com",
                true,
                true,
                "As a Data Analyst at Intel, you will be responsible for analyzing large and complex data sets to provide actionable insights that support strategic decision-making. Your role involves interpreting data, creating reports, and presenting findings to stakeholders. You will use various analytical tools and methodologies to identify trends, patterns, and anomalies, ultimately helping the company make data-driven decisions.",
                "Intel delivers data-driven strategies to optimize business performance and decision-making. We value innovation and excellence in all aspects of our work, aiming to be a leader in the data analysis field.",
                "The ideal candidate will have strong proficiency in SQL and experience with data visualization tools such as Tableau or Power BI. You should have excellent analytical and problem-solving skills, along with the ability to translate complex data into clear and actionable insights. Prior experience in a similar role is required.",
                "Austin, TX",
              "30/06/2024"
        ).setId("j3"));

        jobs.put("j4", new Job(
                "Project Manager",
             "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FGlobal%20Ventures.png?alt=media&token=20cacccd-f7f8-4ad1-8810-e1bcbb860efa",
                "Global Ventures",
                "galisaid123@gmail.com",
                true,
                false,
                "In the Project Manager role at Global Ventures, you will lead project teams to ensure the successful planning, execution, and delivery of projects within scope, time, and budget constraints. You will be responsible for developing project plans, managing resources, and communicating progress to stakeholders. Your role will involve risk management, problem-solving, and ensuring that project objectives align with business goals.",
                "Global Ventures is a leading firm in project management, dedicated to delivering complex projects that drive innovation and success. We pride ourselves on our ability to manage large-scale projects efficiently, working collaboratively with clients to achieve their strategic objectives. Our team is committed to excellence and continuous improvement.",
                "Candidates should have a proven track record in project management, with strong leadership and organizational skills. Proficiency in project management software is required, along with the ability to manage multiple projects simultaneously. A PMP certification or similar qualification is preferred.",
                "Chicago, IL",
                "01/08/2024"
        ).setId("j4"));

        jobs.put("j5", new Job(
                "UX Designer",
               "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FDesign%20Wizards.jpg?alt=media&token=0ea35f88-31e7-43b7-aa00-4833d3afbd36",
                "Design Wizards",
                "galisaid123@gmail.com",
                true,
                true,
                "As a UX Designer at Design Wizards, you will be responsible for creating intuitive and engaging user experiences for web and mobile applications. Your role involves conducting user research, developing wireframes and prototypes, and working closely with developers to implement designs. You will ensure that user interactions are seamless and that the final product meets the highest standards of usability and aesthetics.",
                "Design Wizards focuses on delivering exceptional user experiences through innovative design and research. Our team is driven by creativity and a commitment to solving design challenges in unique and effective ways.",
                "We are looking for candidates with experience in wireframing, prototyping, and conducting user research. Proficiency in design tools such as Sketch or Figma is essential. A strong portfolio demonstrating your design process and outcomes is required, along with excellent problem-solving skills.",
                "San Francisco, CA",
                "15/09/2024"
        ).setId("j5"));

        jobs.put("j6", new Job(
                "HR Manager",
               "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FPeopleFirst%20Solutions.png?alt=media&token=b56bf5a2-5845-4a77-8bfe-8e6e68ade37f",
                "PeopleFirst Solutions",
                "galisaid123@gmail.com",
                true,
                false,
                "As an HR Manager at PeopleFirst Solutions, you will oversee recruitment, employee relations, and performance management processes. You will be responsible for developing HR policies, managing employee benefits, and ensuring compliance with labor laws. Your role involves supporting staff development, handling conflicts, and fostering a positive work environment to enhance employee satisfaction and productivity.",
                "PeopleFirst Solutions is dedicated to providing comprehensive HR services that support both employees and organizational goals. We are committed to creating a supportive and engaging workplace where our team can thrive. Our focus is on driving employee success and organizational excellence through effective HR management.",
                "Candidates should have a strong understanding of HR practices and experience in recruitment and employee management. Excellent communication and interpersonal skills are essential, along with the ability to handle sensitive situations with professionalism. A degree in Human Resources or a related field is preferred.",
                "Boston, MA",
               "01/10/2024"
        ).setId("j6"));
        jobs.put("j7", new Job(
                "Cloud Solutions Architect",
               "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FRapid.png?alt=media&token=5616eb21-730d-4d83-a249-7fbc967b8866",
                "Rapid",
                "galisaid123@gmail.com",
                true,
                true,
                "As a Cloud Solutions Architect at Rapid, you will design and implement scalable cloud infrastructure solutions to meet the needs of our clients. Your responsibilities include assessing client requirements, developing cloud architectures, and overseeing the deployment of cloud-based applications. You will work closely with engineering teams to ensure seamless integration and optimal performance of cloud solutions.",
                "Rapid is a leading technology firm based in Israel, specializing in innovative cloud computing and IT solutions. We are dedicated to delivering cutting-edge technology to help businesses transform and thrive in a digital world. Our team is composed of highly skilled professionals who are passionate about leveraging technology to drive success.",
                "Candidates should have extensive experience with cloud platforms such as AWS, Azure, or Google Cloud. A strong background in designing and managing cloud architectures, excellent problem-solving skills, and the ability to work collaboratively with technical teams are essential. Relevant certifications and a degree in Computer Science or a related field are preferred.",
                "Tel Aviv, Israel",
              "01/09/2024"
        ).setId("j7"));
        jobs.put("j8", new Job(
                "AI Research Scientist",
                "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FInnovateAI%20Labs.png?alt=media&token=2e0014ce-4be5-43a7-a701-f0a0c7dd3a1e",
                "InnovateAI Labs",
                "galisaid123@gmail.com",
                true,
                true,
                "As an AI Research Scientist at InnovateAI Labs, you will conduct cutting-edge research in artificial intelligence and machine learning. Your responsibilities include developing novel algorithms, analyzing large data sets, and publishing research findings. You will collaborate with other scientists and engineers to integrate AI solutions into our products and contribute to the advancement of AI technologies.",
                "InnovateAI Labs is a pioneering research and development company based in Israel, focused on pushing the boundaries of artificial intelligence and machine learning. Our mission is to drive technological innovation and provide state-of-the-art AI solutions that transform industries and improve lives.",
                "Candidates should have a Ph.D. in Computer Science, AI, or a related field, with a strong background in machine learning and deep learning algorithms. Experience with AI frameworks like TensorFlow or PyTorch, and a track record of published research is essential. Strong analytical skills and the ability to work on complex problems are required.",
                "Haifa, Israel",
               "15/10/2024"
        ).setId("j8"));

        jobs.put("j9", new Job(
                "Blockchain Developer",
               "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FBlockChain%20Innovations.png?alt=media&token=d3bbd7d9-b710-40a7-9945-0913670c7fb2",
                "BlockChain Innovations",
                "galisaid123@gmail.com",
                true,
                true,
                "In the role of Blockchain Developer at BlockChain Innovations, you will design and implement blockchain-based solutions for various applications. Your tasks will include developing smart contracts, building decentralized applications (dApps), and ensuring the security and scalability of blockchain systems. You will work closely with other developers and stakeholders to deliver innovative blockchain solutions.",
                "BlockChain Innovations is a leading tech company in Israel specializing in blockchain technology. We are dedicated to advancing blockchain solutions to drive digital transformation and enhance security, transparency, and efficiency for our clients.",
                "We are looking for candidates with strong experience in blockchain development, particularly with Ethereum and Solidity. Proficiency in creating smart contracts, knowledge of cryptographic protocols, and the ability to develop secure and scalable dApps are essential. A background in Computer Science or a related field is preferred.",
                "Tel Aviv, Israel",
              "01/11/2024"
        ).setId("j9"));

        jobs.put("j10", new Job(
                "Cybersecurity Analyst",
                "https://firebasestorage.googleapis.com/v0/b/job-tracker-8b2b8.appspot.com/o/Companies%2FSecureTech%20Solutions.jpg?alt=media&token=57d1ca97-5365-48a9-94b2-6ea8e9a9c72b",
                "SecureTech Solutions",
                "galisaid123@gmail.com",
                true,
                true,
                "As a Cybersecurity Analyst at SecureTech Solutions, you will be responsible for identifying and mitigating security threats to our IT infrastructure. Your role includes monitoring network activity, conducting vulnerability assessments, and responding to security incidents. You will collaborate with IT teams to implement security measures and ensure the protection of sensitive data.",
                "SecureTech Solutions is a premier cybersecurity firm based in Israel, focused on providing comprehensive security solutions to protect businesses from cyber threats. We are committed to safeguarding digital assets and ensuring the highest level of security for our clients through innovative and effective cybersecurity practices.",
                "Candidates should have a solid understanding of cybersecurity principles, experience with security tools and technologies, and the ability to analyze and respond to security incidents. Relevant certifications such as CISSP or CEH, along with a degree in Cybersecurity or a related field, are preferred.",
                "Jerusalem, Israel",
              "15/12/2024"
        ).setId("j10"));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference(JOB_TABLE);
        jobsRef.setValue(jobs);
    }

    public void updateJobCompanyImage(String jobId,String imageUrl, CallBack callBack) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(JOB_TABLE);

        usersRef.child(jobId).child("companyImg").setValue(imageUrl)
                .addOnSuccessListener(unused -> {
                    if(callBack != null)
                        callBack.res(null);
                });
    }



    public ArrayList<AppEvent> convertEventsHashMapToArrayList(HashMap<String, AppEvent> hashMap){
        return new ArrayList<>(hashMap.values());
    }

    public interface CallBack<T> {
        void res(T res);
    }

    public interface CallBackMove {
        void res();
    }




//    public void getUserImage(CallBack<String> callBack) {
//        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        getUserImage(userUid, callBack);
//    }

    public void getJobCompanyImage(String jobId, CallBack<String> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(JOB_TABLE);

        usersRef.child(jobId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Job job = snapshot.getValue(Job.class);

                if (job != null) {
                    callBack.res(job.getCompanyImg());
                } else {
                    callBack.res(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getPdfCVName(CallBack<String> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usersRef.child(userUid).child("pdfCV").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String pdf_cv= snapshot.getValue(String.class);
                    if(pdf_cv!=null)
                        callBack.res(StorageManager.getInstance().getFileName(Uri.parse(pdf_cv)));
                    else
                        callBack.res(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getWordCVName(CallBack<String> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usersRef.child(userUid).child("wordCV").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String word_cv= snapshot.getValue(String.class);
                if(word_cv!=null)
                    callBack.res(StorageManager.getInstance().getFileName(Uri.parse(word_cv)));
                else
                    callBack.res(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void getAllJobs(CallBack<ArrayList<Job>> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference(JOB_TABLE);
        jobsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting jobs", task.getException());
                    callBack.res(new ArrayList<>()); // Handle error by sending an empty list
                } else {
                    ArrayList<Job> jobs = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Job job = snapshot.getValue(Job.class);
                        if(job!=null && job.isActive())
                            jobs.add(job);
                    }
                    callBack.res(jobs);
                }
            }
        });
    }

//    public void getAllApplications(CallBack<ArrayList<Application>> callBack) {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference usersRef = database.getReference(USERS_TABLE);
//        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        usersRef.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//
//                if (user != null) {
//                    ArrayList<Application> apps = new ArrayList<>();
//                    CallBack<Application> callBackApp=new CallBack<Application>() {
//                        @Override
//                        public void res(Application res) {
//                            apps.add(res);
//                        }
//                    };
//                   for(String appId : user.getMyApplications().keySet()){
//                       getApplication(appId, callBackApp);
//                   }
//                    callBack.res(apps);
//                } else {
//                    callBack.res(null);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    public void getAllApplications(CallBack<ArrayList<Application>> callBack) {

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(USERS_TABLE).child(userUid).child("myApplications");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Application> applications = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Application app = snapshot.getValue(Application.class);
                    applications.add(app);
                }
                callBack.res(applications); // Pass the retrieved list to the callback
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to retrieve applications: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addApplication(String userId, Application app, CallBackMove callBack) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);
        usersRef.child(userId).child("myApplications").child(app.getJobId()).setValue(app)
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add application",Toast.LENGTH_SHORT).show();
                }).addOnSuccessListener(unused -> {
                    callBack.res();
                });
    }

    public void updateApplication(Application app, OnAppUpdateListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);
        usersRef.child(userUid).child("myApplications").child(app.getJobId()).setValue(app).addOnSuccessListener(
                unused -> {
                    listener.onSuccess();
                }
                )
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to update application",Toast.LENGTH_SHORT).show();
                });
    }

    public interface OnAppUpdateListener {
        void onSuccess();
    }

    public void addOrUpdateEvent(Application app, AppEvent event){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);
        usersRef.child(userUid).child("myApplications").child(app.getJobId()).child("allEvents").child(event.getId()).setValue(event)
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add event",Toast.LENGTH_SHORT).show();
                });

    }


//    public void getApplication(String appId, CallBack<Application> callBack) {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference appsRef = database.getReference(APPLICATION_TABLE);
//        appsRef.child(appId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Application app = snapshot.getValue(Application.class);
//
//                if (app != null) {
//                    callBack.res(app);
//                } else {
//                    callBack.res(null);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void getUser(CallBack<User> callBack) {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(USERS_TABLE);

        usersRef.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                callBack.res(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSpecificJob(String jobId, CallBack<Job> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference(JOB_TABLE);

        jobsRef.child(jobId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Job job = snapshot.getValue(Job.class);

                if (job != null) {
                    callBack.res(job);
                }
                else{
                    Toast.makeText(context, "There is no job "+jobId,Toast.LENGTH_SHORT).show();
                    callBack.res(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getJobStatus(String jobId, CallBack<Boolean> callBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference jobsRef = database.getReference(JOB_TABLE).child(jobId);

        jobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean active = snapshot.child("active").getValue(boolean.class);
                callBack.res(active);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "There is no job "+jobId,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
