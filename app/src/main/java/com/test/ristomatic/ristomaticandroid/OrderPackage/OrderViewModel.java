package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.ElementModified;
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
    private int idRoom;

    private static AdaptersContainer adaptersContainer;
    private static InitDB initDB;

    private boolean richiama;
    private List<Course> courses;
    public JSONObject comandaRichiamata = null;




    public enum StatusCodeCases {
        STATUS_CODE_500, STATUS_CODE_SCONTRINO_500, REQUEST_SCONTRINO,STATUS_CODE_SCONTRINO_200
    }




    private static MutableLiveData<StatusCodeCases> statusCodeCases;




    public OrderViewModel(Application application) {
        super(application);
        setInitDB(new InitDB(this.getApplication()));
        courses = new ArrayList<>();
        orderRepository = new OrderRepository();
        statusCodeCases = new MutableLiveData<>();
        setAdaptersContainer(new AdaptersContainer());
    }


    public static AdaptersContainer getAdaptersContainer() {
        return adaptersContainer;
    }


    public static MutableLiveData<StatusCodeCases> getStatusCodeCases() {
        return statusCodeCases;
    }

    public static void setStatusCodeCases(StatusCodeCases statusCodeCase) {
        statusCodeCases.setValue(statusCodeCase);
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



    public void initRichiama(int tableId, int idRoom, Context context){
        this.idRoom = idRoom;
        this.richiama = true;
        this.tableId = tableId;
        this.seatsNumber = 0;
        this.idRoom = idRoom;
        setAdaptersContainer(new AdaptersContainer(context, getInitDB()));
    }

    public void init(int tableId, int seatsNumber, int idRoom, Context context) {
        this.idRoom = idRoom;
        this.richiama = false;
        this.richiama = richiama;
        this.seatsNumber = seatsNumber;
        this.tableId = tableId;
        setAdaptersContainer(new AdaptersContainer(context,courses, getInitDB()));
    }

    protected void sendReport() throws JSONException {
        JSONObject report = getReportInformation();
        if(comandaRichiamata == null){
            JSONArray courses = convertReportToJSON();
            System.out.println(courses.toString());
            report.put("portate",courses);
            getOrderRepository().sendReport(report, new VolleyCallbackObject() {
                @Override
                public void onSuccess(JSONObject result) {
                    Toast toast = Toast.makeText(ContextApplication.getAppContext(), "COMANDA ESEGUITA", Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    int color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myGreen);

                    view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    toast.show();
                }
            });
        }else {
            List<ElementModified> elementModifiedList = new ArrayList<>();
            List<Course> comandaModificataList = getCurrentCourses();


            List<Course> comandaRichiamataList = jsonObjectToCoursesList(comandaRichiamata);
            /*System.out.println("COMANDA RICHIAMATA: ");
            for (Course c : comandaRichiamataList) {
                System.out.println(c.toString());
            }
            System.out.println("COMANDA MODIFICATA: ");
            for (Course c : comandaModificataList) {
                System.out.println(c.toString());
            }*/

            for(Course comandaRichiama : comandaRichiamataList){

                Course modifiedCourse = isSameCourse(comandaModificataList, comandaRichiama);
                if(modifiedCourse != null){
                    elementModifiedList.addAll(addOrDeleteDishesInSameCourse(modifiedCourse, comandaRichiama));
                }else{
                    //LA PORTATA NON ESISTE
                    elementModifiedList.addAll(addOrDeleteDishesInSameCourse(new Course(), comandaRichiama));
                }

            }

            for(Course modifiedCourse : comandaModificataList){
                Course comandaRichiama = isSameCourse(comandaRichiamataList, modifiedCourse);
                if(comandaRichiama == null){
                    elementModifiedList.addAll(addOrDeleteDishesInSameCourse(modifiedCourse, new Course()));
                }
            }

            //SCORRERE COMANDA MODIFICATA PER TROVARE PORTATE AGGIUNTE



            JSONArray courses = new JSONArray();
            for(ElementModified e : elementModifiedList){

                Gson gson = new Gson();
                String json = gson.toJson(e);
                System.out.println("JSON: "+json);
                try {
                    courses.put(new JSONObject(json));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            report.put("modifiche", courses);


            getOrderRepository().sendModificaRichiamo(report, new VolleyCallbackObject() {
                @Override
                public void onSuccess(JSONObject result) {
                    Toast toast = Toast.makeText(ContextApplication.getAppContext(), "MODIFICHE INVIATE!", Toast.LENGTH_LONG);
                    View view = toast.getView();

                    int color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myGreen);

                    view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    toast.show();
                }
            });
        }
    }

    private List<ElementModified> addOrDeleteDishesInSameCourse(Course modifiedCourse, Course comandaRichiama){
        List<ElementModified> elementModifiedList = new ArrayList<>();
        int size = modifiedCourse.getAllSelectedDishes().size();
        boolean isModifiedCourseMin = true;

        if(size >= comandaRichiama.getAllSelectedDishes().size()){
            size = comandaRichiama.getAllSelectedDishes().size();
            isModifiedCourseMin = false;
        }


        if(size > 0) {


            for (int i = 0; i < size; i++) {

                SelectedDish modifiedDish = modifiedCourse.getAllSelectedDishes().get(i);
                SelectedDish selectedDish = comandaRichiama.getAllSelectedDishes().get(i);

                if (modifiedDish.getId_rig_tavolo() == selectedDish.getId_rig_tavolo()) {
                    if (modifiedDish.getTimeSelected() != selectedDish.getTimeSelected()) {
                        elementModifiedList.add(new ElementModified(modifiedDish.getSelectedDishId(), modifiedDish.getSelectedDishName(),
                                modifiedCourse.getCourseNumber(), selectedDish.getTimeSelected(), modifiedDish.getTimeSelected(), modifiedDish.getId_rig_tavolo()));
                    }
                    elementModifiedList.addAll(addOrDeleteVariants(modifiedDish.getSelectedVariants(), selectedDish.getSelectedVariants(), modifiedCourse.getCourseNumber(), modifiedDish.getId_rig_tavolo()));
                } else {

                    if (modifiedDish.getSelectedVariants().size() > 0) {
                        List<SelectedVariant> selectedVariants = new ArrayList<>();
                        List<ElementModified> addedVariants = addOrDeleteVariants(modifiedDish.getSelectedVariants(), selectedVariants,
                                modifiedCourse.getCourseNumber(), 0);
                        elementModifiedList.add(new ElementModified(modifiedDish.getSelectedDishId(), modifiedDish.getSelectedDishName(),
                                modifiedCourse.getCourseNumber(), 0, modifiedDish.getTimeSelected(), 0, addedVariants));
                    } else {
                        elementModifiedList.add(new ElementModified(modifiedDish.getSelectedDishId(), modifiedDish.getSelectedDishName(),
                                modifiedCourse.getCourseNumber(), 0, modifiedDish.getTimeSelected(), 0));
                        elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(),
                                modifiedCourse.getCourseNumber(), selectedDish.getTimeSelected(), selectedDish.getTimeSelected() * -1, selectedDish.getId_rig_tavolo()));
                    }
                }
            }
        }

        if(isModifiedCourseMin){

            for (int i = size; i < comandaRichiama.getAllSelectedDishes().size(); i++){
                SelectedDish selectedDish = comandaRichiama.getAllSelectedDishes().get(i);
                elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(),
                        comandaRichiama.getCourseNumber(), selectedDish.getTimeSelected(), selectedDish.getTimeSelected()*-1, selectedDish.getId_rig_tavolo()));
            }

        }else{



            for (int i = size; i < modifiedCourse.getAllSelectedDishes().size(); i++){
                SelectedDish modifiedDish = modifiedCourse.getAllSelectedDishes().get(i);
                if(modifiedDish.getSelectedVariants().size() > 0){
                    List<SelectedVariant> selectedVariants = new ArrayList<>();
                    List<ElementModified> addedVariants = addOrDeleteVariants(modifiedDish.getSelectedVariants(), selectedVariants,
                            modifiedCourse.getCourseNumber(), 0);
                    elementModifiedList.add(new ElementModified(modifiedDish.getSelectedDishId(), modifiedDish.getSelectedDishName(),
                            modifiedCourse.getCourseNumber(), 0, modifiedDish.getTimeSelected(), 0, addedVariants));
                }else{
                    elementModifiedList.add(new ElementModified(modifiedDish.getSelectedDishId(), modifiedDish.getSelectedDishName(),
                            modifiedCourse.getCourseNumber(), 0, modifiedDish.getTimeSelected(), 0));
                }
            }
        }
        return elementModifiedList;
    }

    private Course isSameCourse(List<Course> modifiedCourse, Course selectedCourse){
        Course course = null;
        for(Course c : modifiedCourse){
            if(c.getCourseNumber() == selectedCourse.getCourseNumber()){
                course = c;
            }
        }
        return course;
    }

    private List<ElementModified> addOrDeleteVariants(List<SelectedVariant> modifiedVariants, List<SelectedVariant> selectedVariants, int courseNumber, int rigaVairazione) {
        List<ElementModified> elementsModified = new ArrayList<>();
        boolean isModifiedMajor = true;
        int size = selectedVariants.size();
        if(modifiedVariants.size() <= selectedVariants.size()){
            size = modifiedVariants.size();
            isModifiedMajor = false;
        }

        for(int i = 0; i < size; i++){

            SelectedVariant modifiedVariant = modifiedVariants.get(i);
            SelectedVariant selectedVariant = selectedVariants.get(i);

            System.out.println("MOD RIG TAVOLO: "+modifiedVariant.getId_rig_tavolo() +"SEL RIG TAVOLO: "+ selectedVariant.getId_rig_tavolo());
            if(modifiedVariant.getId_rig_tavolo() == selectedVariant.getId_rig_tavolo()){
                if(modifiedVariant.isPlus() != selectedVariant.isPlus()){
                    if(modifiedVariant.isPlus()){
                        elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "--" + modifiedVariant.getVariantName(),
                                courseNumber, 1, -1, rigaVairazione));
                        elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "++" + modifiedVariant.getVariantName(),
                                courseNumber, 0, 1, rigaVairazione));

                    }else{
                        elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "++" + modifiedVariant.getVariantName(),
                                courseNumber, 1, -1, rigaVairazione));
                        elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "--" + modifiedVariant.getVariantName(),
                                courseNumber, 0, 1, rigaVairazione));
                    }
                }
            }else{
                elementsModified.add(new ElementModified(selectedVariant.getIdVariant(), selectedVariant.getVariantName(),
                        courseNumber, 1, -1, rigaVairazione));
                elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), modifiedVariant.getVariantName(),
                        courseNumber, 0, 1, rigaVairazione));
            }
        }
        if(isModifiedMajor){

            for (int i = size; i < modifiedVariants.size(); i++){
                SelectedVariant modifiedVariant = modifiedVariants.get(i);
                if(modifiedVariant.isPlus()){
                    elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "++"+modifiedVariant.getVariantName(),
                            courseNumber, 0, 1, rigaVairazione));
                }else{
                    elementsModified.add(new ElementModified(modifiedVariant.getIdVariant(), "--"+modifiedVariant.getVariantName(),
                            courseNumber, 0, 1, rigaVairazione));
                }
            }
        }else{
            for (int i = size; i < selectedVariants.size(); i++){
                SelectedVariant selectedVariant = selectedVariants.get(i);
                if(selectedVariant.isPlus()){
                    elementsModified.add(new ElementModified(selectedVariant.getIdVariant(), "++"+selectedVariant.getVariantName(),
                            courseNumber, 1, -1, rigaVairazione));
                }else{
                    elementsModified.add(new ElementModified(selectedVariant.getIdVariant(), "--"+selectedVariant.getVariantName(),
                            courseNumber, 1, -1, rigaVairazione));
                }

            }

        }
        return elementsModified;
    }


    public JSONObject getReportInformation() throws JSONException {
        JSONObject report = new JSONObject();
        JSONObject user = new JSONObject(LoginViewModel.getUserFileInString());
        report.put(getApplication().getString(R.string.Waiter),user.get("codi"));
        report.put("idSala", idRoom);
        report.put(getApplication().getString(R.string.id_tavolo), tableId);
        if(seatsNumber != 0)
            report.put(getApplication().getString(R.string.coperti), seatsNumber);
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

        JSONObject richiamaRequest = new JSONObject();
        try {
            richiamaRequest.put("idTavolo", idTable);
            richiamaRequest.put("idSala", idRoom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        orderRepository.getRichiama(new VolleyCallbackObject() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    comandaRichiamata = result;
                    List<Course> courses = jsonObjectToCoursesList(result);

                    for(Course c : courses){
                        System.out.println("COURSE: " + courses.toString());
                    }
                    adaptersContainer.setCoursesAdapter(new CoursesAdapter(context, courses));
                    callbackObject.onSuccess(getJsonToSendToOrderActivity(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, richiamaRequest);
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
        int idDish = selectedDish.getInt("selectedDishId");
        int timeSelected = selectedDish.getInt("timeSelected");
        String dishName = selectedDish.getString("selectedDishName");
        int id_rig_tavolo = selectedDish.getInt("id_rig_tavolo");
        selDish = new SelectedDish(idDish, dishName, variants, timeSelected, id_rig_tavolo);
        return selDish;
    }

    private JSONObject getJsonToSendToOrderActivity(JSONObject result) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String importo = result.getString("importo");
        jsonObject.put("importo", importo);
        return jsonObject;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
