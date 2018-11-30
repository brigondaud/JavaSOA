package spark;

import beans.Product;
import beans.Receipt;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import java.util.Iterator;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import scala.Tuple2;

/**
 * This spark job outputs the most used product from the receipts web service.
 * It is expected to be launched in client mode so that the result can be
 * directly used after execution.
 * 
 * @author Baptiste Rigondaud & Loïc Poncet & Alice Breton & Laora Heintz
 */
public class MostUsedProduct {
    
    /**
     * This job connects to the given mongodb database to compute the most
     * used product.
     * 
     * @param args 
     */
    public static void main(String args[]) {
        
        if(args.length < 1) throw new IllegalArgumentException("Job is lacking argument");
        
        SparkSession session = SparkSession.builder()
            .appName("mostUsedProduct")
            .config("spark.mongodb.input.uri", args[0])
            .config("spark.mongodb.output.uri", args[0])
            .config("spark.driver.memory", "512m")
            .config("spark.executor.memory", "512m")
            .getOrCreate();
        
        JavaSparkContext context = new JavaSparkContext(session.sparkContext());
        
        JavaMongoRDD<Document> receiptsDocs = MongoSpark.load(context);
        System.out.println("DEBUG " + receiptsDocs.first().toJson());
        
        JavaRDD<Receipt> receipts = receiptsDocs.toDS(Receipt.class)
                .javaRDD();
        
        System.out.println("DEBUG Number or receipts" + Lists.newArrayList(receipts.toLocalIterator()).size());
        /*
        Iterator<Receipt> iterator = receipts.toLocalIterator();
        while(iterator.hasNext()) {
            Receipt r = iterator.next();
            System.out.println("DEBUG " + r);
            for(Product p: r.getProducts())
                System.out.println("DEBUG " + p);
        }
        */
        
        JavaPairRDD<String, Integer> productCounts = receipts
                .flatMap(receipt -> receipt.getProducts().iterator())
                .mapToPair(product -> {
                    System.out.println("PRODUCT " + product);
                    return new Tuple2<>(product.getName(), product.getQuantity());
                })
                .reduceByKey((q1, q2) -> {
                    System.out.println("QUANTITYADD " + q1 + ' ' + q2);
                    return q1+q2;
                });
        
        List<Tuple2<String, Integer>> product = productCounts.sortByKey(false).take(0);
        StringBuilder result = new StringBuilder();
        result.append("MostUsedProductResult/");
        if(!product.isEmpty())
            result.append(product.get(0)._1)
                    .append(":")
                    .append(product.get(0)._2);
        
        System.out.println(result);
        
        session.stop();
    }
    
}
