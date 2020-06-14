package com.api.assignment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestMoviesUpcoming 
{
	Response objResponse;
	Set<Object> lstResponseMoviePromoCodeSet;
	
	@BeforeTest
	public void setUp()
	{
		RestAssured.baseURI="https://apiproxy.paytm.com/";
		RestAssured.basePath="v2/movies/upcoming";
		objResponse = RestAssured.get();
	}
	
	@Test
	public void validateStatusCode()
	{
		System.out.println(objResponse.asString());
		Assert.assertEquals(objResponse.getStatusCode(), 200);
	}
	
	@Test
	public void validateMovieReleaseDate()
	{
		Date objDate = new Date();
		SimpleDateFormat objDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String date = objDateFormat.format(objDate);

		String jsonpathReleaseDate = "upcomingMovieData.findAll { it.releaseDate>'"+date+"' }.releaseDate"; 
		List<Object> lstResponseReleaseDate = objResponse.getBody().jsonPath().getList(jsonpathReleaseDate);
		Assert.assertFalse(lstResponseReleaseDate.isEmpty());
	}
	
	@Test
	public void validateMovieURL()
	{
		String jsonpathMovieUrl = "upcomingMovieData.moviePosterUrl"; 
		List<Object> lstResponseMovieUrl = objResponse.getBody().jsonPath().getList(jsonpathMovieUrl);
		System.out.println(lstResponseMovieUrl);
		Iterator<Object> itr = lstResponseMovieUrl.iterator();
		while(itr.hasNext())
		{
			String url = (String) itr.next();
			System.out.println("url: " + url);
			Assert.assertTrue(url.contains(".jpg"));
		}
	}
	
	
	@Test
	public void validateMoviePromoCodeIsUnique()
	{
		String jsonpathMoviepromoCode = "upcomingMovieData.paytmMovieCode"; 
		List<Object> lstResponseMoviepromoCode = objResponse.getBody().jsonPath().getList(jsonpathMoviepromoCode);
		System.out.println(lstResponseMoviepromoCode.size());
		
		Set<Object> lstResponseMoviePromoCodeSet = new HashSet<Object>();
		lstResponseMoviePromoCodeSet.addAll(lstResponseMoviepromoCode);
		System.out.println(lstResponseMoviePromoCodeSet.size());
		Assert.assertEquals(lstResponseMoviepromoCode.size(), lstResponseMoviePromoCodeSet.size());
	}
	
	
	@Test
	public void validateMovieLanguage()
	{
		String jsonpathMoviepromoCode = "upcomingMovieData.paytmMovieCode"; 
		List<Object> lstResponseMoviepromoCode = objResponse.getBody().jsonPath().getList(jsonpathMoviepromoCode);
		System.out.println(lstResponseMoviepromoCode.size());
		
		Set<Object> lstResponseMoviePromoCodeSet = new HashSet<Object>();
		lstResponseMoviePromoCodeSet.addAll(lstResponseMoviepromoCode);
		for(Object obj : lstResponseMoviePromoCodeSet)
		{
			String promoCode = (String) obj;
			System.out.println("promoCode: "+ promoCode);
			String jsonpathMovieLanguage = "upcomingMovieData.findAll { it.paytmMovieCode=='"+promoCode+"' }.language";
			System.out.println("promoCode: "+ jsonpathMovieLanguage);
			List<Object> lstResponseMovieLanguage = objResponse.getBody().jsonPath().getList(jsonpathMovieLanguage);
			System.out.println("lstResponseMovieLanguage: "+ lstResponseMovieLanguage);
			Assert.assertEquals(lstResponseMovieLanguage.size(), 1);
		}
	}
	
	@Test
	public void writeMovieNamesToExcelSheet()
	{
		String isContentAvailable = "0";
		String jsonpathIsContentAvailable = "upcomingMovieData.findAll { it.isContentAvailable=="+isContentAvailable+" }.movie_name";
		System.out.println("jsonpathIsContentAvailable: "+ jsonpathIsContentAvailable);
		List<Object> lstResponseIsContentAvailable = objResponse.getBody().jsonPath().getList(jsonpathIsContentAvailable);
		System.out.println(lstResponseIsContentAvailable);
		
		ExcelUtility objExcelUtility = new ExcelUtility();
		try {
			objExcelUtility.createExcelSheet(lstResponseIsContentAvailable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
