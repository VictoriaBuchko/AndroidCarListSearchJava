package com.example.myapplicationcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplicationcar.model.Car;
import com.example.myapplicationcar.util.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class SearchActivity extends AppCompatActivity {

    private static final int COST_ABS_MIN = 1000;
    private static final int COST_ABS_MAX = 10000000;

    private AutoCompleteTextView acBrand, acModel;
    private Spinner spinnerYearFrom, spinnerYearTo;
    private EditText etCostFrom, etCostTo;
    private TextView tvCostError;
    private Button btnMatches;
    private TextView tvMatchCount;

    private List<Car> allCars;
    private List<Car> filteredCars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Car search");
        }

        allCars = CarRepository.getCars();

        acBrand = findViewById(R.id.acBrand);
        acModel = findViewById(R.id.acModel);
        spinnerYearFrom = findViewById(R.id.spinnerYearFrom);
        spinnerYearTo = findViewById(R.id.spinnerYearTo);
        etCostFrom = findViewById(R.id.etCostFrom);
        etCostTo = findViewById(R.id.etCostTo);
        tvCostError = findViewById(R.id.tvCostError);
        btnMatches = findViewById(R.id.btnMatches);
        tvMatchCount = findViewById(R.id.tvMatchCount);

        setupAutoComplete();
        setupYearSpinners();
        setupListeners();

        btnMatches.setEnabled(false);
    }

    private void setupAutoComplete() {

        TreeSet<String> brands = new TreeSet<>();
        TreeSet<String> models = new TreeSet<>();

        for (Car car : allCars) {
            brands.add(car.getBrand());
            models.add(car.getModel());
        }

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(brands));

        acBrand.setAdapter(brandAdapter);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(models));

        acModel.setAdapter(modelAdapter);
    }

    //роки випуску машин з колекції для пошуку
    private void setupYearSpinners() {

        TreeSet<Integer> years = new TreeSet<>();

        for (Car car : allCars) {
            years.add(car.getYear());
        }

        List<String> yearList = new ArrayList<>();
        yearList.add("Будь-який");

        for (int y : years) {
            yearList.add(String.valueOf(y));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, yearList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerYearFrom.setAdapter(adapter);
        spinnerYearTo.setAdapter(adapter);

        spinnerYearTo.setSelection(yearList.size() - 1);
    }

    private void setupListeners() {

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                doSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        acBrand.addTextChangedListener(textWatcher);
        acModel.addTextChangedListener(textWatcher);

        AdapterView.OnItemSelectedListener spinnerListener =
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> p, android.view.View v, int pos, long id) {
                        doSearch();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> p) {}
                };

        spinnerYearFrom.setOnItemSelectedListener(spinnerListener);
        spinnerYearTo.setOnItemSelectedListener(spinnerListener);

        etCostFrom.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                doSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etCostTo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                doSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnMatches.setOnClickListener(v -> {

            Intent data = new Intent();
            data.putExtra(MainActivity.EXTRA_FILTERED_CARS, new ArrayList<>(filteredCars));

            setResult(RESULT_OK, data);
            finish();
        });
    }

    private int parseCostField(EditText field, int defaultValue) {

        String s = field.getText().toString().trim();

        if (s.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void doSearch() {

        String brand = acBrand.getText().toString().trim().toLowerCase();
        String model = acModel.getText().toString().trim().toLowerCase();

        //рік від
        int yearFrom = 0;

        if (spinnerYearFrom.getSelectedItemPosition() > 0) {
            yearFrom = Integer.parseInt((String) spinnerYearFrom.getSelectedItem());
        }

        //рік до
        int yearTo = Integer.MAX_VALUE;

        if (spinnerYearTo.getSelectedItemPosition() > 0) {
            yearTo = Integer.parseInt((String) spinnerYearTo.getSelectedItem());
        }

        boolean costFromFilled = !etCostFrom.getText().toString().trim().isEmpty();
        boolean costToFilled = !etCostTo.getText().toString().trim().isEmpty();

        int costFrom = costFromFilled ? parseCostField(etCostFrom, 0) : Integer.MIN_VALUE;
        int costTo = costToFilled ? parseCostField(etCostTo, Integer.MAX_VALUE) : Integer.MAX_VALUE;

        boolean costError = false;

        if (costFrom == -1 || costTo == -1) {
            costError = true;
        }

        if (costFromFilled && (costFrom < COST_ABS_MIN || costFrom > COST_ABS_MAX)) {
            costError = true;
        }

        if (costToFilled && (costTo < COST_ABS_MIN || costTo > COST_ABS_MAX)) {
            costError = true;
        }

        if (costError) {
            tvCostError.setText("Введіть числа від " + COST_ABS_MIN + " до " + COST_ABS_MAX);
        } else {
            tvCostError.setText("");
        }

        //перевіряємо чи хоча б одне поле заповнене
        boolean anyFieldFilled =
                !brand.isEmpty()
                        || !model.isEmpty()
                        || spinnerYearFrom.getSelectedItemPosition() > 0
                        || spinnerYearTo.getSelectedItemPosition() > 0
                        || costFromFilled
                        || costToFilled;

        if (!anyFieldFilled) {

            filteredCars.clear();
            btnMatches.setEnabled(false);
            btnMatches.setText("0 matches");
            tvMatchCount.setText("Заповніть хоча б одне поле");
            return;
        }

        if (costError) {

            filteredCars.clear();
            btnMatches.setEnabled(false);
            btnMatches.setText("0 matches");
            tvMatchCount.setText("");
            return;
        }

        filteredCars.clear();

        for (Car car : allCars) {//фільтрація

            boolean matches = true;

            if (!brand.isEmpty() && !car.getBrand().toLowerCase().contains(brand)) {
                matches = false;
            }

            if (!model.isEmpty() && !car.getModel().toLowerCase().contains(model)) {
                matches = false;
            }

            if (yearFrom > 0 && car.getYear() < yearFrom) {
                matches = false;
            }

            if (yearTo < Integer.MAX_VALUE && car.getYear() > yearTo) {
                matches = false;
            }

            // ✔ ВАЖНО: цена учитывается только если введена
            if (costFromFilled && car.getCost() < costFrom) {
                matches = false;
            }

            if (costToFilled && car.getCost() > costTo) {
                matches = false;
            }

            if (matches) {
                filteredCars.add(car);
            }
        }

        int count = filteredCars.size();

        tvMatchCount.setText(count + " matches");
        btnMatches.setText(count + " matches");
        btnMatches.setEnabled(count > 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//стрілка назад

        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}