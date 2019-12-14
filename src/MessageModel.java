public class MessageModel {

    public static final int GET_PRODUCT = 100;
    public static final int PUT_PRODUCT = 101;

    public static final int GET_CUSTOMER = 200;
    public static final int PUT_CUSTOMER = 201;

    public static final int GET_PURCHASE = 300;
    public static final int PUT_PURCHASE = 301;

    public static final int OPERATION_OK = 1000; // server responses!
    public static final int OPERATION_FAILED = 1001;

    public int code;
    public String data;

    public MessageModel() {
        code = 0;
        data = null;
    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }
}
