package org.uma.jmetalsp.examples.streamingdatasource;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetalsp.StreamingDataSource;
import org.uma.jmetalsp.observeddata.ListObservedData;
import org.uma.jmetalsp.observeddata.SingleObservedData;
import org.uma.jmetalsp.observer.Observable;
import org.uma.jmetalsp.observer.impl.DefaultObservable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class reads double values from the keyboard and returns them as a list
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class SimpleStreamingDataSourceFromKeyboard implements
        StreamingDataSource<SingleObservedData<List<Double>>> {
  private Observable<SingleObservedData<List<Double>>> observable;

  /**
   * @param observable
   */
  public SimpleStreamingDataSourceFromKeyboard(Observable<SingleObservedData<List<Double>>> observable) {
    this.observable = observable;
  }

  public SimpleStreamingDataSourceFromKeyboard() {
    this(new DefaultObservable<>());
  }


  @Override
  public void run() {
    Scanner scanner = new Scanner(System.in);

    double v1 ;
    double v2 ;

    while (true) {
      System.out.println("Introduce the new reference point(between commas):");
      String s = scanner.nextLine() ;
      Scanner sl= new Scanner(s);
      sl.useDelimiter(",");
      try {
        v1 = Double.parseDouble(sl.next());
        v2 = Double.parseDouble(sl.next());
      }catch (Exception e){//any problem
        v1=0;
        v2=0;
      }
      System.out.println("REF POINT: " + v1 + ", " + v2) ;

      observable.setChanged();
      List<Double> values = Arrays.asList(v1, v2) ;
      observable.notifyObservers(new SingleObservedData<>(values));
    }
  }

  @Override
  public Observable<SingleObservedData<List<Double>>> getObservable() {
    return observable;
  }
}
