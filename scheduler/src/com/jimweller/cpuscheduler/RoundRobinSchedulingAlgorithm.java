/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Shahin Fotowat
 * Spring 2019
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

	//List of the processes stored
    private Vector<Process> jobList;
    //The timeslice each process gets
    private int quantum;   
    //A count down to when to interrupt a process because it's timeslice is over.
    private int quantumCounter; 
    //This variable keeps track of the number of consecutive timeslices a process has consumed
    private long turnCounter;
    //The index into the vector/array/readyQueue
    private int activeProcessPos;
	//priority of the processes - lower number: higher priority
    private boolean priority;
	//Default value for the quantum slice
	public final int QUANTUM_DEFAULT_VALUE = 10;

	//Constructor - Set the default values for the variables
    RoundRobinSchedulingAlgorithm() {
		activeJob = null;
		quantum   = QUANTUM_DEFAULT_VALUE;
		quantumCounter = quantum;
		activeProcessPos = -1;
		turnCounter = 0;
		priority    = false;
		jobList = new Vector<Process>();
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) {
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
		jobList.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) {
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
        
        // Fill in this method
        /*------------------------------------------------------------*/
		int jobPosition = jobList.indexOf(p);
		boolean retunredJob = jobList.remove(p);
		if (activeProcessPos >= jobPosition && jobPosition >= 0){
			activeProcessPos--;
			try {
				activeJob = jobList.get(activeProcessPos);
			} catch (ArrayIndexOutOfBoundsException exception){
				activeJob = null;
			}
		}
		quantumCounter = 0; //this would tell us that we need to preempt next cycle
			return retunredJob;
        /*------------------------------------------------------------*/
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
    when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() {
        return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     * Value to assign to quantum.
     */
    public void setQuantum(int v) {
        this.quantum = v;
    }

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) {
        // Remove the next lines to start your implementation
        //throw new UnsupportedOperationException();
		
		int index = 0;
		Process p = null;
		Process nextJob = null;
		quantumCounter--; //This gets called every tick of the CPU clock

		//not time to preempt
		if (activeProcessPos >= 0 && !isJobFinished() && quantumCounter > 0) {
			return activeJob;
		}  
		
		 //No jobs available - List of jobs is empty
		if(jobList.size() == 0) { 
			activeProcessPos = -1;
			return null;
		}

		//Determines the next job in the list
		if (activeProcessPos >= (jobList.size() - 1) || activeProcessPos < 0) {
			index = 0;
		} else {
			index = activeProcessPos + 1;
		}

		nextJob = jobList.get(index);
		activeProcessPos = index;

		//if (priority == true) {
			// weight timeslice by priority
		//	quantumCounter = quantum * activeJob.getPriorityWeight();      // backwards
		//	quantumCounter = quantum * (10 - nextJob.getPriorityWeight()); // backwards
		//} else {
		quantumCounter = quantum;
		//}

		this.activeJob = nextJob;
		return nextJob;
    }
	
    /*------------------------------------------------------------*/

    public String getName() {
        return "Round Robin";
    }
    
}