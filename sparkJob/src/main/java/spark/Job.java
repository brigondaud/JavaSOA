package spark;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaRDD;

/**
 * A  very simple Spark job.
 * @author Baptiste Rigondaud & Lo�c Poncet
 */
public class Job {
    
    public static void main(String[] args) {
        SparkSession spark = SparkSession
        .builder()
                .config("spark.driver.memory", "512m")
                .config("spark.executor.memory", "512m")
        .appName("JavaSparkPi")
        .getOrCreate();
        
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

      int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
      int n = 100 * slices;
      List<Integer> l = new ArrayList<>(n);
      for (int i = 0; i < n; i++) {
        l.add(i);
      }

      JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

      int count = dataSet.map(integer -> {
        double x = Math.random() * 2 - 1;
        double y = Math.random() * 2 - 1;
        return (x * x + y * y <= 1) ? 1 : 0;
      }).reduce((integer, integer2) -> integer + integer2);

      System.out.println("Pi is roughly " + 4.0 * count / n);

        spark.stop();
    }
    
}