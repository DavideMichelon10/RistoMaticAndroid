package com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;

public final class InsertDishUtilities {
    private InsertDishUtilities() {
    }

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        InsertDishUtilities.context = context;
    }

    //Ritorna la posizione del piatto se il piatto è già presente nella lista, -1 altrimenti
    public static int findDishInCourse(SelectedDish insertedDish, int coursePosition) {
        for (int i = 0; i < CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size(); i++) {
            if (CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(i).getSelectedDishName().compareTo(insertedDish.getSelectedDishName()) == 0) {
                return i;
            }
        }
        return -1;
    }

    //Cambia timeSelectedDish
    public static void changeTimeSelectedDish(int coursePosition, int dishPosition, RecyclerView recyclerViewCourses) {
        int timeSelected = CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).getTimeSelected();
        CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).setTimeSelected(++timeSelected);
        ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                .findViewHolderForAdapterPosition(coursePosition))
                .getRecyclerViewCourse()
                .getAdapter()
                .notifyItemChanged(dishPosition);
    }
    //Cambia timeSelectedDish nel caso sia specificato
    public static void changeTimeSelectedDish(int coursePosition, int dishPosition, RecyclerView recyclerViewCourses, int timeSelected) {
        int currentTimeSelected = CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).getTimeSelected();
        CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).setTimeSelected(currentTimeSelected + timeSelected);
        ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                .findViewHolderForAdapterPosition(coursePosition))
                .getRecyclerViewCourse()
                .getAdapter()
                .notifyItemChanged(dishPosition);
    }

    //Inserisce il piatto all'interno della portata
    public static void insertDishInCourse(int coursePosition, SelectedDish insertedDish, RecyclerView recyclerViewCourses) {
        //Aggiungo insertedDish nella lista di piatti selezionati all'interno della portata
        CoursesAdapter.getCourses().get(coursePosition).addSelectedDish(insertedDish);
        //Ricavo la recyclerViewCourses

        //Chiamo il notifyItemInserted sull'adapter della portata e come position passo la grandezza della lista
        //di piatti selezionati meno 1
        ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                .findViewHolderForAdapterPosition(coursePosition))
                .getRecyclerViewCourse()
                .getAdapter()
                .notifyItemInserted(CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size() - 1);
    }
    //Inserisce il piatto all'interno della portata nel caso timeSelected sia specificato
    public static void insertDishInCourse(int coursePosition, SelectedDish insertedDish, RecyclerView recyclerViewCourses, int timeSelected) {
        insertedDish.setTimeSelected(timeSelected);
        //Aggiungo insertedDish nella lista di piatti selezionati all'interno della portata
        CoursesAdapter.getCourses().get(coursePosition).addSelectedDish(insertedDish);
        //Ricavo la recyclerViewCourses

        //Chiamo il notifyItemInserted sull'adapter della portata e come position passo la grandezza della lista
        //di piatti selezionati meno 1
        ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                .findViewHolderForAdapterPosition(coursePosition))
                .getRecyclerViewCourse()
                .getAdapter()
                .notifyItemInserted(CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size() - 1);
    }

    //Inserisce il piatto nel caso la portata esista già
    public static void handleInExistingCourse(int courseNumber, final SelectedDish insertedDish) {
        int coursePosition = 0;
        final RecyclerView recyclerViewCourses = ((OrderActivity) context)
                .findViewById(R.id.recyclerViewCourses);

        //ricava la posizione della portata esistente nella lista
        for (int i = 0; i < CoursesAdapter.getCourses().size(); i++) {
            if (CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber)) {
                coursePosition = i;
                break;
            }
        }
        final int dishPosition = findDishInCourse(insertedDish, coursePosition);
        try {
            if (dishPosition != -1) {
                changeTimeSelectedDish(coursePosition, dishPosition, recyclerViewCourses);
            } else {
                insertDishInCourse(coursePosition, insertedDish, recyclerViewCourses);
            }
        } catch (NullPointerException exeption) {
            final int finalCoursePosition = coursePosition;
            final int finalCoursePosition1 = coursePosition;
            recyclerViewCourses.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                            .findViewHolderForAdapterPosition(finalCoursePosition) != null) {
                        if (dishPosition != -1) {
                            changeTimeSelectedDish(finalCoursePosition1, dishPosition, recyclerViewCourses);
                        } else {
                            insertDishInCourse(finalCoursePosition1, insertedDish, recyclerViewCourses);
                        }
                    }
                }
            }, 30);
        }
    }

    //Inserisce il piatto nel caso la portata esista già e nel caso sia specificato il numero di piatti da aggiungere
    public static void handleInExistingCourse(int courseNumber, final SelectedDish insertedDish, final int timeSelected) {
        int coursePosition = 0;
        final RecyclerView recyclerViewCourses = ((OrderActivity) context)
                .findViewById(R.id.recyclerViewCourses);

        //ricava la posizione della portata esistente nella lista
        for (int i = 0; i < CoursesAdapter.getCourses().size(); i++) {
            if (CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber)) {
                coursePosition = i;
                break;
            }
        }
        final int dishPosition = findDishInCourse(insertedDish, coursePosition);
        try {
            if (dishPosition != -1) {
                changeTimeSelectedDish(coursePosition, dishPosition, recyclerViewCourses, timeSelected);
            } else {
                insertDishInCourse(coursePosition, insertedDish, recyclerViewCourses, timeSelected);
            }
        } catch (NullPointerException exeption) {
            final int finalCoursePosition = coursePosition;
            final int finalCoursePosition1 = coursePosition;
            recyclerViewCourses.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                            .findViewHolderForAdapterPosition(finalCoursePosition) != null) {
                        if (dishPosition != -1) {
                            changeTimeSelectedDish(finalCoursePosition1, dishPosition, recyclerViewCourses, timeSelected);
                        } else {
                            insertDishInCourse(finalCoursePosition1, insertedDish, recyclerViewCourses, timeSelected);
                        }
                    }
                }
            }, 30);
        }
    }

    //Inserisce il piatto nel caso la portata non esiste
    public static void insertDishInNewCourse(int courseNumber, SelectedDish insertedDish) {
        //La portata che verrà inserita
        Course courseInserted = new Course(courseNumber);
        //Aggiungi il piatto da inserire alla portata
        courseInserted.addSelectedDish(insertedDish);
        //Se indexCoursePosition è uguale a -1 allora basta fare l'add
        int indexCoursePosition = isCoursePositionOccupied(courseNumber);
        //Se la portata da inserire non va inserita in una posizione già occupata da un altra portata
        if (indexCoursePosition == -1) {
            CoursesAdapter.getCourses().add(courseInserted);
            ((RecyclerView) ((OrderActivity) context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(CoursesAdapter.getCourses().size());
        } else {
            CoursesAdapter.getCourses().add(indexCoursePosition, courseInserted);
            ((RecyclerView) ((OrderActivity) context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(indexCoursePosition);
        }
    }
    //Inserisce il piatto nel caso la portata non esiste e timeSelected è specificato
    public static void insertDishInNewCourse(int courseNumber, SelectedDish insertedDish, int timeSelected) {
        //Assegna al piatto il timeSelected specificato
        insertedDish.setTimeSelected(timeSelected);
        //La portata che verrà inserita
        Course courseInserted = new Course(courseNumber);
        //Aggiungi il piatto da inserire alla portata
        courseInserted.addSelectedDish(insertedDish);
        //Se indexCoursePosition è uguale a -1 allora basta fare l'add
        int indexCoursePosition = isCoursePositionOccupied(courseNumber);
        //Se la portata da inserire non va inserita in una posizione già occupata da un altra portata
        if (indexCoursePosition == -1) {
            CoursesAdapter.getCourses().add(courseInserted);
            ((RecyclerView) ((OrderActivity) context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(CoursesAdapter.getCourses().size());
        } else {
            CoursesAdapter.getCourses().add(indexCoursePosition, courseInserted);
            ((RecyclerView) ((OrderActivity) context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(indexCoursePosition);
        }
    }
    //Controlla se la portata da inserire esiste già
    public static boolean doesCourseExist(int courseNumber) {
        for (int i = 0; i < CoursesAdapter.getCourses().size(); i++) {
            if (CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber)) {
                return true;
            }
        }
        return false;
    }

    //Ritorna -1 se la portata va aggiunta in fondo e l'indice di dova va inserito se non va in fondo
    public static int isCoursePositionOccupied(int courseNumber) {
        System.out.println("isCoursePositionOccupied");
        for (int i = 0; i < CoursesAdapter.getCourses().size(); i++) {
            if (courseNumber < CoursesAdapter.getCourses().get(i).getCourseNumber()) {
                return i;
            }
        }
        return -1;
    }
}
