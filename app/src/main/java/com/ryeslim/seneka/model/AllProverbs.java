package com.ryeslim.seneka.model;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ryeslim.seneka.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AllProverbs {

    private ArrayList<Proverb> theListOfAll;
    private short dummyID;
    private String dummyProverb;

    private static AllProverbs instance = null;

    public static AllProverbs getInstance() {
        if (instance == null) {
            instance = new AllProverbs();
        }
        return instance;
    }

    private AllProverbs() {

        theListOfAll = new ArrayList<>();
        theListOfAll.ensureCapacity(1000);
        theListOfAll.add(new Proverb((short) 0, "Melas – plonasienis daiktas: gerai įsižiūrėjus, jis persišviečia."));
    }

    public Proverb getRandom() {
        Proverb result;
        int i = (int) Math.floor(Math.random() * theListOfAll.size());
        result = theListOfAll.get(i);
        return result;
    }


    private RequestQueue loadingQue;

    /**
     * Adapted from StackOverflow
     */
    public void setLoadingQue(RequestQueue loadingQue) {
        this.loadingQue = loadingQue;
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, "https://api.myjson.com/bins/yaa26",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    System.out.println(jsonObject.getInt("id"));
                                    System.out.println(jsonObject.getString("text"));
                                    dummyID = (short) jsonObject.getInt("id");
                                    dummyProverb = jsonObject.getString("text").trim();
                                    theListOfAll.add(new Proverb(dummyID, dummyProverb));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        MainActivity.getInstance().goForward();
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        loadingQue.add(arrReq);
    }
}

