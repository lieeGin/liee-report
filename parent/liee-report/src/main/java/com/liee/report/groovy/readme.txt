groovy报表查询方式使用说明：

1、groovy脚本中需要包含getData(返回分页对象，包含数据集合和数据总条数) ，getList(只返回数据集合)方法，该方法为查询的入口，入参是map形式的查询参数。
	def getData(param){
		...
	}
	参数格式如下(具体如何使用，根据实际情况在groovy脚本中处理)：
	param
	  |---key  ： 参数配置的code
	       |
	     object   ： 值是一个map
	  	   |---value  
	  	   |     |
	  	   |   实际参数值 
	  	   |
	  	   |--compareWay
	  	   	     |
	  	   	    查询匹配方式  大于，小于，等于 ，like 等


2、getData 方法务必返回一个map对象，包括rows(查询的结果集list) 属性和 total(查询总条数) 属性。

3、groovy中调用查询sql方式：
	a.引入必须的包
		import java.io.File;
		import com.liee.report.groovy.GroovyDB;
	b.获取db对象
		def db = GroovyDB.instance;
	c.查询
		def result = db.rows("select * from sys_user");
		println "结果： "+result.size();
	    result.each{
			row->
			println row["user_name"];
	    }

4、groovy 脚本中获取分页参数方式：
	当前页数:${pageNum}
	每页条数:${pageSize}
	排序字段:${sort}
	顺序:${order}
	
5、查询返回结果每行记录的各个字段名称要与报表配置中的field 对应。
	
	
	