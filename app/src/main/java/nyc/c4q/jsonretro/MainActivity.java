package nyc.c4q.jsonretro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.jsonretro.backend.PuppyService;
import nyc.c4q.jsonretro.model.RandoPuppy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    //    private static final String TAG = "jsontag";
    private static final String TAG = "JSON?";
    private ImageView imageView;
    private Button newPuppy;
    private PuppyService puppyService;
    private String puppyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject jsonObject1 = setJson();
        JSONArray jsonArray = parseJSONArray(jsonObject1);

        List<Person> person = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Joanne");
            jsonObject.put("age", 27);

            Log.d(TAG, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageView = (ImageView) findViewById(R.id.puppy_imageview);
        newPuppy = (Button) findViewById(R.id.new_puppy_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // creating the service so we can use it to make requests:
        puppyService = retrofit.create(PuppyService.class);

        newPuppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<RandoPuppy> puppy = puppyService.getPuppy();
                puppy.enqueue(new Callback<RandoPuppy>() {
                    @Override
                    public void onResponse(Call<RandoPuppy> call, Response<RandoPuppy> response) {
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        puppyUrl = response.body().getMessage();
                        Picasso.with(getApplicationContext())
                                .load(response.body().getMessage())
                                .into(imageView);
                    }

                    @Override
                    public void onFailure(Call<RandoPuppy> call, Throwable t) {
                        Log.d(TAG, "onResponse: " + t.toString());
                    }
                });
            }
        });
        if (savedInstanceState != null) {
            String savedPuppy = savedInstanceState.getString("puppyUrl");
            puppyUrl = savedPuppy;
            Picasso.with(getApplicationContext())
                    .load(savedPuppy)
                    .into(imageView);
        } else {
            newPuppy.callOnClick();
        }
    }

    private JSONArray parseJSONArray(JSONObject jsonObject1) {
        JSONArray finalJsonArray = null;

        try {
            finalJsonArray = jsonObject1.getJSONArray("person");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalJsonArray;
    }

    private JSONObject setJson() {
        JSONObject finalSetJson = null;
        try {
            finalSetJson = new JSONObject("{\"person\": [{“name”: “Jose”, “age”: 37},{“name”: “Ramona”, “age”: 40}]}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalSetJson;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("puppyUrl", puppyUrl);
    }



}
