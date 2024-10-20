package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			double ny = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			distance += ny;
		}
		return distance;
	}

	public double totalElevation() {

		double elevation = 0;

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i].getElevation() < gpspoints[i + 1].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}
		}
		return elevation;
	}

	public int totalTime() {

		// TODO
		int lengde = gpspoints.length;
		int time = gpspoints[lengde - 1].getTime() - gpspoints[0].getTime();
		return time;
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speeds;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		// TODO
		for (double speed : speeds()) {
			if (maxspeed < speed) {
				maxspeed = speed;
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double average = 0;

		// TODO
		average = totalDistance() / totalTime();

		return average;
	}

	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		// TODO
		double[] cycling = new double[] { 4.0, 6.0, 8.0, 10.0, 12.0, 16.0 }; //Met skala for sykling
		double[] speedsmph = new double[] { 10.0, 12.0, 14.0, 16.0, 20.0 }; // fart for ulik met verdi (sykling)
		if (speedmph < speedsmph[0]) { //om farta er lågare enn 10 får met verdi som 4.0
			met = cycling[0];
		} else {
			met = cycling[cycling.length - 1];// vis ikkje blir met høgaste verdi
		}
		for (int i = 0; i < speedsmph.length - 1; i++) { //Går gjennom og sjekker om verdien faller innanfor eit av dei andre fartene
			if (speedmph >= speedsmph[i] && speedmph < speedsmph[i + 1]) {
				met = cycling[i + 1];
			}
		}

		double hr = (double) secs / 3600; //Rekner om til tider
		kcal = met * weight * hr; //Rekner kcal
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		double speed[] = speeds();

		// TODO

		int t = 0;
		for (int i = 0; i < speed.length; i++) {
			t = gpspoints[i + 1].getTime() - gpspoints[i].getTime(); //finner tid
			totalkcal += kcal(weight, t, speed[i]); //setter inn vekt, tid og fart i kcal metoden, og legger de
		}

		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		// TODO

		String str[] = stringStatistics();
		System.out.println(str[0] + "\n" + str[1] + "\n" + str[2] + "\n" + str[3] + "\n" + str[4] + "\n" + str[5] + "\n"
				+ str[6] + "\n" + str[0]);

	}

	public String[] stringStatistics() {
		String format = "%1$-15s:";

		String t = String.format(format, "Total Time") + GPSUtils.formatTime(totalTime());
		String d = GPSUtils.formatDouble(totalDistance() / 1000);
		d = String.format(format, "Total distance") + d + " km";
		String e = GPSUtils.formatDouble(totalElevation());
		e = String.format(format, "Total elevation") + e + " m";
		String ms = GPSUtils.formatDouble(maxSpeed() * 3.6);
		ms = String.format(format, "Max speed") + ms + " km/t";
		String as = GPSUtils.formatDouble(averageSpeed() * 3.6);
		as = String.format(format, "Average speed") + as + " km/t";
		String k = GPSUtils.formatDouble(totalKcal(WEIGHT));
		k = String.format(format, "Energy") + k + " kcal";
		String eq = "==============================================";
		String[] str = { eq, t, d, e, ms, as, k, eq };
		return str;
	}

	public double[] climbs() {
		double[] d = new double[gpspoints.length - 1]; //Oppretter ein tabell med alle distansane (ikkje nødvendig, men gjorde det enklere å tenke)
		double[] e = new double[gpspoints.length - 1]; //Oppretter ein tabell med alle høydene (ikkje nødvendig, men gjorde det enklere å tenke)
		double[] climb = new double[d.length];
		for (int i = 0; i < e.length; i++) {

			d[i] = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			e[i] = gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			climb[i] = Math.atan(e[i] / d[i])*100; //Finner graden mellom lengde og høgde og omgjør til prosent
		}

		return climb;
	}

	public double maxClimb() {
		double max = GPSUtils.findMax(climbs()); //finner max frå climb tabellen
		return max;
	}

}
