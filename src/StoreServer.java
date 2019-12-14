import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "C:\\Users\\charl\\Documents\\software\\store.db";

    public static void main(String[] args) {

        int port = 1000;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect(dbfile);

            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println("GET product with id = " + msg.data);
                    ProductModel p = adapter.loadProduct(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PRODUCT) {
                    ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                    System.out.println("PUT command with Product = " + p);
                    int res = adapter.saveProduct(p);
                    if (res == IDataAdapter.PRODUCT_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}