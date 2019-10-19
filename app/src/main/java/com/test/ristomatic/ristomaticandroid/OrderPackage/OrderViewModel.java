package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository orderRepository;
    private int tableId, seatsNumber;

    private static AdaptersContainer adaptersContainer;
    private static InitDB initDB;

    private List<Course> courses;
    private JSONObject comandaRichiamata = null;

    public OrderViewModel(Application application) {
        super(application);
        setInitDB(new InitDB(this.getApplication()));
        courses = new ArrayList<>();
        orderRepository = new OrderRepository();
        setAdaptersContainer(new AdaptersContainer());
    }


    public static AdaptersContainer getAdaptersContainer() {
        return adaptersContainer;
    }
    public void setAdaptersContainer(AdaptersContainer adaptersContainer) {
        this.adaptersContainer = adaptersContainer;
    }
    public static InitDB getInitDB() {
        return initDB;
    }
    public void setInitDB(InitDB initDB) {
        this.initDB = initDB;
    }
    public void initRichiama(int tableId, Context context){
        this.tableId = tableId;
        this.seatsNumber = 0;
        setAdaptersContainer(new AdaptersContainer(context, getInitDB()));
    }

    public void init(int tableId, int seatsNumber, Context context) {
        this.seatsNumber = seatsNumber;
        this.tableId = tableId;
        setAdaptersContainer(new AdaptersContainer(context,courses, getInitDB()));
    }

    protected void sendReport() throws JSONException {
        JSONObject report = getReportInformation();
        if(comandaRichiamata == null) {
            JSONArray courses = convertReportToJSON();
            report.put("portate",courses);
            System.out.println(report.toString());

            orderRepository.sendReport(report, new VolleyCallbackObject() {
                @Override
                public void onSuccess(JSONObject result) {
                }
            });
        }else{
            List<Course> currentCourses = getCurrentCourses();
            List<Course> richiamaCourses = jsonObjectToCoursesList(comandaRichiamata);
           // TODO: continuare confronto liste; meglio aspettare mergesendReport
        }
    }


    public JSONObject getReportInformation() throws JSONException {
        JSONObject report = new JSONObject();
        JSONObject user = new JSONObject(LoginViewModel.getUserFileInString());
        report.put(getApplication().getString(R.string.Waiter),user.get("codi"));
        report.put(getApplication().getString(R.string.id_tavolo), tableId);
        if(seatsNumber != 0){
            report.put(getApplication().getString(R.string.coperti), seatsNumber);
        }else{
            report.put(getApplication().getString(R.string.coperti), 0);
        }
        return report;
    }

    private JSONArray convertReportToJSON(){
        JSONArray courses = new JSONArray();
        for(int i=0; i< getCurrentCourses().size(); i++){
            Course course = getCurrentCourses().get(i);
            Gson gson = new Gson();
            String json = gson.toJson(course);
            try {
                courses.put(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }

    private List<Course> getCurrentCourses() {
        return CoursesAdapter.getCourses();
    }

    public void getRichiama(final VolleyCallbackObject callbackObject, int idTable, final Context context) {
        orderRepository.getRichiama(new VolleyCallbackObject() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    comandaRichiamata = result;
                    List<Course> courses = jsonObjectToCoursesList(result);
                    adaptersContainer.setCoursesAdapter(new CoursesAdapter(context, courses));
                    callbackObject.onSuccess(getJsonToSendToOrderActivity(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, idTable);
    }

    private List<Course> jsonObjectToCoursesList(JSONObject result) throws JSONException {
        List<Course> courses = new ArrayList<>();
        JSONArray portate = result.getJSONArray("portate");

        for (int i = 0; i < portate.length(); i++) {
            courses.add(getCourseFromJson(portate,i));
        }
        return courses;
    }

    private Course getCourseFromJson(JSONArray portate, int i) throws JSONException {
        JSONObject portata = portate.getJSONObject(i);
        int courseNumber = portata.getInt("courseNumber");
        JSONArray selectedDishes = portata.getJSONArray("selectedDishes");
        List<SelectedDish> selectedDishes1 = new ArrayList<>();
        for (int j = 0; j < selectedDishes.length(); j++) {
            selectedDishes1.add(getSelectedDishFromJson(selectedDishes, j));
        }
        return new Course(courseNumber, selectedDishes1);
    }

    private SelectedDish getSelectedDishFromJson(JSONArray selectedDishes, int j) throws JSONException {
        Gson gson = new Gson();
        SelectedDish selDish;
        JSONObject selectedDish = selectedDishes.getJSONObject(j);
        JSONArray selectedVariants = selectedDish.getJSONArray("selectedVariants");
        List<SelectedVariant> variants = new ArrayList<>();

        for (int z = 0; z < selectedVariants.length(); z++) {
            SelectedVariant variant = gson.fromJson(selectedVariants.get(z).toString(), SelectedVariant.class);
            variant.setVariantName(getInitDB().getVariantModelDao().getVariantName(variant.getIdVariant()));
            variants.add(variant);
        }
        int timeSelected = selectedDish.getInt("timeSelected");
        String dishName = selectedDish.getString("selectedDishName");

        selDish = new SelectedDish(dishName, variants, timeSelected);
        return selDish;
    }

    private JSONObject getJsonToSendToOrderActivity(JSONObject result) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String importo = result.getString("importo");
        jsonObject.put("importo", importo);
        return jsonObject;
    }


    //Cambia lo stato del tavolo indicato nello stato specificato
    public void changeTableState(final int idTable, final String state){
        JSONObject objectToSend = new JSONObject();
        try {
            objectToSend = createJsonToSend(idTable, state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        orderRepository.changeTableState(new VolleyCallbackObject() {
            @Override
            public void onSuccess(JSONObject result) { }
        },objectToSend);
    }


    public JSONObject createJsonToSend(int idTable, String state) throws JSONException {
        JSONObject valuesToChange = new JSONObject();
        valuesToChange.put("idTavolo", idTable);
        valuesToChange.put("stato", state);
        return valuesToChange;
    }

}
