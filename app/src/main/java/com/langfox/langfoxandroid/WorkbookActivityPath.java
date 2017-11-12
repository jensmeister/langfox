package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Base64;
        import android.util.Log;

        import com.google.gson.Gson;
        import com.langfox.cache.CategoryProxy;
        import com.langfox.cache.Deserializer;
        import com.langfox.cache.ExerciseProxy;
        import com.langfox.langfoxandroid.data.DataAdapter;
        import com.langfox.langfoxandroid.pojo.Category_Proxies;

        import org.apache.shiro.crypto.hash.Sha512Hash;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;

        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;

        import static android.R.attr.key;

public class WorkbookActivityPath extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call initViews to retrieve data in the recyclerView
        initViews(6);//4 (exercise view), 5 (path view), 6 (category view) are the relevant values
        setContentView(R.layout.activity_workbook_path);

    }

    private void initViews(final Integer id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.langfox.com/")
                .build();
        LangfoxAPI repo = retrofit.create(LangfoxAPI.class);
        Call<ResponseBody> call = repo.CategoryProxiesBySimpleGetCall(id.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                byte[] serializedData = getBytes(response);
                HashMap<String, List<CategoryProxy>> map = Deserializer.deSerializeHashMapWithCategoryProxies(serializedData);

                List<CategoryProxy> categoryProxies = map.get("ende"); //ui language iso6391 + new language iso 6391
                ArrayList<Language> language_array = new ArrayList<>();

                String iconRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";

                if (categoryProxies == null || categoryProxies.isEmpty()) {
                    Log.d("langfoxApp", "categoryProxies is empty");
                } else {
                    Log.d("langfoxApp", "categoryProxies size: " + categoryProxies.size());

                    Iterator iterator = categoryProxies.iterator();
                    while (iterator.hasNext()) {
                        CategoryProxy categoryProxy = (CategoryProxy) iterator.next();
                        String categoryIcon = iconRoot + categoryProxy.geCategoryIcon();

                        Language language = new Language();
                        language.setName(categoryProxy.getCategoryTitle().toString());
                        language.setImageURL(categoryIcon);
                        language_array.add(language);
                    }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
                    recyclerView.setHasFixedSize(true);

                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    recyclerView.setLayoutManager(layoutManager);

                    DataAdapter adapter = new DataAdapter(getApplicationContext(), language_array);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


//    private String[] printOutCategoryProxies(HashMap<String, List<CategoryProxy>> map, String key) {



    private void printOutExerciseProxies(HashMap<String, List<ExerciseProxy>> map, String key) {
        List<ExerciseProxy> exerciseProxies = map.get(key); //ui language iso6391 + new language iso 6391
        if (exerciseProxies == null || exerciseProxies.isEmpty()) {
            Log.d("langfoxApp", "exerciseProxies is empty");
        } else {
            Log.d("langfoxApp", "exerciseProxies size: " + exerciseProxies.size());
            Iterator iterator = exerciseProxies.iterator();
            while (iterator.hasNext()) {
                ExerciseProxy exerciseProxy = (ExerciseProxy) iterator.next();
                String imageRoot = "https://s3-eu-west-1.amazonaws.com/jwfirstbucket/img/catex/";
                String message = exerciseProxy.getTitleTranslated() + ", " + exerciseProxy.getQuestionCount() + ", " + imageRoot + exerciseProxy.getImageFileName();
                Log.d("langfoxApp", message);
            }
        }
    }

    private byte[] getBytes(Response<ResponseBody> response) {
        try {
            Gson gson = new Gson();
            Category_Proxies contributorsList = gson.fromJson(response.body().string(), Category_Proxies.class);
            String base64EncodedData = contributorsList.getObject_base_64_encoded();
            byte[] serializedData = Base64.decode(base64EncodedData, Base64.DEFAULT);
            String sha512Hash = new Sha512Hash(serializedData).toString();
            Log.d("langfoxApp", "Data byteCount: " + serializedData.length + ", sha512Hash: " + sha512Hash);
            return serializedData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}


