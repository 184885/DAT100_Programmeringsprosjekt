package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) {

		// TODO
		gpspoints = new GPSPoint[antall];
		this.antall = 0;

	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;


		// TODO
		if (antall < gpspoints.length) { //Sjekker at det er plass i tabellen og setter inn GPSPunktet
			gpspoints[antall] = gpspoint;
			inserted = true;
			antall++;
		}
		return inserted;

	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;

		// TODO
		gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation); //Bruker convert til å omgjøre String verdiane til eit GPSPunkt
		boolean insert = insertGPS(gpspoint);
		return insert;


	}

	public void print() {
		
		// TODO
		
		System.out.println("====== GPS Data - START ======");
		for (int i=0;i<antall;i++) {
			System.out.print(gpspoints[i].toString());
		}
		System.out.println("====== GPS Data - SLUTT ======");

		
	}
}
