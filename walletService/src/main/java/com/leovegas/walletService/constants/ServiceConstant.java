package com.leovegas.walletService.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author gomathi lakshmanaperumal
 *
 */
public class ServiceConstant {
	
	public static String CREATE_PLAYER_AUDIT = "001";
	
	public static String UPDATE_PLAYER_AUDIT = "002";
	
	public static String DELETE_PLAYER_AUDIT = "003";
	
	public static String CREATE_WALLET_AUDIT = "004";
	
	public static String UPDATE_WALLET_BALANCE = "005";
	
	public static String DELETE_WALLET_AUDIT = "006";
	
	public static String CREATE_PLAYER_MESSAGE = "Player created";
	
	public static String UPDATE_PLAYER_MESSAGE = "player details updated";
	
	public static String DELETE_PLAYER_MESSAGE = "player deleted";
	
	public static String CREATE_WALLET_MESSAGE = "Wallet account created";
	
	public static String UPDATE_WALLET_BALANCE_MESSAGE = "Wallet balance updated";
	
	public static String DELETE_WALLET_MESSAGE = "Wallet account deleted";
	
	public static final Map<String, String> CONSTANT_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put(CREATE_PLAYER_AUDIT, CREATE_PLAYER_MESSAGE);
		        put(UPDATE_PLAYER_AUDIT, UPDATE_PLAYER_MESSAGE);
		        put(DELETE_PLAYER_AUDIT, DELETE_PLAYER_MESSAGE);
		        put(CREATE_WALLET_AUDIT, CREATE_WALLET_MESSAGE);
		        put(UPDATE_WALLET_BALANCE, UPDATE_WALLET_BALANCE_MESSAGE);
		        put(DELETE_WALLET_AUDIT, DELETE_WALLET_MESSAGE);
		    }});
	

}
