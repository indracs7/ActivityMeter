package com.joblesscoders.activitymeter;

import java.util.List;

public class ActivityPojo
{
    private String filePath;
    private _id _id;

    public ActivityPojo(String filePath, com.joblesscoders.activitymeter._id _id, List<Float> magnetx, List<Float> magnety, String activity, List<Float> acceleroz, List<Float> gyrox, List<Float> gyroy, List<Float> gyroz, List<Float> accelerox, List<Float> acceleroy, long startTime, long endTime, List<Float> magnetz, String user) {
        this.filePath = filePath;
        this._id = _id;
        this.magnetx = magnetx;
        this.magnety = magnety;
        this.activity = activity;
        this.acceleroz = acceleroz;
        this.gyrox = gyrox;
        this.gyroy = gyroy;
        this.gyroz = gyroz;
        this.accelerox = accelerox;
        this.acceleroy = acceleroy;
        this.startTime = startTime;
        this.endTime = endTime;
        this.magnetz = magnetz;
        this.user = user;
    }

    public String getFilePath() {
        return filePath;
    }

    public com.joblesscoders.activitymeter._id get_id() {
        return _id;
    }

    private List<Float> magnetx;

    private List<Float> magnety;

    private String activity;

    private List<Float> acceleroz;

    private List<Float> gyrox;

    private List<Float> gyroy;

    private List<Float> gyroz;

    private List<Float> accelerox;

    private List<Float> acceleroy;

    private long startTime;

    private long endTime;

    private List<Float> magnetz;

    private String user;

    public List<Float> getMagnetx() {
        return magnetx;
    }

    public List<Float> getMagnety() {
        return magnety;
    }

    public String getActivity() {
        return activity;
    }

    public List<Float> getAcceleroz() {
        return acceleroz;
    }

    public List<Float> getGyrox() {
        return gyrox;
    }

    public List<Float> getGyroy() {
        return gyroy;
    }

    public List<Float> getGyroz() {
        return gyroz;
    }

    public List<Float> getAccelerox() {
        return accelerox;
    }

    public List<Float> getAcceleroy() {
        return acceleroy;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public List<Float> getMagnetz() {
        return magnetz;
    }

    public String getUser() {
        return user;
    }

    public ActivityPojo(List<Float> magnetx, List<Float> magnety, String activity, List<Float> acceleroz, List<Float> gyrox, List<Float> gyroy, List<Float> gyroz, List<Float> accelerox, List<Float> acceleroy, long startTime, long endTime, List<Float> magnetz, String user) {
        this.magnetx = magnetx;
        this.magnety = magnety;
        this.activity = activity;
        this.acceleroz = acceleroz;
        this.gyrox = gyrox;
        this.gyroy = gyroy;
        this.gyroz = gyroz;
        this.accelerox = accelerox;
        this.acceleroy = acceleroy;
        this.startTime = startTime;
        this.endTime = endTime;
        this.magnetz = magnetz;
        this.user = user;
    }
}
