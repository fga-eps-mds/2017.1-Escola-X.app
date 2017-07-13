package helper;

public class MaskHelper {

    public String phoneMask(String phone) {

        String phoneMask = "";
        int finalString = phone.length();

        phoneMask = phone.substring(0,2);
        phoneMask = phoneMask.concat("9");
        phoneMask = phoneMask.concat("" + phone.substring(2,finalString));

        return phoneMask;
    }
}
