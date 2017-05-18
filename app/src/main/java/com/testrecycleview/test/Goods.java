package com.testrecycleview.test;

import android.graphics.Bitmap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by meyhuan on 15/5/13.
 */
public class Goods implements Serializable {

    public static final String URL_FRESH_NEWS = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=";
    private String name;
    private String des;
    private String price;
    private String address;
    private String imageUrl;
    private Bitmap image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public static ArrayList<Goods> parse(String json){

        JSONObject resultObj = null;
        ArrayList<Goods> goodsArrayList = new ArrayList<>();
        try {
            resultObj = new JSONObject(json);
            JSONArray postsArray = resultObj.optJSONArray("posts");



            for (int i = 0; i < postsArray.length(); i++) {

                Goods goods = new Goods();
                JSONObject jsonObject = postsArray.getJSONObject(i);

                goods.setName(jsonObject.optString("title"));
                goods.setAddress(jsonObject.optString("date"));
                goods.setPrice(jsonObject.optString("id"));
                JSONObject jsonObject1 = jsonObject.getJSONObject("custom_fields");
                JSONArray jsonArray = jsonObject1.optJSONArray("thumb_c");
                String url = jsonArray.optString(0);
//                if(!TextUtil.isNull(url)){
                    goods.setImageUrl(url);
//                }
                goodsArrayList.add(goods);


            }

            return goodsArrayList;

        } catch (JSONException e) {

        }
        return goodsArrayList;

    }


}
