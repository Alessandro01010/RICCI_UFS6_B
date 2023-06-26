import com.google.gson.Gson;
import org.example.Wine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App
{
    static Gson gson = new Gson();
    private static int SERVER_PORT = 1234;

    static ArrayList<Wine> wines = new ArrayList<Wine>();



    public static void main(String[] args)
    {
        buildList();
        System.out.println("Server started!");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Errore apertura Server Socket");
        }
        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Errore serverSocket.accept() ");
            }
            Socket finalClientSocket = clientSocket;
            new Thread(() -> {
                read(finalClientSocket);
            }).start();
        }
    }

    static boolean  read(Socket clientSocket) {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(clientSocket.getInetAddress()+ " connected");
        } catch (IOException e) {
            System.out.println("Cannot read buffer");
            return false;
        }

        PrintWriter out = null; // allocate to write answer to client.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("can't create PrintWriter");
        }

        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                //String answer = handleRequest(s);
                //out.println(answer);
                ClientHandler ro = new ClientHandler(s,cars,out);
                Thread thread = new Thread(ro);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println(clientSocket.getInetAddress()+ " Disconnected");
            return false;
        }
        return true;
    }

    private static String handleRequest(String s) {
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
    static void buildList() {
        wines.add(new Wine(123,"Dom Perignon Vintage Moet & Chandon 2008",3594.9, 2));
        wines.add(new Wine(3634,"Pignoli Radikon Radikon 2009",38346.9, 1));
        wines.add(new Wine(135,"Pinot Nero Elena Walch Elena Walch 2018",130000.4, 10));
        System.out.println(wines);
    }
}

