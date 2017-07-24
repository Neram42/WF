package org.camunda.wf.hiring.services;

public final class Constants  {

	  public static final String REMOTE_URL = "http://25.59.214.213:8080";
	

	  /**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	  private Constants(){
	    throw new AssertionError();
	  }
	}