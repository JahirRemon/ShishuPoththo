package com.example.mdjahirulislam.shishupoththo.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdjahirulislam.shishupoththo.Activity.PrescriptionViewActivity;
import com.example.mdjahirulislam.shishupoththo.Adapter.MyAdapterForAutoComplete;
import com.example.mdjahirulislam.shishupoththo.R;
import com.example.mdjahirulislam.shishupoththo.model.WeightModelClass;
import com.example.mdjahirulislam.shishupoththo.model.DoseModelClass;
import com.example.mdjahirulislam.shishupoththo.model.MedicineModelClass;
import com.example.mdjahirulislam.shishupoththo.model.SuspensionModelClass;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabyDragCalculationFragment extends Fragment {

    private static final int AUTO_COMPLETE_DELAY = 300;
    private static final String TAG = BabyDragCalculationFragment.class.getSimpleName();
    private static final String CAPSULE = "Capsule";
    private static final String SUSPENSION = "Suspension";
    private static final String INJECTION = "Injection";
    private static final String TABLET = "Tablet";
    private TextView day;
    private TextView month;
    private TextView durationTV;
    private TextView summeryTV;
    private ImageView addIV;
    private Button completeBTN;

    private LinearLayout tab;
    private LinearLayout capsule;
    private LinearLayout syrup;
    private LinearLayout injection;
    private LinearLayout suspension;

    private ListView drugDetailsList;
    private AutoCompleteTextView medicineNameATV;
    //    private AutoCompleteAdapter autoSuggestAdapter;
    private MyAdapterForAutoComplete autoSuggestAdapter;
    private Handler handler;
    private int TRIGGER_AUTO_COMPLETE = 100;

    ArrayList<String> testList = new ArrayList<String>();
    ArrayList<String> medicineList=new ArrayList();
    ArrayList<MedicineModelClass> medicineModelClasses = new ArrayList<>();

//    String[] testList = {"Apple","Android", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

    private String patientName;
    private String patientAge;
    private int patientAgeOfMonth;


    public BabyDragCalculationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_baby_drag_calculation, container, false);
        day = view.findViewById(R.id.dragDayTV);
        month = view.findViewById(R.id.dragMonthTv);
        tab = view.findViewById(R.id.drag_tabLL);
        capsule = view.findViewById(R.id.drag_capsuleLL);
        syrup = view.findViewById(R.id.drag_syrup);
        injection = view.findViewById(R.id.drag_injectionLL);
        suspension = view.findViewById(R.id.drag_suspension);
        medicineNameATV = view.findViewById(R.id.medicineNameATV);
        drugDetailsList = view.findViewById(R.id.my_list_view);
        completeBTN = view.findViewById(R.id.completeBTN);

        durationTV = view.findViewById(R.id.durationTV);
        summeryTV = view.findViewById(R.id.summeryTV);
        addIV = view.findViewById(R.id.plusIV);


        patientName = getArguments().getString("name");
        patientAge = getArguments().getString("fullAge");
        patientAgeOfMonth = getArguments().getInt("age");


        final ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,medicineList);
        drugDetailsList.setAdapter(arrayAdapter);



        Log.d(TAG, "onCreateView: " + patientName + "     -----   age   " + patientAgeOfMonth);

        medicineModelClasses = loadData();


        autoSuggestAdapter = new MyAdapterForAutoComplete(getContext(), R.layout.custome_row_design, medicineModelClasses);
        medicineNameATV.setThreshold(1);  //will start working from first character
        medicineNameATV.setAdapter(autoSuggestAdapter);


        medicineNameATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MedicineModelClass drugs = (MedicineModelClass) adapterView.getItemAtPosition(i);
