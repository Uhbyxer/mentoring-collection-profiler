package com.mentoring.profiler.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ListProfiler {

	private int randomNumber;
	private int randomIndex;
	private int randomIndexGet;

	private List<Integer> linkedList;
	private List<Integer> arrayList;
    
	private List<Integer> linkedList1;
	private List<Integer> arrayList1;
    
	private List<Integer> linkedList10;
	private ArrayList<Integer> arrayList10;
    
	private List<Integer> linkedList1000;
	private List<Integer> arrayList1000;
    
	private List<Integer> linkedList1001;
	private ArrayList<Integer> arrayList1001;

	private List<Integer> linkedListInsert;
	private List<Integer> arrayListInsert;
    
	private List<Integer> linkedListDelete;
	private List<Integer> arrayListDelete;
	
	private List<Integer> linkedList1000Get;
	private List<Integer> arrayList1000Get;
	
	@Setup(Level.Invocation)
	public void init() {
		randomNumber = new Random().nextInt(100);
		randomIndex = new Random().nextInt(100) + 400;
		randomIndexGet = new Random().nextInt(20) + 490;
		

	    linkedList = new LinkedList<Integer>();
	    arrayList = new ArrayList<Integer>();	
	    
		linkedList1 = new LinkedList<Integer>(Arrays.asList(0));
		arrayList1 = new ArrayList<Integer>(Arrays.asList(0));
		
		linkedList10 = new LinkedList<Integer>(Arrays.asList(0, 1, 2 , 3, 4 , 5, 6, 7, 8, 9, 10));
		arrayList10 = new ArrayList<Integer>(Arrays.asList(0, 1, 2 , 3, 4 , 5, 6, 7, 8, 9, 10));
		arrayList10.trimToSize();
		
        linkedList1000 = new LinkedList<Integer>();
        arrayList1000 = new ArrayList<Integer>();
        
        linkedList1001 = new LinkedList<Integer>();
    	arrayList1001 = new ArrayList<Integer>(1000);
    	
        linkedListInsert = new LinkedList<Integer>();
        arrayListInsert = new ArrayList<Integer>();
        
        linkedListDelete = new LinkedList<Integer>();
        arrayListDelete = new ArrayList<Integer>();
        
        
        linkedList1000Get = new LinkedList<Integer>();
        arrayList1000Get = new ArrayList<Integer>();
        
        Random random = new Random();
        
    	for(int i = 0; i < 1000; i++) {
    		arrayList1001.add(random.nextInt(1000));
    		linkedList1001.add(random.nextInt(1000));
    		
    		linkedListInsert.add(random.nextInt(1000));
    		arrayListInsert.add(random.nextInt(1000));

    		linkedListDelete.add(random.nextInt(1000));
    		arrayListDelete.add(random.nextInt(1000));
    		
    		linkedList1000Get.add(random.nextInt(1000));
    		arrayList1000Get.add(random.nextInt(1000));
    	}
    
    	arrayList1001.trimToSize();
	}
	
    @Benchmark
    public void arrayListAddFirst(Blackhole blackhole) {
       arrayList.add(randomNumber);
       blackhole.consume(arrayList);
    }
    

    @Benchmark
    public void linkedListAddFirst(Blackhole blackhole) {
      	linkedList.add(randomNumber);
    	blackhole.consume(linkedList);
    }
	
    @Benchmark
    public void arrayListAddSecond(Blackhole blackhole) {
       arrayList1.add(randomNumber);
       blackhole.consume(arrayList1);
    }
    

    @Benchmark
    public void linkedListAddSecond(Blackhole blackhole) {
        linkedList1.add(randomNumber);
   		blackhole.consume(linkedList1);
    }
    
    @Benchmark
    public void arrayListAddEleven(Blackhole blackhole) {
        arrayList10.add(randomNumber);
        blackhole.consume(arrayList10);
    }
    

    @Benchmark
    public  void linkedListAddEleven(Blackhole blackhole) {
        linkedList10.add(randomNumber);
        blackhole.consume(linkedList10);
    }
	

    @Benchmark
    public void arrayListAdd1000Elements(Blackhole blackhole) {
       
    	for(int i = 0; i < 1000; i++) {
    		arrayList1000.add(i);
    	}
    	
    	blackhole.consume(arrayList1000);
    }
    

    @Benchmark
    public void linkedListAdd1000Elements(Blackhole blackhole) {
        
    	for(int i = 0; i < 1000; i++) {
    		linkedList1000.add(i);
    	}
    	
    	blackhole.consume(linkedList1000);
    }
    
    @Benchmark
    public void linkedListAdd1001(Blackhole blackhole) {
        linkedList1001.add(randomNumber);
        blackhole.consume(linkedList1001);
    }
    

    @Benchmark
    public void arrayListAdd1001(Blackhole blackhole) {
        arrayList1001.add(randomNumber);
        blackhole.consume(arrayList1001);
    }
    
    @Benchmark
    public void arrayListInsert(Blackhole blackhole) {
    	arrayListInsert.add(randomIndex, randomNumber);
    	blackhole.consume(arrayListInsert);
    }
    
    @Benchmark
    public void linkedListInsert(Blackhole blackhole) {
    	linkedListInsert.add(randomIndex, randomNumber);
    	blackhole.consume(linkedListInsert);
    }
    
    @Benchmark
    public void arrayListDelete(Blackhole blackhole) {
    	arrayListDelete.remove(randomIndex);
    	blackhole.consume(arrayListDelete);
    }
    
    @Benchmark
    public void linkedListDelete(Blackhole blackhole) {
    	linkedListDelete.remove(randomIndex);
    	blackhole.consume(linkedListDelete);
    }
    
    
    @Benchmark
    public void linkedListGetElement(Blackhole blackhole) {
    	blackhole.consume(linkedList1000Get.get(randomIndexGet));
    }
    
    @Benchmark
    public void arrayListGetElement(Blackhole blackhole) {
    	blackhole.consume(arrayList1000Get.get(randomIndexGet));
    }


	public static void main(String[] args) throws RunnerException {

		Options options = new OptionsBuilder()
				.include(ListProfiler.class.getSimpleName()).threads(1)
				.forks(1).shouldFailOnError(true).shouldDoGC(true).warmupTime(TimeValue.milliseconds(100)).mode(Mode.SampleTime).timeUnit(TimeUnit.NANOSECONDS)
				.jvmArgs("-server").build();
		new Runner(options).run();

	}
}
