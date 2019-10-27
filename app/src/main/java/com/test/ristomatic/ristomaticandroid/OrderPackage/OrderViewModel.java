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
        this.richiama = true;
        this.tableId = tableId;
        this.seatsNumber = 0;
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
        if(comandaRichiamata == null) {
            JSONArray courses = convertReportToJSON();
            report.put("portate",courses);
            orderRepository.sendReport(report, richiama, new VolleyCallbackObject() {
                @Override
                public void onSuccess(JSONObject result) {
                }
            });
        }else{

            List<ElementModified> elementModifiedList = new ArrayList<>();
            List<Course> comandaModificataList = getCurrentCourses();
            List<Course> comandaRichiamataList = jsonObjectToCoursesList(comandaRichiamata);

            //SCORRI DA COMANDA RICHIAMATA E SE ELEMENTI NON PRESENTI AGGIUNGI CON -
            for(Course course : comandaRichiamataList){
                List<SelectedDish> piattiComandaModificata = checkIfCoursePresentInModificata(comandaModificataList, course.getCourseNumber());
                //LA PORTATA E' PRESENTE
                if(piattiComandaModificata != null){
                    for(SelectedDish selectedDish : course.getAllSelectedDishes()){
                        //TODO: da rivedere discorso varianti
                        if(checkIfDishPresentEqual(elementModifiedList, selectedDish, piattiComandaModificata, course.getCourseNumber(), true) == 2){
                            //SELEZIONARE PIATTI CON LO STESSO ID

                        }
                    }
                }else{//LA PORTATA NON E PRESENTE
                    addAllElementsInCourseToModifiedList(elementModifiedList, course.getAllSelectedDishes(), course.getCourseNumber(), true);
                }

            }

            //SCORRI DA COMANDA MODIFICATA E SE ELEMENTI NON PRESENTI AGGIUNGI CON +
            for (Course course : comandaModificataList){

                List<SelectedDish> piattiComandaModificata = checkIfCoursePresentInModificata(comandaRichiamataList, course.getCourseNumber());
                if(piattiComandaModificata != null){
                    for(SelectedDish s : course.getAllSelectedDishes()){
                        if(checkIfDishPresentEqual(elementModifiedList, s, piattiComandaModificata, course.getCourseNumber(), false) == 2){
                            //SELEZIONARE PIATTI CON LO STESSO ID

                        }
                    }

                }else{//AGGIUNGO TUTTO
                    addAllElementsInCourseToModifiedList(elementModifiedList, course.getAllSelectedDishes(), course.getCourseNumber(), false);

                }
            }




            JSONArray courses = new JSONArray();

            for(ElementModified e : elementModifiedList){
                Gson gson = new Gson();
                String json = gson.toJson(e);
                try {
                    courses.put(new JSONObject(json));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            report.put("modifiche", courses);


            orderRepository.sendModificaRichiamo(report, new VolleyCallbackObject() {
                @Override
                public void onSuccess(JSONObject result) {

                }
            });
        }
    }




    // 1 : PIATTO UGALE CON STESSE VARIAZIONI TROVATO
    // 2 : STESSO PIATTO MA CON VARIAZIONI DIVERSE
    // 3 : PIATTO CON ID NON MATCHATO
    //SE TROVA PIATTO UGALE, controlla se quantita e ritorna vero
    private int checkIfDishPresentEqual(List<ElementModified> elementModifiedList,SelectedDish selectedDish, List<SelectedDish> piattiComandaModificata, int portata, boolean isRichiama) {
        int result = 3;
        for(SelectedDish d : piattiComandaModificata){
            //PIATTO UGUALE CON VARIANTI UGUALI
            if(d.equals(selectedDish)){

                if(d.getTimeSelected() != selectedDish.getTimeSelected()){
                    elementModifiedList.add(new ElementModified(d.getSelectedDishId(),d.getSelectedDishName(), portata,selectedDish.getTimeSelected(),d.getTimeSelected()));
                }
                return 1;
            }else if(d.getSelectedDishId() == selectedDish.getSelectedDishId()){
                result = 2;
            }
        }
        //NON E' STATO TROVATO NESSUN PIATTO CON QUESTO ID --> E' STATO ELIMINATO
        if (result == 3){
            if(isRichiama){
                int quantitaModificata = selectedDish.getTimeSelected() * -1;
                elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(), portata, selectedDish.getTimeSelected(), quantitaModificata));
            }else{
                elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(), portata, 0, selectedDish.getTimeSelected()));
            }
        }
        return result;

    }

    private List<SelectedDish> checkIfCoursePresentInModificata(List<Course> comandaModificata, int numeroComanda){

        for(Course course : comandaModificata){
            if(course.getCourseNumber() == numeroComanda){
                return course.getAllSelectedDishes();
            }
        }
        return null;
    }


    private void addAllElementsInCourseToModifiedList(List<ElementModified> elementModifiedList, List<SelectedDish> piattiEliminati, int numeroPortata, boolean isRichiama){
        for(SelectedDish selectedDish : piattiEliminati){
            //IF PER NON FARE DUE METODI DIVERSI
            if(isRichiama){
                int quantitaModificata = selectedDish.getTimeSelected() * -1;

                elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(), numeroPortata, selectedDish.getTimeSelected(),quantitaModificata));

                //SE CI SONO VARIANTI
                //TODO: da rivedere discorso varianti
                if(!selectedDish.getSelectedVariants().isEmpty()) {
                    for(SelectedVariant v : selectedDish.getSelectedVariants()){
                        elementModifiedList.add(new ElementModified(v.getIdVariant(), v.getVariantName(), numeroPortata, 1, -1));
                    }
                }
            }else{
                elementModifiedList.add(new ElementModified(selectedDish.getSelectedDishId(), selectedDish.getSelectedDishName(), numeroPortata,0, selectedDish.getTimeSelected()));
                if(!selectedDish.getSelectedVariants().isEmpty()){

                }
            }

        }
    }



    public JSONObject getReportInformation() throws JSONException {
        JSONObject report = new JSONObject();
        JSONObject user = new JSONObject(LoginViewModel.getUserFileInString());
        report.put(getApplication().getString(R.string.Waiter),user.get("codi"));

        report.put("idSala", idRoom);
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
        int idDish = selectedDish.getInt("selectedDishId");
        int timeSelected = selectedDish.getInt("timeSelected");
        String dishName = selectedDish.getString("selectedDishName");

        selDish = new SelectedDish(idDish, dishName, variants, timeSelected);
        return selDish;
    }

    private JSONObject getJsonToSendToOrderActivity(JSONObject result) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String importo = result.getString("importo");
        jsonObject.put("importo", importo);
        return jsonObject;
    }


    public JSONObject createJsonToSend(int idTable, String state) throws JSONException {
        JSONObject valuesToChange = new JSONObject();
        valuesToChange.put("idTavolo", idTable);
        valuesToChange.put("stato", state);
        return valuesToChange;
    }

}
