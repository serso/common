package org.solovyev.common.utils;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * User: serso
 * Date: 04.05.2009
 * Time: 22:01:24
 */
public class Timer {
    private static HashMap<String, Long> timeMap = new HashMap<String, Long>();
    private static Calendar cal = Calendar.getInstance();

    public static void startTask( String task ) {
        timeMap.put( task, System.currentTimeMillis() );
    }

    public static Date getStartTime( String task ) {
        cal.setTimeInMillis(timeMap.get( task ));
        return cal.getTime();
    }

    public static long getTimeInMillis ( String task ) {
        return  ( System.currentTimeMillis() - timeMap.get( task ) );
    }

    public static String getStartTimeString ( String task ) {
        return "Task '" + task + "' was started at " + Timer.getStartTime(task) + ".";
    }

    public static void writeStartTimeString( PrintWriter out, String task ) throws IOException {
        out.write( Timer.getStartTimeString(task) );
        out.println();
    }

    public static void writeTimeString( PrintWriter out, String task ) throws IOException {
        out.write( Timer.getTimeString(task) );
        out.println();        
    }

     public static String getTimeString ( String task ) {
        return "Task '" + task + "' has worked for " + getTimeInMillis( task ) + " ms.";
    }


    public static int getTimeInSeconds( String task ) {
        cal.setTimeInMillis(getTimeInMillis( task ));
        return cal.get( Calendar.MILLISECOND );
    }

    public static Date getTime( ) {
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.getTime();
    }



    public static void deleteTask ( String task ) {
        timeMap.remove(task);
    }
}
