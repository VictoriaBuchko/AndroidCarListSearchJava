package com.example.myapplicationcar.util;


import com.example.myapplicationcar.R;
import com.example.myapplicationcar.model.Car;

import java.util.ArrayList;
import java.util.List;
public class CarRepository {

    public static List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car(R.drawable.car1, "Toyota", "Camry", 2015, "Відмінний стан, один власник", 18000));
        cars.add(new Car(R.drawable.car2, "BMW", "X5", 2018, "Повний привід, панорамний дах", 45000));
        cars.add(new Car(R.drawable.car3, "Audi", "A4", 2017, "Сервісна книга, без ДТП", 32000));
        cars.add(new Car(R.drawable.car4, "Mercedes", "C-Class", 2019, "Свіжопригнана, розмитнена", 52000));
        cars.add(new Car(R.drawable.car5, "Honda", "Civic", 2016, "Економічна, ідеальна для міста",  14000));
        cars.add(new Car(R.drawable.car1, "Toyota", "RAV4", 2020, "Гібрид, пробіг 30 000 км", 38000));
        cars.add(new Car(R.drawable.car2, "BMW", "3 Series",   2016, "Спортивний пакет M", 28000));
        cars.add(new Car(R.drawable.car3, "Volkswagen","Passat", 2014, "Дизель, автомат", 12000));
        cars.add(new Car(R.drawable.car4, "Skoda", "Octavia", 2018, "Повна комплектація", 17000));
        cars.add(new Car(R.drawable.car5, "Ford", "Focus", 2013, "Бюджетний варіант, є нюанси", 7000));
        cars.add(new Car(R.drawable.car1, "Hyundai",  "Tucson", 2019, "Корейська якість, кредит можливий",24000));
        cars.add(new Car(R.drawable.car2, "Kia", "Sportage", 2017, "Два комплекти гуми",  19000));
        cars.add(new Car(R.drawable.car3, "Mazda", "CX-5", 2020, "Без пробігу по Україні", 35000));
        cars.add(new Car(R.drawable.car4, "Nissan", "Leaf", 2021, "Електро, 200 км запас ходу", 22000));
        cars.add(new Car(R.drawable.car5, "Chevrolet","Cruze", 2015, "Газ/бензин, економ", 9000));

        return cars;
    }
}
