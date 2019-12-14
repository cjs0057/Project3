
public class OracleDataAdapter implements IDataAdapter {
    public int connect(String dbfile) {
        //...
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        // ...
        return CONNECTION_CLOSE_OK;

    }
    public CustomerModel loadCustomer(int id) {
        return null;
    }
    public int saveCustomer(CustomerModel model) {
        return CUSTOMER_SAVE_OK;
    }

    public ProductModel loadProduct(int id) {
        return null;
    }
    public int saveProduct(ProductModel model) {
        return PRODUCT_SAVE_OK;
    }

    public PurchaseModel loadPurchase(int id) {
        return null;
    }
    public int savePurchase(PurchaseModel model) {
        return PURCHASE_SAVE_OK;
    }

}
