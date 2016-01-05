package com.yp.lockscreen.utils;

import java.util.HashMap;

import com.yp.enstudy.utils.StringUtil;

public class StringUtils {
    
    @SuppressWarnings("unused")
    public static HashMap<Integer, Integer> checkWordInPosition(final String word,String msg){
        HashMap<Integer, Integer> maps = new HashMap<Integer, Integer>();
        int start,end,pEnd = 0;
        int count=0;
        while(true){
            if(msg.indexOf(word)>=0){
                start = msg.indexOf(word);
                end = start + word.length()-1;
                int nextIndex = end +1;
                
                String leftStr = leftWord(msg,start);
                String endStr = rightWord(msg, end);
                
                if((leftStr==null || !isLetter(leftStr)) && (endStr==null || !isLetter(endStr))){
                    int finalStart= start+pEnd+(count);
                    maps.put(finalStart, finalStart+word.length());
//                    System.out.println("true");
                }else{
//                    System.out.println("false");
                }
                
                if(nextIndex<=msg.length()){
                    pEnd += end;
                    msg = msg.substring(nextIndex,msg.length());                    
                }else{
                    break;
                }
                count++;
            }else{
                break;
            }
        }
        return maps;
    }
    
    public static String rightWord(String msg,int end){
        try {
            if((end+1)>=msg.length()) return null;
            return msg.substring(end+1, end+2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String leftWord(String msg,int start){
        try {
            if(start==0) return null;
            return msg.substring(start-1, start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean isLetter(String str){ 
              java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[a-zA-Z0-9]+"); 
              java.util.regex.Matcher m = pattern.matcher(str);
              return m.matches();     
     }
    
    public static float parseSizeForMB(int size){
        float fs =  ((float)size/(float)1024/(float)1024);
        return (float)(Math.round(fs*100))/100;
    }
    
    /**
     * @param s xxxx年xx月xx日
     * @return
     */
    public static String timeCn2En(String s){
    	try {
    		return s.replace("年", "-").replace("月", "-").replace("日", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return s;
    }
}
