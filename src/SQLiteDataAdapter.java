
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter {

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    @Override
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = null;

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                product = new ProductModel();
                product.mProductID = rs.getInt("ProductId");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public int saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();
            ProductModel p = loadProduct(product.mProductID); // check if this product exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Products WHERE ProductID = " + product.mProductID);
            }

            String sql = "INSERT INTO Products(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_SAVE_FAILED;
        }

        return PRODUCT_SAVE_OK;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = null;

        try {
            String sql = "SELECT PurchaseId, CustomerId, ProductId, Quantity FROM Purchases WHERE PurchaseId = " + purchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                purchase = new PurchaseModel();
                purchase.mPurchaseID = rs.getInt("PurchaseId");
                purchase.mCustomerID = rs.getInt("CustomerId");
                purchase.mProductID = rs.getInt("ProductId");
                purchase.mQuantity = rs.getDouble("Quantity");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }
    @Override
    public int savePurchase(PurchaseModel purchase) {
        try {
            Statement stmt = conn.createStatement();
            PurchaseModel p = loadPurchase(purchase.mPurchaseID); // check if this purchase exists
            if (p != null) {
                stmt.executeUpdate("DELETE FROM Purchases WHERE PurchaseID = " + purchase.mPurchaseID);
            }

            String sql = "INSERT INTO Purchases VALUES " + purchase;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_SAVE_FAILED;
        }

        return PURCHASE_SAVE_OK;
    }

    public CustomerModel loadCustomer(int id) {
        CustomerModel customer = null;

        try {
            String sql = "SELECT * FROM Customers WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer = new CustomerModel();
                customer.mCustomerID = id;
                customer.mName = rs.getString("Name");
                customer.mPhone = rs.getString("Phone");
                customer.mAddress = rs.getString("Address");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public int saveCustomer(CustomerModel customer) {
        try {
            Statement stmt = conn.createStatement();
            CustomerModel c = loadCustomer(customer.mCustomerID); // check if this customer exists
            if (c != null) {
                stmt.executeUpdate("DELETE FROM Customers WHERE CustomerID = " + customer.mCustomerID);
            }

            String sql = "INSERT INTO Customers(CustomerId, Name, Phone, Address) VALUES " + customer;
            System.out.println(sql);

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_SAVE_FAILED;
        }

        return CUSTOMER_SAVE_OK;
    }
}
