package com.example.myapplicationcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationcar.adapter.CarAdapter;
import com.example.myapplicationcar.model.Car;
import com.example.myapplicationcar.util.CarRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_FILTERED_CARS = "FILTERED_CARS";
    private CarAdapter carAdapter;
    private List<Car> allCars;

    private final ActivityResultLauncher<Intent> searchLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            @SuppressWarnings("unchecked")
                            List<Car> filtered = (List<Car>)
                                    result.getData().getSerializableExtra(EXTRA_FILTERED_CARS);
                            if (filtered != null) {
                                carAdapter.updateData(filtered);
                            }
                        } else {

                            carAdapter.updateData(allCars);//якщо пошук скасовано то повертаєтся повний список
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allCars = CarRepository.getCars();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carAdapter = new CarAdapter(this, allCars);
        recyclerView.setAdapter(carAdapter);
    }

    private static final int MENU_SEARCH = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_SEARCH, Menu.NONE, "Пошук")
                .setIcon(android.R.drawable.ic_menu_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_SEARCH) {
            Intent intent = new Intent(this, SearchActivity.class);
            searchLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}