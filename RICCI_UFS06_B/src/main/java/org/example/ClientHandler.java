package org.example;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private String s;

    private ArrayList<Car> cars;

    private PrintWriter out;

    public ClientHandler(String s, ArrayList<Wine> wines, PrintWriter out) {
        this.s = s;
        this.wines = wines;
        this.out = out;
    }

    @Override
    public void run() {
        String answer = handleRequest(s);
        out.println(answer);
    }

    private String handleRequest(String s) {
        Gson gson = new Gson();
        String ans = gson.toJson(new Error("Not a command"));
        switch(s) {
            case "more_expensive":
                Car mostExp = cars.get(0);
                for (Car c :
                        cars) {
                    if (c.getPrezzo()>mostExp.getPrezzo()){
                        mostExp = c;
                    }
                }
                ans = gson.toJson(mostExp);
                break;
            case "all":
                ans = gson.toJson(cars);
                break;
            case "all_sorted_on_brand" :
                cars.sort((o1, o2) -> {
                    return o1.getMarca().compareTo(o2.getMarca());
                });
                ans = gson.toJson(cars);
                break;
            case "all_sorted_in_price" :
                cars.sort((o1, o2) -> {
                    if (o1.getPrezzo()>o2.getPrezzo())
                        return 1;
                    if (o1.getPrezzo()<o2.getPrezzo())
                        return -1;
                    return 0;
                });

                ans = gson.toJson(cars);
                break;
            default:
                // code block
        }
        return ans;
    }

}
