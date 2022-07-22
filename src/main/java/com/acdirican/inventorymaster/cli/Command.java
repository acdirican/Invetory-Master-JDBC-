package com.acdirican.inventorymaster.cli;

public class Command {

	static final String HELP = "help";
	static final String EXIT = "exit";
	
	
	static final String LIST = "list";
	
		static final String LISTMORETHEN = "morethan";
		static final String LISTLESSTHEN = "lessthan";
		static final String LISTEQUALS = "equals";
		static final String LISTDEPLETEDS = "depleted";
	
		static final String PRODUCT = "product";
		static final String SUPPLIER = "supplier";
	
	static final String FIND = "find";
	static final String UPDATE = "update";
	static final String GETWITHID = "getid";
	static final String GETWITHINDEX = "getindex";
	static final String ADD = "add";

	static final String DELETE = "delete";
	static final String DELETE_ALL = "delete_all";

	static final String META = "meta";

	static final String ERROR = "ERROR: ";
	static final String DBERROR = ERROR + " DB connection or query error!";
	static final String ERROR_UNKNOWN_PARAMATER = ERROR + " Unknown paramter!";
	
	static final String QUANTITY = "[QUANTITY]";
	static final String ID = "ID";
	static final String INDEX = "[INDEX]";
	static final String PS = "[" + PRODUCT + "/" + SUPPLIER + "]";
	static final String NL = System.lineSeparator();
	static final String SPACE = " ";
	
	static String help() {
		return LIST + " of Commands:\n" 
				+ "help\n" 
				+ "exit \n" 
				
				+ LIST + SPACE + PS + NL 
				
				+ LIST + SPACE + LISTMORETHEN + SPACE + QUANTITY + NL
				+ LIST + SPACE + LISTLESSTHEN + SPACE +  QUANTITY + NL
				+ LIST + SPACE + LISTEQUALS + QUANTITY + NL
				+ LIST + SPACE + LISTDEPLETEDS + NL
				+ FIND + SPACE + PS + " [NAME]" + NL
				
				+ GETWITHINDEX + SPACE + PS + SPACE + "[INDEX]" + NL 
				+ GETWITHID + SPACE + PS +  ID + NL 
				+ UPDATE + SPACE + PS  + SPACE + ID + NL 
								
				+ ADD + SPACE + PS + NL
				+ DELETE + SPACE + PS +  SPACE + ID + NL 
				+ DELETE_ALL + SPACE + PS +  SPACE + ID + NL 
				
				+ META;
	}
}
