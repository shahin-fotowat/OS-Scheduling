/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 * The current implementation will work without memory management features
 *
 */
package com.jimweller.cpuscheduler;


import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {

	private ArrayList<Process> jobs;
	// Add data structures to support memory management
	/*------------------------------------------------------------*/
	private ArrayList<Integer> memBlock;
	private ArrayList<Integer> memPid;
	
	/*------------------------------------------------------------*/

	class FCFSComparator implements Comparator<Process> {
		public int compare(Process p1, Process p2) {
			if (p1.getArrivalTime() != p2.getArrivalTime()) {
				return Long.signum(p1.getArrivalTime() - p2.getArrivalTime());
			}
			return Long.signum(p1.getPID() - p2.getPID());
		}
	}

	FCFSComparator comparator = new FCFSComparator();

	FCFSSchedulingAlgorithm() {
		activeJob = null;
		jobs = new ArrayList<Process>();

		// Initialize memory
		/*------------------------------------------------------------*/
		memBlock = new ArrayList<Integer>();
		memPid = new ArrayList<Integer>();
		
		memBlock.add(0,380);
		memPid.add(0, 0);
		/*------------------------------------------------------------*/

	}


	/** Add the new job to the correct queue. */
	public void addJob(Process p) {

	// Check if any memory is available 
	/*------------------------------------------------------------*/
	
	boolean ignoreProcess = false;
  
	for ( int i = 0; i < memBlock.size(); i++){
		if (p.getMemSize() <= memBlock.get(i)){
			ignoreProcess = true;
			memBlock.set(i, (int)(memBlock.get(i) - p.getMemSize()));
			memBlock.add(i, 0);
			memPid.add(i,(int)p.getPID());
			System.out.println("process with pid " + memPid.get(i) + " added to memory!");
			break;
		}		  
	}
  

  /*------------------------------------------------------------*/

  // If enough memory is not available then don't add it to queue 
	if (ignoreProcess == false)
	{
		p.setIgnore(true);
		return;
	}
	
	/*------------------------------------------------------------*/
	jobs.add(p);
	Collections.sort(jobs, comparator);
	}

	/** Returns true if the job was present and was removed. */
	public boolean removeJob(Process p) {
		if (p == activeJob)
			activeJob = null;

		// In case memory was allocated, free it
		/*------------------------------------------------------------*/
		int pid = (int)p.getPID();
		for(int i = 0; i < memPid.size(); i++) {
			if(pid == memPid.get(i)) {
				System.out.print("process with pid " + memPid.get(i)); 
				memBlock.set(i, memBlock.get(i) + (int)p.getMemSize());
				System.out.print(" is removed from memory with size: " + memBlock.get(i) + "\n");
				memPid.set(i, 0);
				break;
			}
		}
			
		for(int i = 0; i < memPid.size() - 1; i++) {
				if((memPid.get(i) == 0) && (memPid.get(i+1) == 0)) {
					memBlock.set(i+1 , memBlock.get(i) + memBlock.get(i+1));
					memBlock.remove(i);
					memPid.remove(i);	
				}
		}
		
		/*------------------------------------------------------------*/

		return jobs.remove(p);
	}

	/**
	 * Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such
	 * as when switching to another algorithm in the GUI
	 */
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the next process that should be run by the CPU, null if none
	 * available.
	 */
	public Process getNextJob(long currentTime) {
		Process earliest = null;

		if (!isJobFinished())
			return activeJob;
		if (jobs.size() > 0)
			earliest = jobs.get(0);
		activeJob = earliest;
		return activeJob;
	}

	public String getName() {
		return "First-Come First-Served";
	}

	public void setMemoryManagment(String v) {
		// Modify class to suppor memory management
	}
}