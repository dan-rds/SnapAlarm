package com.example.snapalarm;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.gson.Gson;

import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


public class GVision {

    Vision vision;

    GVision(String apiKey) {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                jsonFactory,
                null).setApplicationName("SnapAlarm");
        visionBuilder.setVisionRequestInitializer(new VisionRequestInitializer(apiKey));

        vision = visionBuilder.build();
    }


    public List<AnnotateImageResponse> getFeatures(String filePath) throws IOException {
        //Convert image to base64
        Path path = Paths.get(filePath);
        byte[] imgData = Files.readAllBytes(path);

        //Set image request headers
        Feature feature = new Feature();
        feature.setType("LABEL_DETECTION");
        AnnotateImageRequest request = new AnnotateImageRequest();
        Image image = new Image();
        image.encodeContent(imgData);
        request.setImage(image);
        request.setFeatures(Arrays.asList(feature));

        //Send request and get response
        try {
            BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
            batchRequest.setRequests(Arrays.asList(request));
            BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();

            //Read response
            List<AnnotateImageResponse> responses = batchResponse.getResponses();
            return responses;
        } catch (GoogleJsonResponseException e) {
            Log.wtf("GVision Authentication", "Authentication error caused by ".concat(e.getMessage()));
            return null;
        }

    }


    public static HashMap callGVis(final String filePath) {

        // Limit threads and make returnable with executor
        HashMap preds = null;
        try {
           preds = new asyncCall().execute(filePath).get();
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
        return preds;
    }

    // Little method so that you can call log(whatever string) and not need to
    // change a million System.out.printlns to Log.ds or vice versa. (Just one)
    public static void log(String msg){
        //System.out.println(msg);
        Log.d("__LOG", msg);
    }

    public static HashMap parse(String jsonResp, HashMap predictions) {
        // split up json into string arrays
        String[] descriptions = jsonResp.split("\"description\":\"");
        String[] scores = jsonResp.split("score\":");
        for (int i = 0; i < 10; i++) {
            String desc = descriptions[i].split("\"")[0];  // take off the ends of the string
            String score = scores[i].split(",")[0];

            // If the desc or score contains any non-alpha chars, skip
            if (!desc.matches(".*[a-z].*")) {
                GVision.log("INVALID score  " + score);
                GVision.log("INVALID desc  " + desc);
                continue;
            }
            GVision.log("score  " + score);
            GVision.log("desc  " + desc);
            predictions.put(desc, Float.valueOf(score));  //add to hashmap
        }
        return predictions;
    }
}

class asyncCall extends AsyncTask<String, Integer, HashMap> {

    protected HashMap doInBackground(String... paths) {
        HashMap predictions = new HashMap();
        try {
            GVision viz = new GVision("AIzaSyCw3sLLmjUG9X4WRNEqANJUhUd4LpB9NBE");
            List<AnnotateImageResponse> response = viz.getFeatures(paths[0]);
            Gson gson = new Gson();

            String jsonResp = gson.toJson(response.get(0));
            predictions = GVision.parse(jsonResp, predictions);

            // split up json into string arrays
            String[] descriptions = jsonResp.split("\"description\":\"");
            String[] scores = jsonResp.split("score\":");
            for (int i = 0; i < 5; i++) {
                String desc = descriptions[i].split("\"")[0];  // take off the ends of the string
                String score = scores[i].split(",")[0];

                // If the desc or score contains any non-alpha chars, skip
                if (!desc.matches(".*[a-z].*")) {
                    GVision.log("INVALID score  " + score);
                    GVision.log("INVALID desc  " + desc);
                    continue;
                }
                GVision.log("score  " + score);
                GVision.log("desc  " + desc);
                predictions.put(desc, Float.valueOf(score));  //add to hashmap
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return predictions;
    }
}





