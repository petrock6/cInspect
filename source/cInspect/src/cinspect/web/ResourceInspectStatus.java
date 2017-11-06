package cinspect.web;

import cinspect.inspector.statuses.AppDoSInspectorStatus;
import cinspect.inspector.statuses.CCInspectorStatus;
import cinspect.inspector.statuses.LFIInspectorStatus;
import cinspect.inspector.statuses.PHPInfoInspectorStatus;
import cinspect.inspector.statuses.RCEInspectorStatus;
import cinspect.inspector.statuses.RFIInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.inspector.statuses.SSNInspectorStatus;
import cinspect.inspector.statuses.TimedSQLInspectorStatus;
import cinspect.inspector.statuses.UDRJSInspectorStatus;
import cinspect.inspector.statuses.XSSInspectorStatus;

public class ResourceInspectStatus {
	private AppDoSInspectorStatus appDoS;
	private CCInspectorStatus cc;
	private LFIInspectorStatus lfi;
	private PHPInfoInspectorStatus phpinfo;
	private RCEInspectorStatus rce;
	private RFIInspectorStatus rfi;
	private SQLInspectorStatus sql;
	private SSNInspectorStatus ssn;
	private TimedSQLInspectorStatus timedSQL;
	private UDRJSInspectorStatus udrjs;
	private XSSInspectorStatus xss;
	
	ResourceInspectStatus() {
		appDoS = AppDoSInspectorStatus.NOT_INSPECTED;
		cc = CCInspectorStatus.NOT_INSPECTED;
		lfi = LFIInspectorStatus.NOT_INSPECTED;
		phpinfo = PHPInfoInspectorStatus.NOT_INSPECTED;
		rce = RCEInspectorStatus.NOT_INSPECTED;
		rfi = RFIInspectorStatus.NOT_INSPECTED;
		sql = SQLInspectorStatus.NOT_INSPECTED;
		ssn = SSNInspectorStatus.NOT_INSPECTED;
		timedSQL = TimedSQLInspectorStatus.NOT_INSPECTED;
		udrjs = UDRJSInspectorStatus.NOT_INSPECTED;
		xss = XSSInspectorStatus.NOT_INSPECTED;
	}
	
	public AppDoSInspectorStatus getAppDoS() {
		return appDoS;
	}
	public void setAppDoS(AppDoSInspectorStatus appDoS) {
		this.appDoS = appDoS;
	}
	public CCInspectorStatus getCc() {
		return cc;
	}
	public void setCc(CCInspectorStatus cc) {
		this.cc = cc;
	}
	public LFIInspectorStatus getLfi() {
		return lfi;
	}
	public void setLfi(LFIInspectorStatus lfi) {
		this.lfi = lfi;
	}
	public PHPInfoInspectorStatus getPhpinfo() {
		return phpinfo;
	}
	public void setPhpinfo(PHPInfoInspectorStatus phpinfo) {
		this.phpinfo = phpinfo;
	}
	public RCEInspectorStatus getRce() {
		return rce;
	}
	public void setRce(RCEInspectorStatus rce) {
		this.rce = rce;
	}
	public RFIInspectorStatus getRfi() {
		return rfi;
	}
	public void setRfi(RFIInspectorStatus rfi) {
		this.rfi = rfi;
	}
	public SQLInspectorStatus getSql() {
		return sql;
	}
	public void setSql(SQLInspectorStatus sql) {
		this.sql = sql;
	}
	public SSNInspectorStatus getSsn() {
		return ssn;
	}
	public void setSsn(SSNInspectorStatus ssn) {
		this.ssn = ssn;
	}
	public TimedSQLInspectorStatus getTimedSQL() {
		return timedSQL;
	}
	public void setTimedSQL(TimedSQLInspectorStatus timedSQL) {
		this.timedSQL = timedSQL;
	}
	public UDRJSInspectorStatus getUDRJS() {
		return udrjs;
	}
	public void setUdrjs(UDRJSInspectorStatus udrjs) {
		this.udrjs = udrjs;
	}
	public XSSInspectorStatus getXss() {
		return xss;
	}
	public void setXss(XSSInspectorStatus xss) {
		this.xss = xss;
	}
}