//                fruitDesc.setText(fruit.getDesc());
                Log.d(TAG, "onItemClick: " + drugs.getDoseList());


                for (int j = 0; j < drugs.getDoseList().size(); j++) {

                    DoseModelClass doses = drugs.getDoseList().get(j);

                    if (drugs.getDrugType().equalsIgnoreCase(CAPSULE) || drugs.getDrugType().equalsIgnoreCase(TABLET)) {
                        if (patientAgeOfMonth <= doses.getEndMonth() && patientAgeOfMonth >= doses.getStartMonth()) {
                            Log.d(TAG, "1. onItemClick: age--> " + patientAgeOfMonth + "     endmonth---->" + drugs.getDoseList().get(j).getEndMonth());
                            summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + doses.getDosesQuantity() + " mg, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");
                        }
                    } else if (drugs.getDrugType().equalsIgnoreCase(SUSPENSION)) {
                        if (patientAgeOfMonth <= doses.getEndMonth() && patientAgeOfMonth >= doses.getStartMonth()) {

                            double tsfUnite = -1;
                            double weight = -1;
                            for (int k = 0; k < loadTSFData().size(); k++) {
                                if (drugs.getId() == loadTSFData().get(k).getDrugId()) {
                                    tsfUnite = loadTSFData().get(k).getSuspensionTSFUnite();
                                    Log.d(TAG, "2. onItemClick: tsf--->" + tsfUnite);
                                }
                            }

                            for (int k = 0; k < getAge().size(); k++) {
                                WeightModelClass weightModelClass = getAge().get(k);
                                if (patientAgeOfMonth <= weightModelClass.getEndMonth() && patientAgeOfMonth >= weightModelClass.getStartMonth()) {
                                    weight = weightModelClass.getWeightKG();
                                }
                            }
                            if (doses.getIsWeightSensitive() == 1) {
                                Log.d(TAG, "2. onItemClick: doses.getDosesQuantity---> " + doses.getDosesQuantity());
                                String result = new DecimalFormat("##.##").format((doses.getDosesQuantity()*weight) / tsfUnite);
                                summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + result + " tsf, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");
                            }else {
                                Log.d(TAG, "2. onItemClick: doses.getDosesQuantity---> " + doses.getDosesQuantity());
                                String result = new DecimalFormat("##.##").format(doses.getDosesQuantity() / tsfUnite);
                                summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + result + " tsf, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");

                            }
                        }
                    } else {
                        if (patientAgeOfMonth <= doses.getEndMonth() && patientAgeOfMonth >= doses.getStartMonth()) {

                            double weight = -1;

                            for (int k = 0; k < getAge().size(); k++) {
                                WeightModelClass weightModelClass = getAge().get(k);
                                if (patientAgeOfMonth <= weightModelClass.getEndMonth() && patientAgeOfMonth >= weightModelClass.getStartMonth()) {
                                    Log.d(TAG, "3. onItemClick: age--> " + patientAgeOfMonth + "     endmonth---->" + drugs.getDoseList().get(j).getEndMonth());
                                    weight = weightModelClass.getWeightKG();
                                }
                            }


                            if (doses.getIsWeightSensitive() == 1) {
                                Log.d(TAG, "2. onItemClick: doses.getDosesQuantity---> " + doses.getDosesQuantity());
                                String result = new DecimalFormat("##.##").format((doses.getDosesQuantity()*weight));
//                                summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + result + " tsf, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");
                                summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + result + " mg, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");

                            }else {
                                Log.d(TAG, "2. onItemClick: doses.getDosesQuantity---> " + doses.getDosesQuantity());
                                String result = new DecimalFormat("##.##").format(doses.getDosesQuantity());
                                summeryTV.setText(drugs.getDrugType() + " " + drugs.getDrugName() + " - " + result + " mg, " + 24 / doses.getInterval() + " Times - " + durationTV.getText() + " Day");

                            }
                        }
                    }

                }
            }
        });


        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day.setBackground(null);
                day.setTextColor(getResources().getColor(R.color.colorHintColor));
                month.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
                month.setTextColor(getResources().getColor(R.color.colorTitleColor));

            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month.setBackground(null);
                month.setTextColor(getResources().getColor(R.color.colorHintColor));
                day.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
                day.setTextColor(getResources().getColor(R.color.colorTitleColor));

            }
        });

        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTab();
            }
        });
        capsule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCapsule();
            }
        });
        syrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSyrup();
            }
        });
        injection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInjection();
            }
        });

        suspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSuspension();
            }
        });


        ((TextView) view.findViewById(R.id.tabText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTab();
            }
        });
        ((TextView) view.findViewById(R.id.syrupText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSyrup();
            }
        });
        ((TextView) view.findViewById(R.id.capsuleText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCapsule();
            }
        });
        ((TextView) view.findViewById(R.id.injectionText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInjection();
            }
        });
        ((TextView) view.findViewById(R.id.suspensionText)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSuspension();
            }
        });


        addIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: work not done
