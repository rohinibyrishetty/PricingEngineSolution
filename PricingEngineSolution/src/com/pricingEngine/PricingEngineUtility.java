package com.pricingEngine;

import java.util.Scanner;

public class PricingEngineUtility {

	private Scanner scan;
	private static Double prc;
	private Scanner scan2;

	void inputSurveydData(String totalSurveyedProducts, PricingEngine price) {
		try {
			int inputVal = Integer.parseInt(totalSurveyedProducts);
			if (inputVal <= 0) {
				System.out.println("oops data count should be more than zero !!!");
				System.exit(1);
			} else {
				scan2 = new Scanner(System.in);
				while (inputVal > 0) {
					String line = scan2.nextLine();
					if (line.indexOf(" ") != -1) {
						String productAnalysisArray[] = line.split(" ");
						if (productAnalysisArray.length == 3) {
							validateSurveyInputData(productAnalysisArray[1], productAnalysisArray[2]);
							price.addSurveyData(price.surveyContainer, productAnalysisArray[0], productAnalysisArray[1],
									productAnalysisArray[2]);
						} else {
							System.out
									.println("input surveyed data is more or less sufficient for further analysis!!!");
							System.exit(1);
						}
					} else {
						System.out.println("input surveyed data is more or less sufficient for further analysis!!!");
						System.exit(1);
					}
					if (!(line.equals("")))
						inputVal--;
				}
			}
		} catch (NumberFormatException nfe) {
			System.out.println("please enter a valid decimal number !!!");
			System.exit(1);
		}

	}

	static void validateSurveyInputData(String supply, String demand) {

		if (!((supply.equals("H") || supply.equals("L")) && (demand.equals("H") || demand.equals("L")))) {
			System.out.println("the entered supply/demand values are not appropriate !!!");
			System.exit(1);
		}
	}

	void inputClassificationData(String totalSurveyedProducts, PricingEngine price) {
		try {
			int inputVal = Integer.parseInt(totalSurveyedProducts);
			if (inputVal <= 0) {
				System.out.println("oops data count should be more than zero !!!");
				System.exit(1);
			} else {
				scan = new Scanner(System.in);
				while (inputVal > 0) {
					String line = scan.nextLine();
					if (line.indexOf(" ") != -1) {
						String productClassificationArray[] = line.split(" ");
						if (productClassificationArray.length == 3) {
							validateClassicInputData(productClassificationArray[1], productClassificationArray[2]);
							StringBuilder key = new StringBuilder(productClassificationArray[0]);
							price.keys.add(key);
							price.addProductClassification(price.clasificationContainer, key,
									productClassificationArray[1], productClassificationArray[2]);
						} else {
							System.out
									.println("input surveyed data is more or less sufficient for further analysis !!!");
							System.exit(1);
						}
					} else {
						System.out.println("input surveyed data is more or less sufficient for further analysis !!!");
						System.exit(1);
					}
					if (!(line.equals("")))
						inputVal--;
				}
			}
		} catch (NumberFormatException nfe) {
			System.out.println("please enter a valid decimal number !!!");
			System.exit(1);
		}

	}

	static void validateClassicInputData(String competitor, String price) {
		try {
			setPrc(Double.parseDouble(price));

		} catch (NumberFormatException nfe) {
			System.out.println("please enter a valid digit");
			System.exit(1);
		}

		if (!((competitor.equals("X") || competitor.equals("Y") || competitor.equals("Z") || competitor.equals("V")))) {
			System.out.println("the entered supply/demand values are not appropriate !!!");
			System.exit(1);
		}
	}

	public static Double getPrc() {
		return prc;
	}

	public static void setPrc(Double prc) {
		PricingEngineUtility.prc = prc;
	}


}
