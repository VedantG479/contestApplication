package com.example.userapp.Orders;

public class Choose_Squad_Data {
    private String vid,name,p1ID,p2ID,p3ID,p4ID,P1N,P2N,P3N,P4N,ref_no;

    public Choose_Squad_Data() {
    }

    public Choose_Squad_Data(String vid, String name, String p1ID, String p2ID, String p3ID, String p4ID, String p1N, String p2N, String p3N, String p4N, String ref_no) {
        this.vid = vid;
        this.name = name;
        this.p1ID = p1ID;
        this.p2ID = p2ID;
        this.p3ID = p3ID;
        this.p4ID = p4ID;
        P1N = p1N;
        P2N = p2N;
        P3N = p3N;
        P4N = p4N;
        this.ref_no = ref_no;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getP1ID() {
        return p1ID;
    }

    public void setP1ID(String p1ID) {
        this.p1ID = p1ID;
    }

    public String getP2ID() {
        return p2ID;
    }

    public void setP2ID(String p2ID) {
        this.p2ID = p2ID;
    }

    public String getP3ID() {
        return p3ID;
    }

    public void setP3ID(String p3ID) {
        this.p3ID = p3ID;
    }

    public String getP4ID() {
        return p4ID;
    }

    public void setP4ID(String p4ID) {
        this.p4ID = p4ID;
    }

    public String getP1N() {
        return P1N;
    }

    public void setP1N(String p1N) {
        P1N = p1N;
    }

    public String getP2N() {
        return P2N;
    }

    public void setP2N(String p2N) {
        P2N = p2N;
    }

    public String getP3N() {
        return P3N;
    }

    public void setP3N(String p3N) {
        P3N = p3N;
    }

    public String getP4N() {
        return P4N;
    }

    public void setP4N(String p4N) {
        P4N = p4N;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }
}
