package matech.utils.db;

public class Page {
   /**
    * 分页
    */
	
	public int page_index;   //当前页
	public int page_size=10; //每页大小
	public int total_record; //总记录
	public int page_count;   // 页数
	
	public Page(int total_record,int page_size){
		this.total_record=total_record;
		if(page_size!=0){
			this.page_size=page_size;
		}
		
	}
	
	public Page(int total_record,int page_size,int page_index){
		this.total_record=total_record;
		if(page_size!=0){
			this.page_size=page_size;
		}
		
		this.page_index=page_index;
		
	}
	
	public Page(int total_record,int page_size,String page_index){
		this.total_record=total_record;
		if(page_size!=0){
			this.page_size=page_size;
		}
		
		String str_page_index=(page_index==null || page_index=="")?"1":page_index;		
		this.page_index = Integer.parseInt(str_page_index);
		
	}
	
	public int getPage_index() {
		return page_index;
	}
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}
	public void setPage_index(String page_index) {
		String str_page_index=(page_index==null || page_index=="")?"1":page_index;		
		this.page_index = Integer.parseInt(str_page_index);
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getTotal_record() {
		return total_record;
	}
	public void setTotal_record(int total_record) {
	    this.page_count=total_record/page_size;
	    if(total_record%page_size>0){
	    	this.page_count=this.page_count+1;
	    }
		this.total_record = total_record;
	}
	public int getPage_count() {
		return page_count;
	}
	public void setPage_count(int page_count) {
		this.page_count = page_count;
	}

	public String getPageSql(String sql) {
		int start=page_size*(page_index-1);
		int end=start+page_size;
		String page_sql="	SELECT * FROM (" +
				"					SELECT ROWNUM RN, A.*  FROM " +
				"          (" +sql+") A "  +
				"          WHERE ROWNUM <="+end+ 
				"  	 )WHERE RN >="+start;
		return page_sql;
	}
	
}
