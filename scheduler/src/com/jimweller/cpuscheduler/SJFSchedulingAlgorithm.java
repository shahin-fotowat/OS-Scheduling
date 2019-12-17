/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Charles Zhu
 * Spring 2016
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

import com.jimweller.cpuscheduler.Process;

public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
	
	 Vector<Process> jobs;
	private boolean preempt;
    
    SJFSchedulingAlgorithm(){
        // Fill in this method
        /*------------------------------------------------------------*/
		activeJob = null;
    	preempt = false;
		jobs = new Vector<Process>();


        /*------------------------------------------------------------*/
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/

		jobs.add(p);


        /*------------------------------------------------------------*/
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/
		
		if (p == activeJob)
			activeJob = null;
		return jobs.remove(p);


        /*------------------------------------------------------------*/
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
        throw new UnsupportedOperationException();
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
        // Remove the next lines to start your implementation
       // throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/

		Process p = null, returnJob = (Process) jobs.get(0);
		long time = 0, shortestTime = returnJob.getBurstTime();
		
		if (!isJobFinished() && !isPreemptive())
			return activeJob;
		
		for (int i = 0; i < jobs.size(); ++i) {
			p = (Process) jobs.get(i);
			time = p.getBurstTime();
			if (time < shortestTime) {
				shortestTime = time;
				returnJob = p;
			}
		}
		activeJob = returnJob;
		return activeJob;

        /*------------------------------------------------------------*/
    }

    public String getName(){
        return "Shortest Job First";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/

		return preempt;

        /*------------------------------------------------------------*/
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
        // Remove the next lines to start your implementation
       // throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/

		preempt = v;

        /*------------------------------------------------------------*/
    }
    
}