//                Toast.makeText(getActivity(), "Not working", Toast.LENGTH_SHORT).show();
                medicineList.add(summeryTV.getText().toString());
                arrayAdapter.notifyDataSetChanged();

            }
        });

        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), PrescriptionViewActivity.class)
                        .putExtra("name",patientName)
                        .putExtra("age",patientAge)
                        .putExtra("list",medicineList)
                );

            }
        });

        return view;

    }

    private void setSyrup() {

        tab.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        capsule.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        syrup.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
        injection.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        suspension.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));

    }

    private void setCapsule() {
        tab.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        capsule.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
        syrup.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        injection.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        suspension.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
    }

    void setTab() {
        tab.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
        capsule.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        syrup.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        injection.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        suspension.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
    }

    void setInjection() {
        tab.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        capsule.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        syrup.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        injection.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
        suspension.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
    }

    void setSuspension() {
        tab.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        capsule.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        syrup.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        injection.setBackground(getResources().getDrawable(R.drawable.edit_text_bg_shape));
        suspension.setBackground(getResources().getDrawable(R.drawable.icon_bg_shape));
    }

    public ArrayList<SuspensionModelClass> loadTSFData() {
        ArrayList<SuspensionModelClass> tsfUnite = new ArrayList<>();
        tsfUnite.add(new SuspensionModelClass(1, 9, 125));
        tsfUnite.add(new SuspensionModelClass(2, 12, 100));
        tsfUnite.add(new SuspensionModelClass(3, 14, 125));
        tsfUnite.add(new SuspensionModelClass(4, 18, 200));

        return tsfUnite;
    }

    public ArrayList<WeightModelClass> getAge() {
        ArrayList<WeightModelClass> weight = new ArrayList<>();
        weight.add(new WeightModelClass(0, 2, 3.3));
        weight.add(new WeightModelClass(3, 5, 6));
        weight.add(new WeightModelClass(6, 8, 7.8));
        weight.add(new WeightModelClass(9, 11, 9.2));
        weight.add(new WeightModelClass(12, 23, 10.2));
        weight.add(new WeightModelClass(24, 35, 12.3));
        weight.add(new WeightModelClass(36, 47, 14.6));
        weight.add(new WeightModelClass(48, 59, 16.7));
        weight.add(new WeightModelClass(60, 71, 18.7));
        weight.add(new WeightModelClass(72, 83, 20.7));
        weight.add(new WeightModelClass(84, 95, 22.9));
        weight.add(new WeightModelClass(96, 107, 25.3));
        weight.add(new WeightModelClass(108, 119, 28.1));
        weight.add(new WeightModelClass(120, 131, 31.4));
        weight.add(new WeightModelClass(132, 144, 32.2));

        return weight;
    }


    public ArrayList<MedicineModelClass> loadData() {
        ArrayList<MedicineModelClass> dataList = new ArrayList<>();
        ArrayList<DoseModelClass> doseList = new ArrayList<>();
        doseList.add(new DoseModelClass(0, 0, 6, 216, 50, 24, 1));

//        -------------Oral-----------------

        ArrayList<DoseModelClass> amoxicillinCapsuleDoseList = new ArrayList<>();
        amoxicillinCapsuleDoseList.add(new DoseModelClass(1, 8, 6, 12, 62.5, 8, 0));
        amoxicillinCapsuleDoseList.add(new DoseModelClass(2, 8, 13, 60, 125, 8, 0));
        amoxicillinCapsuleDoseList.add(new DoseModelClass(3, 8, 61, 216, 250, 8, 0));

        ArrayList<DoseModelClass> amoxicillinSuspensionDoseList = new ArrayList<>();

        amoxicillinSuspensionDoseList.add(new DoseModelClass(4, 9, 6, 12, 62.5, 8, 0));
        amoxicillinSuspensionDoseList.add(new DoseModelClass(5, 9, 13, 60, 125, 8, 0));
        amoxicillinSuspensionDoseList.add(new DoseModelClass(6, 9, 61, 216, 250, 8, 0));


        ArrayList<DoseModelClass> CefiximeTabletDoseList = new ArrayList<>();

        CefiximeTabletDoseList.add(new DoseModelClass(19, 11, 6, 12, 75, 24, 0));
        CefiximeTabletDoseList.add(new DoseModelClass(20, 11, 13, 48, 100, 24, 0));
        CefiximeTabletDoseList.add(new DoseModelClass(21, 11, 49, 120, 200, 24, 0));
        CefiximeTabletDoseList.add(new DoseModelClass(22, 11, 121, 216, 400, 24, 0));

        ArrayList<DoseModelClass> CefiximeSuspensionDoseList = new ArrayList<>();

        CefiximeSuspensionDoseList.add(new DoseModelClass(23, 12, 6, 12, 75, 24, 0));
        CefiximeSuspensionDoseList.add(new DoseModelClass(24, 12, 13, 48, 100, 24, 0));
        CefiximeSuspensionDoseList.add(new DoseModelClass(25, 12, 49, 120, 200, 24, 0));
        CefiximeSuspensionDoseList.add(new DoseModelClass(26, 12, 121, 216, 400, 24, 0));


        ArrayList<DoseModelClass> CofurixumeTabletDoseList = new ArrayList<>();
        CofurixumeTabletDoseList.add(new DoseModelClass(27, 13, 6, 216, 30, 12, 1));

        ArrayList<DoseModelClass> CofurixumeSuspensionDoseList = new ArrayList<>();
        CofurixumeSuspensionDoseList.add(new DoseModelClass(28, 14, 6, 216, 30, 12, 1));

        ArrayList<DoseModelClass> AzithromycinCapsulDoseList = new ArrayList<>();
        AzithromycinCapsulDoseList.add(new DoseModelClass(30, 16, 6, 216, 10, 24, 1));


        ArrayList<DoseModelClass> AzithromycinTabletDoseList = new ArrayList<>();

        AzithromycinTabletDoseList.add(new DoseModelClass(31, 17, 6, 216, 10, 24, 1));


        ArrayList<DoseModelClass> AzithromycinSuspensionDoseList = new ArrayList<>();

        AzithromycinSuspensionDoseList.add(new DoseModelClass(32, 18, 6, 216, 10, 24, 1));


//        ----------------Injectable------------------


        ArrayList<DoseModelClass> amoxicillinInjectionDoseList = new ArrayList<>();
        amoxicillinInjectionDoseList.add(new DoseModelClass(7, 10, 6, 216, 25, 8, 1));


        ArrayList<DoseModelClass> CofurixumeInjectionDoseList = new ArrayList<>();

        CofurixumeInjectionDoseList.add(new DoseModelClass(29, 15, 6, 216, 30, 12, 1));


//        ----------------Injectable------------------


        ArrayList<DoseModelClass> CeftriaxoneInjectionDoseList = new ArrayList<>();
        CeftriaxoneInjectionDoseList.add(new DoseModelClass(8, 0, 6, 216, 50, 24, 1));

        ArrayList<DoseModelClass> CefotaximeInjectionDoseList = new ArrayList<>();
        CefotaximeInjectionDoseList.add(new DoseModelClass(9, 1, 6, 216, 75, 12, 1));

        ArrayList<DoseModelClass> CeftazidimeInjectionDoseList = new ArrayList<>();
        CeftazidimeInjectionDoseList.add(new DoseModelClass(10, 2, 6, 216, 25, 8, 1));

        ArrayList<DoseModelClass> MeropenemInjectionDoseList = new ArrayList<>();
        MeropenemInjectionDoseList.add(new DoseModelClass(11, 3, 6, 216, 20, 8, 1));

        ArrayList<DoseModelClass> VancomycinInjectionDoseList = new ArrayList<>();
        VancomycinInjectionDoseList.add(new DoseModelClass(12, 4, 6, 216, 15, 8, 1));

        ArrayList<DoseModelClass> AmikacinInjectionDoseList = new ArrayList<>();
        AmikacinInjectionDoseList.add(new DoseModelClass(13, 5, 6, 216, 7.5, 12, 1));

        ArrayList<DoseModelClass> GentomicineInjectionDoseList = new ArrayList<>();
        GentomicineInjectionDoseList.add(new DoseModelClass(14, 6, 6, 144, 2.5, 8, 1));
        GentomicineInjectionDoseList.add(new DoseModelClass(15, 6, 145, 216, 2, 8, 1));

        ArrayList<DoseModelClass> MetronidazoleInjectionDoseList = new ArrayList<>();
        MetronidazoleInjectionDoseList.add(new DoseModelClass(16, 7, 6, 12, 125, 8, 0));
        MetronidazoleInjectionDoseList.add(new DoseModelClass(17, 7, 13, 60, 250, 8, 0));
        MetronidazoleInjectionDoseList.add(new DoseModelClass(18, 7, 61, 120, 500, 8, 0));


        dataList.add(new MedicineModelClass(0, "Ceftriaxone", INJECTION, CeftriaxoneInjectionDoseList));
        dataList.add(new MedicineModelClass(1, "Cefotaxime", INJECTION, CefotaximeInjectionDoseList));
        dataList.add(new MedicineModelClass(2, "Ceftazidime", INJECTION, CeftazidimeInjectionDoseList));
        dataList.add(new MedicineModelClass(3, "Meropenem", INJECTION, MeropenemInjectionDoseList));
        dataList.add(new MedicineModelClass(4, "Vancomycin", INJECTION, VancomycinInjectionDoseList));
        dataList.add(new MedicineModelClass(5, "Amikacin", INJECTION, AmikacinInjectionDoseList));
        dataList.add(new MedicineModelClass(6, "Gentomicine", INJECTION, GentomicineInjectionDoseList));
        dataList.add(new MedicineModelClass(7, "Metronidazole", INJECTION, MetronidazoleInjectionDoseList));
        dataList.add(new MedicineModelClass(8, "Amoxicillin", CAPSULE, amoxicillinCapsuleDoseList));
        dataList.add(new MedicineModelClass(9, "Amoxicillin", SUSPENSION, amoxicillinSuspensionDoseList));
        dataList.add(new MedicineModelClass(10, "Amoxicillin", INJECTION, amoxicillinInjectionDoseList));
        dataList.add(new MedicineModelClass(11, "Cefixime", TABLET, CefiximeTabletDoseList));
        dataList.add(new MedicineModelClass(12, "Cefixime", SUSPENSION, CefiximeSuspensionDoseList));
        dataList.add(new MedicineModelClass(13, "Cofurixume", TABLET, CofurixumeTabletDoseList));
        dataList.add(new MedicineModelClass(14, "Cofurixume", SUSPENSION, CofurixumeSuspensionDoseList));
        dataList.add(new MedicineModelClass(15, "Cofurixume", INJECTION, CofurixumeInjectionDoseList));
        dataList.add(new MedicineModelClass(16, "Azithromycin", CAPSULE, AzithromycinCapsulDoseList));
        dataList.add(new MedicineModelClass(17, "Azithromycin", TABLET, AzithromycinTabletDoseList));
        dataList.add(new MedicineModelClass(18, "Azithromycin", SUSPENSION, AzithromycinSuspensionDoseList));


        return dataList;
    }


}
