package com.personal.covidtracker.service;

import com.personal.covidtracker.model.Statistics;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CasesService {

    private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<Statistics> currentStats = new ArrayList<>();
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public List<Statistics> getCurrentStats() {
        return currentStats;
    }

    // fetchData() gets the daily COVID data from the updated raw CSV URL by sending a request and prints it out.
    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *") // schedule this method to execute every day at 12:00 AM
    public void fetchData() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Statistics> newDailyStats = new ArrayList<>();

        // parsing the CSV information from a reader:
        StringReader in = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for(CSVRecord record : records) {
            Statistics stat = new Statistics();
            int todayCases = Integer.parseInt(record.get(record.size() - 1));
            int yesterdayCases = Integer.parseInt(record.get(record.size() - 2));
            stat.setCountry(record.get("Country/Region"));
            stat.setProvince(record.get("Province/State"));
            stat.setCases(todayCases);
            stat.setDailyDelta(todayCases - yesterdayCases);
            newDailyStats.add(stat);
        }
        this.currentStats = newDailyStats;

        // to get the time the data updated:
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentTime = dateFormat.format(date);
    }
}
