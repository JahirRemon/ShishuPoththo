package com.example.mdjahirulislam.shishupoththo.Fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdjahirulislam.shishupoththo.Activity.BabyHomeActivity;
import com.example.mdjahirulislam.shishupoththo.Activity.SetActiveImage;
import com.example.mdjahirulislam.shishupoththo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BabyInfoFragment extends Fragment {
    private static final String TAG = BabyInfoFragment.class.getSimpleName();

    BabyHomeActivity babyHomeActivity;
    private SetActiveImage setActiveImage;
    private LinearLayout male;
    private LinearLayout female;
    private EditText ageYearTV;
    private EditText ageMonthTV;
    private EditText ageDayTV;
    private EditText nameTV;

    private TextView ageCalendarTV;

    private int active = R.drawable.icon_bg_shape;
    private int inactive = R.drawable.icon_bg_shape_empty;
    private SimpleDateFormat sdf;
    private SimpleDateFormat viewSDF;
    private Calendar dob;
    private String childAge;

    private int childAgeDay=0;
    private int childAgeMonth=0;
    private int childAgeYear=0;

    private int monthArray[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};



    public BabyInfoFragment() {
        this.babyHomeActivity = babyHomeActivity;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate( R.layout.fragment_baby_info, container, false );
        Button nextButton = v.findViewById( R.id.nextBtn );
        male = v.findViewById( R.id.baby_maleLL );
        female = v.findViewById( R.id.baby_femaleLL );
        ageYearTV = v.findViewById(R.id.ageYearTV);
        ageMonthTV = v.findViewById(R.id.ageMonthTV);
        ageDayTV = v.findViewById(R.id.ageDayTV);
        nameTV = v.findViewById( R.id.nameTV );
        ageCalendarTV = v.findViewById( R.id.calenderText );

        viewSDF = new SimpleDateFormat("dd MMM yyyy");
        sdf = new SimpleDateFormat("dd MM yyyy");
        dob = Calendar.getInstance();


        male.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMaleBG();
            }
        } );

        female.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFemaleBG();
            }
        } );

        (v.findViewById( R.id.info_male )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMaleBG();
            }
        } );

        (v.findViewById( R.id.info_female )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setFemaleBG();
            }
        } );

        (v.findViewById( R.id.calenderIcon )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(v);
            }
        } );

        (v.findViewById( R.id.calenderText )).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender(v);
            }
        } );

        nextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTV.getText().toString();
                if (name.isEmpty()){
                    nameTV.setError("Required Name");
                }else if (ageCalendarTV.getText().toString().isEmpty() ) {

                    Toast.makeText(getActivity(), "Select Birth Date", Toast.LENGTH_SHORT).show();
//                    ageCalendarTV.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                    ageCalendarTV.setError("Required Birth Date");

                    ageCalendarTV.setHintTextColor(getResources().getColor(R.color.colorRed));
                }else {
                    Log.d(TAG, "onClick: "+ageCalendarTV.getText());
                    BabyDragCalculationFragment babyDragFragment = new BabyDragCalculationFragment();
                    Bundle args = new Bundle();
                    args.putString("name", name );
                    args.putInt("age", childAgeMonth+(childAgeYear*12));
                    args.putString("fullAge", childAge);
                    babyDragFragment.setArguments(args);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(new BabyInfoFragment());
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit);
                    fragmentTransaction.replace(R.id.fragmentContainer, babyDragFragment);
                    fragmentTransaction.commit();

                    setActiveImage.setActiveImage(true);


                }
            }
        } );

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach( activity );
        setActiveImage = (SetActiveImage) activity;
    }

    void setFemaleBG() {
        female.setBackground( getResources().getDrawable( R.drawable.icon_bg_shape ) );
        male.setBackground( null );
    }

    void setMaleBG() {
        male.setBackground( getResources().getDrawable( R.drawable.icon_bg_shape ) );
        female.setBackground( null );
    }
    public void showCalender(final View view) {
        new DatePickerDialog( getContext(), new DatePickerDialog.OnDateSetListener() {
            Calendar calendar=Calendar.getInstance();
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


                calendar.set( i,i1,i2 );
                ageCalendarTV.setText( viewSDF.format(calendar.getTime()));

                //                    dob.setTime(sdf.parse(ageCalendarTV.getText().toString()));

                childAge = CalculateAge(sdf.format(calendar.getTimeInMillis()));
                Log.d("Child pro ",childAge+" ----  "+sdf.format(calendar.getTimeInMillis()));
                String[] DoB = childAge.split(" ");
                childAgeYear = Integer.parseInt(DoB[0]);
                childAgeMonth = Integer.parseInt(DoB[2]);
                childAgeDay = Integer.parseInt(DoB[4]);

                ageYearTV.setText(String.valueOf(childAgeYear)+" Years");
                ageMonthTV.setText(String.valueOf(childAgeMonth)+" Month");
                ageDayTV.setText(String.valueOf(childAgeDay)+" Day");


            }
        } ,     Calendar.getInstance().get( Calendar.YEAR ),
                Calendar.getInstance().get( Calendar.MONTH ),
                Calendar.getInstance().get( Calendar.DAY_OF_MONTH )).show();
    }

    public static int getAge(Calendar dob) throws Exception {
        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }


        return age;
    }

    public String CalculateAge(String dateOfBirth ) {

        //split date of birth
        String[] dateOfBirthString= dateOfBirth.split(" ");
        String dayString=dateOfBirthString[0];
        String monthString=dateOfBirthString[1];
        String yearString=dateOfBirthString[2];


        int d=Integer.parseInt(dayString);
        int m=Integer.parseInt(monthString);
        int y=Integer.parseInt(yearString);



        int day = 0, month = 0, year = 0;

        Calendar age  = Calendar.getInstance(Locale.getDefault());

        int curDay = age.get(Calendar.DAY_OF_MONTH);
        int curMonth = age.get(Calendar.MONTH)+1;
        int curYear = age.get(Calendar.YEAR);


        if (curDay>= d && curMonth >= m) {
            day = curDay-d;
            month = curMonth - m;
            year = curYear-y;
        } else if (curDay<d && curMonth >= m) {
            day = curDay+monthArray[curMonth-2]-d;
            if ((curMonth-1)>=m){
                month = curMonth-1-m;
                year = curYear-y;

            }else {
                month = curMonth-1+12-m;
                year = curYear-1-y;

            }



        }
        else if (curDay>=d && curMonth<m) {
            day = curDay-d;
            month = curMonth+12-m;
            year = curYear-1-y;
        } else if (curDay<d && curMonth<m) {
            if (curMonth==1){
                day = curDay+monthArray[11]-d;
            }else {

                day = curDay+monthArray[curMonth-2]-d;
            }

            month = curMonth-1+12-m;
            year = curYear-1-y;
        }

        return String.valueOf(year+" Years "+month+" Month "+day+" Day ");
    }

}
