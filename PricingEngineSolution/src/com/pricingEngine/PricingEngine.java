package com.pricingEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class PricingEngine {

	HashMap<String, ArrayList<String>> surveyContainer = new HashMap<String, ArrayList<String>>();
	HashMap<StringBuilder, ArrayList<String>> clasificationContainer = new HashMap<StringBuilder, ArrayList<String>>();
	ArrayList<StringBuilder> keys = new ArrayList<StringBuilder>();
	HashMap<String, ArrayList<Double>> productPriceMap = new HashMap<String, ArrayList<Double>>();
	private static Scanner scan;
	private int size;
	private ArrayList<String> productFound;
	private double price;

	void addSurveyData(HashMap<String, ArrayList<String>> surveyContainer, String product, String supply,
			String demand) {
		ArrayList<String> supplyDemandList = new ArrayList<String>();
		supplyDemandList.add(supply);
		supplyDemandList.add(demand);
		surveyContainer.put(product, supplyDemandList);
	}

	void addProductClassification(HashMap<StringBuilder, ArrayList<String>> classificationContainer,
			StringBuilder product, String competitor, String price) {
		ArrayList<String> competetiorPriceList = new ArrayList<String>();
		competetiorPriceList.add(competitor);
		competetiorPriceList.add(price);
		classificationContainer.put(product, competetiorPriceList);
	}

	public static void main(String[] console) {
		PricingEngine priceEngine = new PricingEngine();

		System.out.print("please enter total no.of surveyed products: ");
		scan = new Scanner(System.in);
		String totalSurveyedProducts = scan.next();
		PricingEngineUtility priceEngineUtil = new PricingEngineUtility();
		priceEngineUtil.inputSurveydData(totalSurveyedProducts, priceEngine);

		System.out.print("Please enter total no. of classified products: ");
		String totalClassifiedProducts = scan.next();

		priceEngineUtil.inputClassificationData(totalClassifiedProducts, priceEngine);

		System.out.println("\n-----------------");
		System.out.println("Recommended price: ");
		System.out.println("-----------------");
		priceEngine.calculateFuturePrice(priceEngine.surveyContainer, priceEngine.clasificationContainer, priceEngine.productPriceMap);
	}

	void calculateFuturePrice(HashMap<String, ArrayList<String>> surveyContainer,
			HashMap<StringBuilder, ArrayList<String>> clasificationContainer,
			HashMap<String, ArrayList<Double>> productPriceMap) {

		setSize(surveyContainer.size());
		Object surveyContainerKeyset[] = surveyContainer.keySet().toArray();
		setProductFound(new ArrayList<String>());

		for (Object baseproduct : surveyContainerKeyset) {

			String productSupply = surveyContainer.get(baseproduct.toString()).get(0);
			String productDemand = surveyContainer.get(baseproduct.toString()).get(1);
			double price = 0.0;
			double priceSum = 0.0;
			int productCount = 0;
			ArrayList<Double> pricelist = new ArrayList<Double>();
			for (int count = 0; count < keys.size(); count++) {
				if (baseproduct.toString().equals(keys.get(count).toString())) {

					ArrayList<String> list = clasificationContainer.get(keys.get(count));

					if (productSupply.equals("L") && productDemand.equals("L"))
						price = Double.parseDouble(list.get(1)) + ((Double.parseDouble(list.get(1))) * 10) / 100;
					else if (productSupply.equals("L") && productDemand.equals("H"))
						price = Double.parseDouble(list.get(1)) + ((Double.parseDouble(list.get(1))) * 5) / 100;
					else if (productSupply.equals("H") && productDemand.equals("L"))
						price = Double.parseDouble(list.get(1)) - ((Double.parseDouble(list.get(1))) * 5) / 100;
					else
						price = Double.parseDouble(list.get(1));
					pricelist.add(price);
					priceSum = priceSum + price;
					productCount = productCount + 1;
				}
			}

			double priceAvg = priceSum / productCount;
			pricelist = filterPriceListByAvg(priceAvg, pricelist);
			Collections.sort(pricelist);

			productPriceMap.put(baseproduct.toString(), pricelist);

			productCount = 0;
		}
		calculateRecomPrice(productPriceMap);
	}

	void calculateRecomPrice(HashMap<String, ArrayList<Double>> productPriceMap) {

		for (String key : productPriceMap.keySet()) {
			ArrayList<Double> sortedPricelist = productPriceMap.get(key);
			int maxFreq = 0;
			double data = 0.0;
			for (double price : sortedPricelist) {
				int freq = Collections.frequency(sortedPricelist, price);

				if (freq > maxFreq && price > data) {
					maxFreq = freq;
					data = price;
				}

			}
			System.out.println(key + " price:= " + Math.round(data * 100.0) / 100.0);
		}
	}

	ArrayList<Double> filterPriceListByAvg(double avg, ArrayList<Double> priceList) {
		setPrice(0.0);
		ArrayList<Double> filteredPriceList = new ArrayList<Double>();
		for (double prc : priceList) {
			if (prc > (avg / 2))
				filteredPriceList.add(prc);
		}
		return filteredPriceList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<String> getProductFound() {
		return productFound;
	}

	public void setProductFound(ArrayList<String> productFound) {
		this.productFound = productFound;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}

