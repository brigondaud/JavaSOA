/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * A  very simple Spark job.
 * @author Baptiste Rigondaud & Loïc Poncet
 */
public class Job {
    
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("Test Job");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<Integer> l = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
          l.add(i);
        }

        long count = sc.parallelize(l).filter(i -> {
          double x = Math.random();
          double y = Math.random();
          return x*x + y*y < 1;
        }).count();
        System.out.println("Pi is roughly " + 4.0 * count / 100);
    }
    
}
