package which.me.utilty;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class jsonUtil {

	/**
	 * 将ArrayList转换成JsonArray格式
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		ResultSetMetaData md=rs.getMetaData();
		int num=md.getColumnCount();
		System.out.println("num: " +num);
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				if(md.getColumnName(i).equals("birthday")||md.getColumnName(i).equals("time")){
					mapOfColValues.put(md.getColumnName(i), rs.getString(i)); //获取列名和值放进JSON传输对象中
				}else{
					
					System.out.println("++ : " + md.getColumnName(i)+" : "+rs.getObject(i));
					
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i)); //获取列名和值放进JSON传输对象中
				}
			}
			array.add(mapOfColValues);//放到JSON集合中
			System.out.println("\nArray : "+ array+"\n\n");
		}
		return array;
	}
}
