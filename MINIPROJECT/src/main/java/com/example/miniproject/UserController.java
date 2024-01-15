package com.example.miniproject;

public class UserController {
    private static String place;

    private static String email;

    private static String id;

    private static int amount;

    private static double totalamount;

    private static int discount;

    private static int person;

    private static String bid;

    public static String getplace() {

        return place;
    }

    public static void setplace(String userplace) {
        place = userplace;
    }

    public static String getemail() {

        return email;
    }

    public static void setemail(String useremail) {
        email = useremail;
    }

    public static String getid() {

        return id;
    }

    public static void setid(String userid) {
        id = userid;
    }

    public static int getamount() {

        return amount;
    }

    public static void setamount(int useramount) {
        amount = useramount;
    }

    public static double gettotalamount() {

        return totalamount;
    }

    public static void settotalamount(double usertotalamount) {

        totalamount = usertotalamount;
    }
    public static int getdiscount() {

        return discount;
    }

    public static void setdiscount(int userdiscount) {

        discount = userdiscount;
    }

    public static int getperson() {

        return person;
    }

    public static void setperson(int userperson) {

        person = userperson;
    }
    public static String getbid() {

        return bid;
    }

    public static void setbid(String userbid) {
        bid = userbid;
    }
}

