package com.liee.report.groovy;
import groovy.sql.Sql   

class GroovyDB{
	
	def _myGroovyDbTool ;
	
	private static volatile GroovyDB instance
    private GroovyDB() {}
	
    static GroovyDB getInstance () {
        if (instance) {
           return instance
        } else {
            synchronized(GroovyDB) {
                if (instance) {
                	return instance
                } else {
                    instance = new GroovyDB ();
                    instance.init();
                	return instance;
                }
            }
        }
    }
	
    // 初始化Sql对象
    def init(){
    	def jdbcUrl="jdbc:mysql://localhost:3306/mydatabase?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull";
    	def user="root";
    	def password="123456";
    	def driverClass="com.mysql.jdbc.Driver";
    	
    	
    	/*def config = new XmlParser().parse("../../../../../resources/c3p0.xml");
    	
    	config.each{
    		if(it.attribute("name")=="billiardadmin"){
    			def propertys = it.property;
    			propertys.each{
    				def name = it.attribute("name");
    				if(name == "jdbcUrl"){
    					jdbcUrl = it.text();
    				}else if(name == "user"){
    					user = it.text();
    				}else if(name == "password"){
    					password = it.text();
    				}else if(name == "driverClass"){
    					driverClass = it.text();
    				}
    			}
    		}
    	}*/
    	
    	_myGroovyDbTool= Sql.newInstance(jdbcUrl,user, password,driverClass);
    }
	
	
	def query(sql){
		 def result = [];
		 _myGroovyDbTool.query (sql,{ 
		 while (it.next()){
			result.add(it);
		 }
		 println "query success"
		 });
		 
		 return result;
	}
	
	
	def rows(sql){
		 def result = [];
		 _myGroovyDbTool.rows(sql).each({row->  
		   result.add(row);
		 })
		 println "rows success"
		 return result;
	}

	
	def getWhere(param){
		def str="";
		param.each{
		  key , value ->
		  if("${value.compareWay}" == "like" && "${value.value}" !=""){
			  str+=" and ${key} ${value.compareWay} '%${value.value}%' "
		  }else if("${value.compareWay}" == "in"&& "${value.value}" !=""){
			  str+=" and ${key} ${value.compareWay} (${value.value}) "
		  }else if("${value.value}" !=""){
			  str+=" and ${key} ${value.compareWay} ${value.value} "
		  }
	    }
		
		return str;
	}
	
	
	def getOrderBy(sort,order){
		def str="";
		if(sort!="" && order!="" && sort!=null && order!=null  && sort!="null" && order!="null"){
			str = " order by "+sort +" "+ order;
		}
		return str;
	}
	
	
	def getLimit(pageNum,pageSize){
		def str="";
		if(pageNum!="" && pageSize!="" && pageNum.isInteger() && pageSize.isInteger() && Integer.parseInt(pageNum)!=0 && Integer.parseInt(pageSize)!=0){
			str = " limit "+(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize)+" , "+pageSize;
		}
		return str;
	}
	
	
} 

 