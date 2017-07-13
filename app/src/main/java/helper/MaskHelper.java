package helper;

public class MaskHelper {

    public String phoneMask(String phone) {

        String phoneMask = "";
        int finalString = phone.indexOf("");

        phoneMask = phone.substring(0,2);
        phoneMask = phoneMask.concat("9");
        phoneMask = phoneMask.concat("" + phone.substring(2,10));

        return phoneMask;
    }
}
