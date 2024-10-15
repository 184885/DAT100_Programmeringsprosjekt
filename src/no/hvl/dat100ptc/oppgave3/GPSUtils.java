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

		// TODO
		min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}

		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		// TODO
		double[] latitudes = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		return latitudes;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		// TODO

		double[] longitudes = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		// TODO
		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();

		double phi1 = Math.toRadians(latitude1);
		double phi2 = Math.toRadians(latitude2);
		double deltaphi = phi1 - phi2;
		double deltadelta = Math.toRadians(longitude2) - Math.toRadians(longitude1);
		d = R * compute_c(compute_a(phi1, phi2, deltaphi, deltadelta));

		return d;

	}

	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {

		// TODO
		double a;
		a = Math.pow(Math.sin(deltaphi / 2), 2)
				+ Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltadelta) / 2, 2);

		return a;
	}

	private static double compute_c(double a) {

		// TODO
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return c;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		// TODO
		double d = distance(gpspoint1, gpspoint2);
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		speed = d / secs;
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		// TODO
		String format1 = "  %1$02d" + TIMESEP + "%2$02d" + TIMESEP + "%3$02d";
		int hh = secs / 3600;
		int mm = secs % 3600 / 60;
		int ss = secs - hh * 3600 - mm * 60;
		timestr = String.format(format1, hh, mm, ss);
		String time = String.format("%1$10S", timestr);
		return time;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		// TODO
		String format = "%1$" + TEXTWIDTH + ".2f";
		str = (String.format(format, d)).replace(',', '.');
		return str;

	}
}
