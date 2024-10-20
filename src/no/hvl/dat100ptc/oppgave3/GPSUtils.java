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
		double[] latitudes = new double[gpspoints.length]; //Oppretter ny tabell 
		for (int i = 0; i < gpspoints.length; i++) { //Skriver inn kvart latitudes verdi i tabellen
			latitudes[i] = gpspoints[i].getLatitude();
		}
		return latitudes;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		// TODO

		double[] longitudes = new double[gpspoints.length]; //Oppretter ny tabell 
		for (int i = 0; i < gpspoints.length; i++) { //Skriver inn kvart longitudes verdi i tabellen
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		// TODO
		//Setter verdier som er relevante for å rekne distanse i følge formelen
		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();
		
		//Omrekner til radianer
		double phi1 = Math.toRadians(latitude1);
		double phi2 = Math.toRadians(latitude2);
		//Rekner ut deltaphi og delta'delta' *lamda
		double deltaphi = phi2 - phi1;
		double deltadelta = Math.toRadians(longitude2) - Math.toRadians(longitude1);
		// Bruker hjelpesetningane til å rekne avstanden
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
		double d = distance(gpspoint1, gpspoint2); //bruker distance til å finne avstanden mellom punktene
		secs = gpspoint2.getTime() - gpspoint1.getTime(); //Rekner tida som er gått mellom punkta
		speed = d / secs; //Rekner ut farten
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		// TODO
		String format1 = "  %1$02d" + TIMESEP + "%2$02d" + TIMESEP + "%3$02d"; //Setter opp eit format for koden
		int hh = secs / 3600; //Rekner ut timer 
		int mm = secs % 3600 / 60; // Rekner ut gjenståande minutter
		int ss = secs - hh * 3600 - mm * 60; //Rekner ut gjenståande sekunder
		timestr = String.format(format1, hh, mm, ss); //Lagar ein String med ønsket format
		String time = String.format("%1$10S", timestr); //Sørger for at stringen er ønsket lengde
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
