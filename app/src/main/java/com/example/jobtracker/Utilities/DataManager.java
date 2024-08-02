package com.example.jobtracker.Utilities;

import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.Model.Application;
import com.example.jobtracker.Model.Job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DataManager {

    public static ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<>();

        jobs.add(new Job().setName("Product Manager for POS/Restaurant/Kiosk Technology Systems 9069").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setLocation("Tel Aviv").setImg_company("https://www.janglo.net/assets/images/logos/logoonbright.svg"));
        jobs.add(new Job().setName("Product Manager").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setLocation("Tel Aviv").setImg_company("https://www.janglo.net/assets/images/logos/logoonbright.svg"));
        jobs.add(new Job().setName("Product Manager for POS/Restaurant/Kiosk Technology Systems 9069").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setLocation("Tel Aviv").setImg_company("https://www.janglo.net/assets/images/logos/logoonbright.svg"));
        jobs.add(new Job().setName("Product Manajjkbkgjkgbjkhbklvf fdkjldgjmvfr fddkgvkfd  kvfldv v;dslgv ger for POS/Restaurant/Kiosk Technology Systems 9069").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setLocation("Tel Aviv").setImg_company("https://www.janglo.net/assets/images/logos/logoonbright.svg"));
        jobs.add(new Job().setName("Product Manager for POS/Restaurant/Kiosk Technology Systems 9069").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setLocation("Tel Aviv").setImg_company("https://www.janglo.net/assets/images/logos/logoonbright.svg"));

        return jobs;
    }

    public static ArrayList<Application> getMyApplications() {
        ArrayList<AppEvent> events=new ArrayList<>();
//        events.add(new AppEvent().setTitle("The hr returned to me").setDescription("jfjfjf dfdfjf fdhfbf dbjfjdfdffdf\ndjjfdjfjkfdkjfd").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
//        events.add(new AppEvent().setTitle("The hr returned to me").setDescription("jfjfjf dfdfjf fdhfbf dbjfjdfdffdf\ndjjfdjfjkfdkjfd").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
//        events.add(new AppEvent().setTitle("The hr returned to me").setDescription("jfjfjf dfdfjf fdhfbf dbjfjdfdffdf\ndjjfdjfjkfdkjfd").setDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            ArrayList<Application> apps = new ArrayList<>();
//
//        apps.add(new Application().setTitle("Software developer").setReturned(true).setEventList(events));
//        apps.add(new Application().setTitle("Product Manager").setReturned(true).setEventList(events));
//        apps.add(new Application().setTitle("Data Analyst").setReturned(true).setEventList(events));
//        apps.add(new Application().setTitle("Noc FFKF FGKGKG").setReturned(true).setEventList(events));
//        apps.add(new Application().setTitle("IT support").setReturned(true).setEventList(events));
        return apps;
    }
}
