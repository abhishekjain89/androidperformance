package com.num.utils;

import java.util.Scanner;

import com.num.models.Measure;
import com.num.models.Throughput;
import com.num.models.WarmupExperiment;

public class ParseUtil {

	public static Measure PingParser(String s) {
		try {

			String lastLine = null;
			Scanner scanLines = new Scanner(s);
			Scanner scanLastLine;
			Scanner scanValues;
			double max;
			double min;
			double avr;
			double stddev;

			while (scanLines.hasNextLine())
				lastLine = scanLines.nextLine();

			scanLastLine = new Scanner(lastLine);
			scanLastLine.findInLine("=");

			if (scanLastLine.hasNext()) {
				scanValues = new Scanner(scanLastLine.next());
				scanValues.useDelimiter("/");
				try {
					min = scanValues.nextDouble();
					avr = scanValues.nextDouble();
					max = scanValues.nextDouble();
					stddev = scanValues.nextDouble();
					return (new Measure(max, min, avr, stddev));
				} catch (Exception e) {
					return null;
				}
			} else
				return (new Measure(-1, -1, -1, -1));
		} catch (Exception e) {
			return (new Measure(-1, -1, -1, -1));
		}
	}

	public static com.num.models.Throughput ThroughputParser(String output) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void warmupParser(String output, WarmupExperiment experiment) {

		Scanner scanLines = new Scanner(output);

		scanLines.nextLine();

		while (scanLines.hasNextLine()) {
			try {
				String line = scanLines.nextLine();
				int count = Integer.parseInt(line.split("icmp_seq=")[1].split(" ")[0]);
				double value = Double.parseDouble(line.split("time=")[1].split(" ms")[0]);
				
				experiment.addSequence(value, count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
