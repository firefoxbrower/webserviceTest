package matech.utils.db;

/**
 * 
 * 记录集元数据信息
 * 
 */
public class ResultSetMetaDataInf {
	private String catalogName;
	private String columnLabel;
	private String columnName;
	private String columnType;
	private String columnTypeName;
	private String precision;
	private String scale;
	private String schemaName;
	private String tableName;
	
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getColumnLabel() {
		return columnLabel;
	}
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		return "ResultSetMetaDataInf [catalogName=" + catalogName
				+ ", columnLabel=" + columnLabel + ", columnName=" + columnName
				+ ", columnType=" + columnType + ", columnTypeName="
				+ columnTypeName + ", precision=" + precision + ", scale="
				+ scale + ", schemaName=" + schemaName + ", tableName="
				+ tableName + "]";
	}
}
