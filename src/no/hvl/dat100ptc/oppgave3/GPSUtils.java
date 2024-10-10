package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.TODO;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] latitudes = new double [gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		  }
		
		return latitudes;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] longitudes = new double [gpspoints.length];
		
		for (int i=0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		  }
		
		return longitudes;

	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();

		// til radianer
		
		double la1 = Math.toRadians(latitude1);
		double la2 = Math.toRadians(latitude2);
		double lo1 = Math.toRadians(longitude1);
		double lo2 = Math.toRadians(longitude2);
		
		double lat = la2 - la1;
		double lon = lo2 - lo1;
		double a = compute_a(la1,la2,lat,lon);
		double c = compute_c(a);
		
		d = R * c; 
		
		return d;
		
	}
	
	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
	
		double a = Math.pow(Math.sin(deltaphi/2),2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltadelta/2),2);
		
		return a;
		
		// TODO 

	}

	private static double compute_c(double a) {

		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return c;

	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double distance = distance(gpspoint1, gpspoint2);
		
		double tid = 10;
		
		double hastighet = distance / tid;
		
		return hastighet;
		}
		

	public static String formatTime(int secs) {

		int timer = secs / 3600;
		int minutter = (secs % 3600) / 60;
		int sekunder = secs % 60;
		
		String tidStreng = String.format("%02d:%02d:%02d", timer, minutter, sekunder);
		
		String formatTid = String.format("%10s", tidStreng);
		
		return formatTid;
		
		
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		
		String mellomrom ="";

		str = String.format("%.2f",d);
		str = str.replace(",",".");
		int lengde = TEXTWIDTH - str.length();
		
		int i = 0;
		while(i < lengde) {
			mellomrom += " "; 
			i++;
		}
		
		return mellomrom + str; 
		
		
	}
}
