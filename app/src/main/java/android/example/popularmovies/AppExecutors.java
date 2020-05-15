package android.example.popularmovies;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//On why Executors are better than AsyncTask for querying data:
// https://stackoverflow.com/questions/7717334/asynctask-v-s-threadpoolexecutor-for-network-request:

public class AppExecutors {

    public static AppExecutors instance;

    private AppExecutors() {
    }

    public static AppExecutors getInstance() {
        if(instance == null) {
            return instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getNetworkIO(){
        return mNetworkIO;
    }
}


