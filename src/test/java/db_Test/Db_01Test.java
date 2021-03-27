package db_Test;

import org.junit.Test;
import org.testng.Assert;
import utilities.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Db_01Test {

    @Test
    public void name() {

        String query="select * from categories";

        System.out.println(DatabaseConnector.getQueryResultWithAListMap(query));
    }

    @Test
    public void TC01() throws SQLException {

        //her bir kategori id'ye göre kaç tane ürün olduğunu bulun ve bunları azalan sıralama ile yazın
        String query="select product_name ,category_id from products\n" +
                "order by category_id desc\n";

        List<Map<String,String>> producName=DatabaseConnector.getQueryAsAListOfMaps(query);

        System.out.println(producName.size());

        for (Map w:producName){
            System.out.println(w.values());
        }
    }

    @Test
    public void TC02() throws SQLException {

        //contact title i owner olan
        // ve region degerleri null olan kisilerin 13 kisi oldugunu dogrulayiniz. (customer tablosu kullanilacak)

        String quer="select  count(*) as bos   from customers\n" +
                "where contact_title='Owner' and region is null \n" +
                "union all\n" +
                "select count (region)  as resultNotNull from customers\n" +
                "where contact_title='Owner' and region is Not null ";

        List<Map<String,String>> list=DatabaseConnector.getQueryAsAListOfMaps(quer);

        System.out.println(list.get(0).get("bos"));
        Assert.assertEquals(list.get(0).get("bos"),"13");

        System.out.println(list.get(1).get("bos"));
        Assert.assertEquals(list.get(1).get("bos"),"4");



    }

    @Test
    public void TC03() throws SQLException {

        //(products tablosundan ürünlerin isimlerini,categories tablosundan ürünlerin category name lerini)
        //deniz ürünleri kategorisine ait ürünlerin isimlerini ve category_name lerini getirelim
        //toplamda 12 adet ürün oldugunu dogrulayalim:

        String query3 = "select count(product_name) ,category_name \n" +
                "from products\n" +
                "join categories\n" +
                "on  products.product_id=categories.category_id\n" +
                "where category_name='Seafood'\n" +
                "group by category_name";


       List<Map<String ,String>> list=DatabaseConnector.getQueryAsAListOfMaps(query3);

        System.out.println(list.get(0));


    }
}
