package which.me.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import which.me.Bean.smallTypeBean;
import which.me.utilty.Conn;
import which.me.utilty.jsonUtil;

public class smallTypeDao {

	/**
	 * 根据小类ID查询小类名称
	 * @return
	 */
	public static String sidIsName(String sid){
		String sql = "select name from t_smallType where id = "+sid;
		Connection con = Conn.getCon();
		ResultSet rs = null;
		String name = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		return name;
	}
	/**
	 * 按大类ID查询 返回json
	 * @param bigTypeId
	 * @return
	 */
	public static  JSONArray bigTypeIdsel(int bigTypeId){
		String sql = "select * from t_smallType where bigTypeId = "+bigTypeId;
		return sel(sql);
	}
	/**
	 * 查询全部
	 * @param p
	 * @param pageSize
	 * @return
	 */
	public static JSONArray selAll(int p,int pageSize){
		String sql = "select t_smalltype.*, t_bigtype.name bigTypeName from t_smalltype, t_bigtype where t_smalltype.bigTypeId=t_bigtype.id limit "
				+ (p-1)*pageSize+"," + pageSize;
		
		return sel(sql);
	}
	/**
	 * 根据昵称查询
	 * @param p
	 * @param pageSize
	 * @param username
	 * @return
	 */
	public static JSONArray nameSel(int p,int pageSize,String name){
				
		String sql = "select t_smallType.*, t_bigtype.name as bigTypeName from t_smalltype, t_bigtype "
				+ "where t_smalltype.bigTypeId=t_bigtype.id and t_smalltype.name like '%"+name+"%' limit " + (p-1)*pageSize + ","+ pageSize;
		return sel(sql);
	}
	/**
	 * 查询sql 返回json集合
	 * @param sql
	 * @return
	 */
	public static JSONArray sel(String sql){
		System.out.println("sql查询语句："+sql);
		Connection con = Conn.getCon();
		ResultSet rs = null;
		JSONArray jsonArray = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			/*while(rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				break;
			}*/
			
			ResultSetMetaData md=rs.getMetaData();
			int num=md.getColumnCount();
			System.out.println("num: " +num);
			JSONArray array=new JSONArray();
			while(rs.next()){
				JSONObject mapOfColValues=new JSONObject();
				mapOfColValues.put("id", rs.getObject(1)); //获取列名和值放进JSON传输对象中
				mapOfColValues.put("name", rs.getObject(2)); //获取列名和值放进JSON传输对象中
				mapOfColValues.put("bigTypeId", rs.getObject(3)); //获取列名和值放进JSON传输对象中
				mapOfColValues.put("remarks", rs.getObject(4)); //获取列名和值放进JSON传输对象中
				mapOfColValues.put("bigTypeName", rs.getObject(5)); //获取列名和值放进JSON传输对象中
				array.add(mapOfColValues);//放到JSON集合中
				//System.out.println("\nArray : "+ array+"\n\n");
			}
			
			jsonArray = array;
			
			//System.out.println("TesT1 : " +jsonArray);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
		
	}
	/**
	 * 查询sql 返回list集合
	 * @param sql
	 * @return
	 */
	public static List bigTypeIdselList(int bigTypeId){
		String sql = "select * from t_smallType where bigTypeId = "+bigTypeId;
		Connection con = Conn.getCon();
		ResultSet rs = null;
		List<smallTypeBean> list = new ArrayList<smallTypeBean>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				smallTypeBean s = new smallTypeBean();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				System.out.println("[小类ID："+s.getId()+"    小类名称："+s.getName()+"]");
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
	/**
	 * 查询总行数
	 * @return
	 */
	public static int count(String sql){
		Connection con = Conn.getCon();
		int i = 0;
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			if(rs!=null){
				i = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("查询到的用户行数为："+i);
		return i;
	}
	/**
	 * 添加用户
	 * @param u
	 * @return
	 */
	public static int add(smallTypeBean u){
		String sql = "insert into t_smalltype(name, bigTypeId, remarks) values(?,?,?)";
		Connection con = Conn.getCon();
		PreparedStatement ps = null;
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, u.getName());
			ps.setInt(2, u.getBigTypeId());
			ps.setString(3, u.getRemarks());
			i = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	public static int del(int id){
		System.out.println("接受到要删除的ID："+id);
		String sql = "delete from t_smallType where id=?";
		Connection con = Conn.getCon();
		PreparedStatement ps = null;
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	/**
	 * 删除多个
	 * @param ids
	 * @return
	 */
	public static int manyDel(String ids){
		String sql = "delete from t_smalltype where id in("+ids+")";
		Connection con = Conn.getCon();
		PreparedStatement ps = null;
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	/**
	 * 修改数据
	 * @param u
	 * @return
	 */
	public static int update(smallTypeBean u){
		String sql = "update t_smalltype set name=? , remarks = ?, bigTypeId=? where id=?";
		
		Connection con = Conn.getCon();
		PreparedStatement ps = null;
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, u.getName());
			ps.setString(2, u.getRemarks());
			ps.setInt(3, u.getBigTypeId());
			ps.setInt(4, u.getId());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	/**
	 * 查询总行数
	 * @return
	 */
	public static List bigTypeIdIsSel(int bigTypeId){
		Connection con = Conn.getCon();
		String sql = "select * from t_smallType where bigTypeId = ?";
		ResultSet rs = null;
		List<smallTypeBean> list = new ArrayList<smallTypeBean>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, bigTypeId);
			rs = ps.executeQuery();
			while(rs.next()){
				 int id = rs.getInt("id");
				 String name = rs.getString("name");
				 String remarks = rs.getString("remarks");
				 smallTypeBean s = new smallTypeBean(name, bigTypeId, remarks);
				 s.setId(id);
				 list.add(s);
				 System.out.println("{id="+id+",name="+name+",remarks="+remarks+",bigTypeId="+bigTypeId+"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/*public static void main(String[] args) {
		//System.out.println(sidIsName("22"));
		//System.out.println("kkk");
		//System.out.println(bigTypeIdIsSel(1));
		//bigTypeIdIsSel(6);
	}*/
}
