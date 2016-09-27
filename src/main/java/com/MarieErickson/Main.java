package com.MarieErickson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;


public class Main {

    public static void main(String[] args) throws Exception{
	// write your code here
        String key = null;
        String geoKey = null;
        try(BufferedReader reader = new BufferedReader(new FileReader("key.txt"))){
            key = reader.readLine();
            System.out.println(key);

        }catch(Exception ioe){
            System.out.println("No key file found, or could not read key. " +
                    "Please verify key.txt present");
            System.exit(-1);
        }
        try(BufferedReader reader = new BufferedReader(new FileReader("geoKey.txt"))){
            geoKey = reader.readLine();
            System.out.println(geoKey);

        }catch(Exception ioe){
            System.out.println("No geoKey file found, or could not read key. " +
                    "Please verify geoKey.txt present");
            System.exit(-1);
        }
            GeoApiContext context = new GeoApiContext().setApiKey(key);
        GeoApiContext gcContext = new GeoApiContext().setApiKey(geoKey);
        Scanner stringScanner = new Scanner(System.in);
        Scanner numberScanner = new Scanner(System.in);
        System.out.println("Enter the name of a city to get it's LatLng");
        String city = stringScanner.nextLine();
        GeocodingResult[] mctcLatLang = new GeocodingApiRequest(gcContext).address(city).await();

        for(int x= 0; x<mctcLatLang.length; x++){

            System.out.println(x+". "+mctcLatLang[x].formattedAddress);
        }
//get list of options?
        System.out.println("Enter which address you would like to get results for");
        int y = numberScanner.nextInt();
        double lat = mctcLatLang[y].geometry.location.lat ;
        double lng = mctcLatLang[y].geometry.location.lng;
        //mctcLatLang.latlng(LatLng mctc);
        //System.out.println();
        LatLng addressLL = new LatLng(lat, lng);
        ElevationResult[] results = ElevationApi.getByPoints(context, addressLL).await();
        if (results.length>=1){
            ElevationResult mctcElevation = results[0];
            System.out.println("The elevation of MCTC above sea level is "+mctcElevation.elevation+" meters");
            System.out.println(String.format("The elevation of MCTC above sea level is %.2f meters.", mctcElevation.elevation));
        }
        else{
            System.out.println("No results found");
        }

    }
}
