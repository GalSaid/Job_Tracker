<img src="https://github.com/user-attachments/assets/d755b9dd-e2b5-4566-90d9-c49247aea281" width="500"/>


# Job-Tracker-App
An Android app that lets you manage and track job applications efficiently, including status updates and event tracking. It provides a platform for job seekers to document and monitor their job search process. The app was created to help job seekers stay organized and in control of their applications.

#  Features
* **Upload a resume**
  - one PDF and the other Word.
  - The application requires at least 1.
* **A job board with the option to submit jobs**
  - only if the position is open and you have not applied for this position yet.
* **Job submission**
  - an email template will open that includes a description that you entered beforehand that can be edited.
  - If you are connected to Gmail as soon as you click send it will open in Gmail with the attached file. If you don't have Gmail it will open in Outlook, if there is no Outlook it will give the user the option to choose which application to open.
  - If you have 2 CVs in the application, you can choose which CV to submit.
* **Watching and editing my application** including the status and events I added.
  - For each application there is an option to add/edit an event from including adding title, desctiption and the date of the event.
  - For each application status update whether they got back to me/didn't.
  - For each application additonal status of: pending, rejected, accepted and in process.
* **Analysis**
  - Pie chart for analyzing my status in the jobs I submitted.

<img src="https://github.com/user-attachments/assets/092243ac-3a55-4364-9601-cd40852de533" width="200"/>

Using Firebase for the purpose of:
* Realtime DB
  - Stores user information
  - My applications
  - All the jobs.
  - The data for analysis.
* authentication
  - Custom Authentication System.
  - Reset the password
  - Register and login by email and password.
* Storage
  - Stores pdf and word resumes.
  - Stores the logo images of the companies.

<br></br>

# Application Flow
**1. Register/ Login:**
<p>
      <img src="https://github.com/user-attachments/assets/ab70ab55-acae-426f-90ab-712744dea9a7" width="150">
      &nbsp;&nbsp;&nbsp;
      <img src="https://github.com/user-attachments/assets/cd3750cb-1783-455b-9da0-1d14e7591e79" width="150" >
</p>
     Note: In the registration screen there is a Register button below.

**2. Job board:**
   
   <img src="https://github.com/user-attachments/assets/1c2aa422-1c82-4dda-89a9-7a0ef84272d1" width="150">
   
**3. Viewing the job** by clicking on it in the board:
   <p>
      <img src="https://github.com/user-attachments/assets/ba9250a9-4ca3-405c-8b5d-8cdb487652d4" width="150">
         &nbsp;&nbsp;&nbsp;
      <img src="https://github.com/user-attachments/assets/ecba84a1-9b92-4ff5-8872-bb1dd274fbc9" width="150" >
  </p>

**4. Apply to job:**
   
  <img src="https://github.com/user-attachments/assets/cd042a0f-dd9d-458b-ae9c-656840d82e91" width="150">
      
**6. My applications:** After you submit an application you go automatic to this screen.

   <img src="https://github.com/user-attachments/assets/18a7420e-7d6b-4254-b9a2-31fdc0cdeb99" width="150">
         
Note: You can navigate to this screen also from the menu.

**7. Events:** you can add/edit events for specific application.
   
  <img src="https://github.com/user-attachments/assets/98b0f760-d078-424c-8983-c34a902fb9d8" width="150">

## Additional screens

**Analysis**

  <img src="https://github.com/user-attachments/assets/686e7370-b774-42da-89e4-efc38e226214" width="150">
  
**Viewing/updating the profile**

  <img src="https://github.com/user-attachments/assets/a082bab6-a8f0-47c0-9e54-89f32ba50289" width="150">



