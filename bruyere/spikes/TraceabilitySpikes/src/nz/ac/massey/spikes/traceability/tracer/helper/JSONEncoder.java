package nz.ac.massey.spikes.traceability.tracer.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * This class is for stringifying/serializing variables in order to FREEZE it
 * @author Li Sui
 *
 */

public class JSONEncoder {
	private int maxDepth = 10;//default max depth 

	public String stringify(final Object obj){
		int currentDepth=0;
		return stringify(obj,currentDepth);
	}
	private String stringify(final Object obj,int depth){
			/**
			 * encode string
			 */
			if( obj instanceof String ||obj instanceof Character){
				return  "\""+String.valueOf(obj)+"\"";
			}
			/**
			 * encode auto boxing primitive type
			 */
			if(obj instanceof Integer || obj instanceof Double|| obj instanceof Boolean ||obj instanceof Float  || obj instanceof Short ||obj instanceof Long || obj instanceof Byte ){
				return String.valueOf(obj);
			}
			/**
			 * encode list
			 */
			if(obj instanceof List<?>){
				return StringifyList((List<?>) obj);
			}
			/**
			 * encode object array
			 */
			if(obj instanceof Object[]){
				return StringifyObjectArray(( Object[]) obj);
			}
			/**
			 * encode primitive array(there is no autoboxing for primitive array)
			 */
			if(obj instanceof int[] ){
				return StringifyPrimitiveArray( (int[]) obj);
			}
			if( obj instanceof boolean[]){
				return StringifyPrimitiveArray( (boolean[])obj);
			}
			if( obj instanceof double[]){
				return StringifyPrimitiveArray( (double[])obj);
			}
			if(obj instanceof float[]){
				return StringifyPrimitiveArray( (float[])obj);
			}
			/**
			 * encode map
			 */
			if(obj instanceof Map){
				return stringfyMap((Map<?, ?>) obj);
			}
			/**
			 * encode class field ???????????
			 */			
			if(obj instanceof Object){
				try {
					if(depth<maxDepth){
						return stringfyClass(obj,depth);
					}

				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		return "\"null\"";
	}
	private String StringifyObjectArray(final  Object[] obj){
		StringBuilder output=new StringBuilder();
		output.append("[");
		for(int i =0; i<obj.length;i++){
			if(i==obj.length-1){
				output.append(stringify(obj[i]));	
			}else{
				output.append(stringify(obj[i])+",");
			}
		}
		output.append("]");
		return output.toString();
	}
	private String StringifyPrimitiveArray(final  double[] obj){
		StringBuilder output=new StringBuilder();
		output.append("[");
		for(int i =0; i<obj.length;i++){
			if(i==obj.length-1){
				output.append(stringify(obj[i]));	
			}else{
				output.append(stringify(obj[i])+",");
			}
		}
		output.append("]");
		return output.toString();
	}
	private String StringifyPrimitiveArray(final  int[] obj){
		StringBuilder output=new StringBuilder();
		output.append("[");
		for(int i =0; i<obj.length;i++){
			if(i==obj.length-1){
				output.append(stringify(obj[i]));	
			}else{
				output.append(stringify(obj[i])+",");
			}
		}
		output.append("]");
		return output.toString();
	}
	private String StringifyPrimitiveArray(final  boolean[] obj){
		StringBuilder output=new StringBuilder();
		output.append("[");
		for(int i =0; i<obj.length;i++){
			if(i==obj.length-1){
				output.append(stringify(obj[i]));	
			}else{
				output.append(stringify(obj[i])+",");
			}
		}
		output.append("]");
		return output.toString();
	}
	
	private String StringifyPrimitiveArray(final  float[] obj){
		StringBuilder output=new StringBuilder();
		output.append("[");
		for(int i =0; i<obj.length;i++){
			if(i==obj.length-1){
				output.append(stringify(obj[i]));	
			}else{
				output.append(stringify(obj[i])+",");
			}
		}
		output.append("]");
		return output.toString();
	}
	
	private String StringifyList(final List<?> obj){
		StringBuilder output=new StringBuilder();
		output.append("{\"class\":\""+obj.getClass().getTypeName()+"\",\"elements\":[");
		for(int i =0; i<obj.size();i++){
			//currentDepth=0;
			if(i==obj.size()-1){
				output.append(stringify(obj.get(i)));				
			}else{
				output.append(stringify(obj.get(i))+",");
			}
		}
		output.append("]}");
		return output.toString();
	}

	
	private String stringfyMap(final Map<?,?> obj){
		StringBuilder output=new StringBuilder();
		output.append("{\"class\":\""+obj.getClass().getTypeName()+"\",\"elements\":{");
		int counter=0;
		for(Map.Entry<?, ?> entry :obj.entrySet()){

			if(counter == obj.size()-1){
				output.append("\""+entry.getKey()+"\":"+stringify(entry.getValue()));
				
			}else{	
				output.append("\""+entry.getKey()+"\":"+stringify(entry.getValue())+",");

			}
			counter++;
		}
		output.append("}}");
		return output.toString();
	}
	
	private String stringfyClass(final Object obj,int depth) throws IllegalArgumentException, IllegalAccessException{
		depth++;
		List<Field> fields =new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
		//set all fields access to true and remove reference to this object
		for(int i =0;i<fields.size();i++){
			fields.get(i).setAccessible(true);
			if(fields.get(i).getName().equals("this$0")){
				fields.remove(i);
			}
		}

		StringBuilder output=new StringBuilder();
		//no fields
		if(fields.size() ==0){
			output.append("{\"class\":\""+obj.getClass().getSimpleName()+"\"}");
		}else{
			output.append("{\"class\":\""+obj.getClass().getSimpleName()+"\",");
			for(int i=0;i<fields.size();i++){
				Field f=fields.get(i);
				if(i == fields.size()-1){	
					output.append("\""+f.getName()+"\":"+stringify(f.get(obj),depth));
				}else{
					output.append("\""+f.getName()+"\":"+stringify(f.get(obj),depth)+",");
				}
			} 
			output.append("}");
		}

		return output.toString();
	}

	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(final int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
}
