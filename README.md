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
  - For each application there is an option to add an event from including adding title, desctiption and the date of the event.
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
