/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spark;

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
        System.out.println("Test job stdout");
        sc.stop();
    }
    
}
