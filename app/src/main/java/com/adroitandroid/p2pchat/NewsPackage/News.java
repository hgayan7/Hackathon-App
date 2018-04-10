package com.adroitandroid.p2pchat.NewsPackage;

import android.util.Log;

import java.util.Calendar;

public class News {

    private String imageURl;
    private String title;
    private String content;
    private String source;
    private Calendar time;

    public News(String imageURl, String title, String content, String source, String time) {
        this.imageURl = imageURl;
        this.title = title;
        this.content = content;
        this.source = source;
        this.time = getTime(time);
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    private Calendar getTime(String timeString){

        Calendar c = Calendar.getInstance();

        int yyyy, MM, dd, hh, mm, ss;
        yyyy = Integer.parseInt(timeString.substring(0,4));
        MM = Integer.parseInt(timeString.substring(6,7));
        dd = Integer.parseInt(timeString.substring(8,10));
        hh = Integer.parseInt(timeString.substring(11,12));
        mm = Integer.parseInt(timeString.substring(14,15));
        ss = Integer.parseInt(timeString.substring(17,18));

        Log.i("time", Integer.toString(yyyy) + " " + Integer.toString(MM) + " "  + Integer.toString(dd) + " "  + Integer.toString(hh) + " "
                + Integer.toString(mm) + " "  + Integer.toString(ss));
        c.set(yyyy, MM, dd, hh, mm, ss );

        return c;
    }
}
